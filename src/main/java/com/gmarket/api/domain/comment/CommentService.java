package com.gmarket.api.domain.comment;


import com.gmarket.api.domain.alert.Alert;
import com.gmarket.api.domain.alert.AlertRepository;
import com.gmarket.api.domain.alert.dto.AlertDto;
import com.gmarket.api.domain.alert.enums.AlertType;
import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.board.BoardRepositoryInterface;
import com.gmarket.api.domain.board.enums.BoardStatus;
import com.gmarket.api.domain.comment.dto.CommentDto;
import com.gmarket.api.domain.comment.enums.BoardType;
import com.gmarket.api.domain.comment.enums.CommentStatus;
import com.gmarket.api.domain.user.User;
import com.gmarket.api.domain.user.UserRepositoryInterface;
import com.gmarket.api.domain.user.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository CommentRepository;
    private final UserRepositoryInterface userRepository;
    private final BoardRepositoryInterface boardRepositoryInterface;

    private final AlertRepository alertRepository;

    private final SimpMessagingTemplate messagingTemplate;

    // 댓글 저장
    @Transactional
    public CommentDto save( BoardType boardType, CommentDto commentDto ){

        if ( !boardType.equals( commentDto.getBoardType() ) ){
            throw new IllegalStateException("게시판 타입이 일치하지 않습니다.");
        }

        if ( commentDto.getComment().isEmpty() ){
            throw new IllegalStateException("댓글 내용이 입력되지 않았습니다.");
        }

        Board board = boardRepositoryInterface.findById( commentDto.getBoardId() )
                .orElseThrow( () -> new IllegalStateException("존재하지 않은 게시판입니다"));

        if(board.getStatus().equals( BoardStatus.DELETED )){
            throw new IllegalStateException("이미 삭제된 게시판 입니다");
        }

        User user = userRepositoryInterface.findById( commentDto.getUserId() )
                .orElseThrow( () -> new IllegalStateException("비회원은 댓글을 달 수 업습니다.") );

        if ( user.getStatus().equals(UserStatus.DELETED) ){
            throw new IllegalStateException("탈퇴한 회원 입니다");
        }

        Comment comment = BoardType.boardTypeToSubClass( boardType ); // 게시판 타입에 맞는 자식 엔티티 변환

        comment.dtoToEntity( commentDto ); // dto -> entity 매핑
        
        comment.boardAndUserSetting( board, user ); // 게시판, 유저 검증 후 세팅
        
        comment.createdStatus(); // 생성 상태 설정

        commentRepository.save(comment); // 댓글 엔티티 저장

        // 댓글 알림 중복 검사
        Alert alert = alertRepository.findTop1ByUserAndAlertTypeAndBoard( board.getUser(), AlertType.COMMENT, board);

        String message = "댓글 알림: "+ board.getTitle() + "("+commentRepository.countByBoard(board)+")";

        if( alert == null ){
            alert = new Alert().createAlert( board.getUser(), board, message, AlertType.COMMENT);
        } else {
            alert.alertUpdate(message);
        }

        // 댓글 알림 저장
        alertRepository.save(alert);

        // 댓글 알림 웹 소켓 전송
        messagingTemplate.convertAndSend(
                "/sub/alert/" + board.getUser().getUserId() , new AlertDto().entityToDto(alert));

        return commentDto.entityToDto(comment);
    }

    // 댓글 수정
    @Transactional
    public CommentDto update( BoardType boardType, CommentDto commentDto ){

        if (!boardType.equals(commentDto.getBoardType())){
            throw new IllegalStateException("게시판 타입이 일치하지 않습니다.");
        }

        if (commentDto.getComment().isEmpty()){
            throw new IllegalStateException("댓글 내용이 입력되지 않았습니다.");
        }

        Comment comment = commentRepository.findById( commentDto.getCommentId() ) // 식별 값으로 엔티티 조회
                .orElseThrow( ()-> new IllegalStateException("존재하지 않는 댓글입니다.") );

        if(comment.getStatus().equals(CommentStatus.DELETED)){
            throw new IllegalStateException("이미 삭제된 댓글입니다.");
        }

        if(!commentDto.getUserId().equals(comment.getUser().getUserId())){
            throw new IllegalStateException("댓글 작성자만 수정할 수 있습니다");
        }

        comment.update(commentDto); // 엔티티 수정

        return commentDto.entityToDto(commentRepository.save(comment)); // 수정된 댓글 엔티티 저장
    }

    // 댓글 삭제
    @Transactional
    public void delete(BoardType boardType, Long commentId){

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow( ()-> new IllegalStateException("존재하지 않는 댓글입니다") );

        if( comment.getStatus().equals(CommentStatus.DELETED )){
            throw new IllegalStateException("이미 삭제된 댓글입니다");
        }

        comment.deletedStatus(); // 삭제 상태로 변경

        commentRepository.save(comment); // 상태 변경된 댓글 엔티티 저장
    }

    // 댓글 조회
    public List<CommentDto> commentList(BoardType boardType, Long boardId){

        Board board = boardRepositoryInterface.findById( boardId )
                .orElseThrow( ()-> new IllegalStateException( "존재하지 않는 게시글입니다" ));

        if( board.getStatus().equals(BoardStatus.DELETED )){
            throw new IllegalStateException( "이미 삭제된 게시글입니다" );
        }

        // 댓글의 경우 해당 게시글에서 전체 보기로 보여줌
        PageRequest pageRequest =
                PageRequest.of(0, Integer.MAX_VALUE, Sort.by( Sort.Direction.DESC, "commentId") );

        // stream 활용, java 8에서 추가된 기능으로 컬렉션
        // 배열 또는 컬렉션 인스턴스에 함수 여러 개를 조합해서 원하는 결과를 필터링하고 가공된 결과를 얻을 수 있음
        return commentRepository.findByBoardAndStatusNot( board, CommentStatus.DELETED, pageRequest ).getContent()
                // Page<Comment> -> List<Comment> 여기까지 컬렉션
                .stream().map( comment -> new CommentDto().entityToDto(comment)).collect(Collectors.toList());
                // Stream 생성 -> map()은 중개연산 ( comment -> dto 맵핑 연산 ) -> collect 스트림을 컬렉션으로 변환 toList
    }
}
