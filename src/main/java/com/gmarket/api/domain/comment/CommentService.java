package com.gmarket.api.domain.comment;


import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.board.BoardRepositoryInterface;
import com.gmarket.api.domain.board.enums.BoardStatus;
import com.gmarket.api.domain.comment.enums.BoardType;
import com.gmarket.api.domain.comment.dto.CommentDto;
import com.gmarket.api.domain.comment.enums.CommentStatus;
import com.gmarket.api.domain.user.UserRepository;
import com.gmarket.api.domain.user.User;
import com.gmarket.api.domain.user.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository CommentRepository;
    private final UserRepository userRepository;
    private final BoardRepositoryInterface boardRepositoryInterface;

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

        User user = userRepository.findById( commentDto.getUserId() )
                .orElseThrow( () -> new IllegalStateException("비회원은 댓글을 달 수 업습니다.") );

        if ( user.getStatus().equals(UserStatus.DELETED) ){
            throw new IllegalStateException("탈퇴한 회원 입니다");
        }

        Comment comment = BoardType.boardTypeToSubClass( boardType ); // 게시판 타입에 맞는 자식 엔티티 변환

        comment.dtoToEntity( commentDto ); // dto -> entity 매핑

        comment.boardAndUserSetting( board, user ); // 게시판, 유저 검증 후 세팅

        comment.createdStatus(); // 생성 상태 설정

        return commentDto.entityToDto(CommentRepository.save( comment )); // 댓글 엔티티 저장
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

        Comment comment = CommentRepository.findById( commentDto.getCommentId() ) // 식별 값으로 엔티티 조회
                .orElseThrow( ()-> new IllegalStateException("존재하지 않는 댓글입니다.") );

        if(comment.getStatus().equals(CommentStatus.DELETED)){
            throw new IllegalStateException("이미 삭제된 댓글입니다.");
        }

        if(!commentDto.getUserId().equals(comment.getUser().getUserId())){
            throw new IllegalStateException("댓글 작성자만 수정할 수 있습니다");
        }

        comment.update(commentDto); // 엔티티 수정

        return commentDto.entityToDto(CommentRepository.save(comment)); // 수정된 댓글 엔티티 저장
    }

    // 댓글 삭제
    @Transactional
    public void delete(BoardType boardType, Long commentId){

        Comment comment = CommentRepository.findById(commentId)
                .orElseThrow( ()-> new IllegalStateException("존재하지 않는 댓글입니다") );

        if( comment.getStatus().equals(CommentStatus.DELETED )){
            throw new IllegalStateException("이미 삭제된 댓글입니다");
        }

        comment.deletedStatus(); // 삭제 상태로 변경

        CommentRepository.save(comment); // 상태 변경된 댓글 엔티티 저장
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
                PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.DESC, "commentId"));

        List<Comment> commentList = CommentRepository.
                findByBoardAndStatusNot( board, CommentStatus.DELETED, pageRequest).getContent();

        List<CommentDto> commentDtoList = new ArrayList<>();

        for(Comment comment: commentList){
            CommentDto commentDto = new CommentDto();
            commentDtoList.add(commentDto.entityToDto(comment));
        }

        return commentDtoList;
    }
}
