package com.gmarket.api.domain.board;

import com.gmarket.api.domain.board.dto.BoardDto;
import com.gmarket.api.domain.board.enums.BoardStatus;
import com.gmarket.api.domain.board.enums.BoardType;
import com.gmarket.api.domain.user.UserRepositoryInterface;
import com.gmarket.api.domain.user.User;
import com.gmarket.api.domain.user.enums.UserStatus;
import com.gmarket.api.domain.user.enums.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepositoryInterface boardRepositoryInterface;

    private final BoardRepository boardRepository;

    private final UserRepositoryInterface userRepositoryInterface;

    // 게시글 저장 서비스
    @Transactional
    public BoardDto save(BoardType boardType, BoardDto boardDto){
        System.out.println("========");
        System.out.println(boardType);
        System.out.println(boardDto.getBoardType());
        // Dto check
        if (!boardType.equals(boardDto.getBoardType())){
            throw new IllegalStateException("게시판 타입이 일치하지 않습니다.");
        }

        if (boardType.equals(BoardType.NOTICE)){
            if(!boardDto.getUserType().equals(UserType.MANAGER)) {
                throw new IllegalStateException("공지사항은 매니저만 작성 가능합니다.");
            }
        }

        if(boardDto.getTitle().isEmpty()){
            throw new IllegalStateException("제목은 필수로 입력해야 합니다.");
        }

        if(boardDto.getTitle().length()>30){
            throw new IllegalStateException("제목은 30자를 초과 할 수 없습니다.");
        }

        if(boardDto.getUserId() == null){
            throw new IllegalStateException("유저 식별 값이 입력되지 않았습니다.");
        }

        Optional<User> optionalUser = userRepositoryInterface.findById(boardDto.getUserId());

        if(optionalUser.isEmpty()){
            throw new IllegalStateException("비회원은 글을 작성할 수 없습니다.");
        }

        if(optionalUser.get().getStatus().equals(UserStatus.DELETED)){
            throw new IllegalStateException("탈퇴 회원은 글을 작성할 수 없습니다.");
        }

        Board board = BoardType.boardTypeAndDtoToSubClass(boardType, boardDto);

        board.userSetting(optionalUser.get());

        return BoardType.boardTypeAndDtoToSubClassDto(boardType, boardRepositoryInterface.save(board));
    }

    // 게시글 조회 서비스
    public BoardDto findId(BoardType boardType, Long boardId){
        // Board 엔티티가 아닌 Board SubClass 엔티티 조회를 위해 EntityManager 활용한 userRepository 구현
        Board board = boardRepository.findById(BoardType.boardTypeToSubClass(boardType), boardId);
        if(board == null){
            throw new IllegalStateException("게시글이 존재하지 않습니다");
        }

        if(board.getStatus().equals(BoardStatus.DELETED)){
            throw new IllegalStateException("삭제 된 게시글 입니다");
        }

        return BoardType.boardTypeAndDtoToSubClassDto(boardType, board);
    }

    // 게시글 수정 서비스
    @Transactional
    public BoardDto update(BoardType boardType, BoardDto boardDto){

        // Dto check, 검증 관련 코드 모듈화 필요
        if (!boardType.equals(boardDto.getBoardType())){
            throw new IllegalStateException("게시판 타입이 일치하지 않습니다.");
        }

        if(boardDto.getBoardId() == null) {
            throw new IllegalStateException("게시판 식별 값이 입력되지 않았습니다.");
        }

        if(boardDto.getTitle().isEmpty()){
            throw new IllegalStateException("제목은 필수로 입력해야 합니다.");
        }

        if(boardDto.getTitle().length()>30){
            throw new IllegalStateException("제목은 30자를 초과 할 수 없습니다.");
        }

        if(boardDto.getUserId() == null){
            throw new IllegalStateException("유저 식별 값이 입력되지 않았습니다.");
        }

        Optional<User> optionalUser = userRepositoryInterface.findById(boardDto.getUserId());

        if(optionalUser.isEmpty()){
            throw new IllegalStateException("존재하지 않은 회원입니다");
        }

        if(optionalUser.get().getStatus().equals(UserStatus.DELETED)){
            throw new IllegalStateException("탈퇴 회원은 글을 작성할 수 없습니다.");
        }

        Board board = boardRepository.findById(BoardType.boardTypeToSubClass(boardType), boardDto.getBoardId());

        if(!board.getUser().getUserId().equals(optionalUser.get().getUserId())){
            throw new IllegalStateException("작성자와 일치하지 않습니다, 작성자만 게시글을 수정할 수 있습니다");
        }

        if(board.getStatus().equals(BoardStatus.DELETED)) {
            throw new IllegalStateException("해당 글은 이미 삭제 상태입니다.");
        }

        board = BoardType.boardTypeAndDtoToSubClass(boardType, boardDto);

        board.userSetting(optionalUser.get());

        board.modifiedStatus(boardDto.getUserId());

        return BoardType.boardTypeAndDtoToSubClassDto(boardType, boardRepositoryInterface.save(board));
    }

    // 게시글 삭제 서비스
    @Transactional
    public void delete(BoardType boardType, Long boardId, Long userId){

        Board board = boardRepository.findById(BoardType.boardTypeToSubClass(boardType), boardId);

        if(board == null){
            throw new IllegalStateException("존재하지 않은 게시글입니다.");
        }

        if(board.getStatus().equals(BoardStatus.DELETED)){
            throw new IllegalStateException("이미 삭제된 게시글 입니다.");
        }

        if(!board.getUser().getUserId().equals(userId)){
            throw new IllegalStateException("작성자만 해당 글을 삭제 할 수 있습니다");
        }

        board.deletedStatus();

        boardRepositoryInterface.save(board);
    }

    public List<BoardDto> findPage(BoardType boardType, int page){
        List<Board> boardList = boardRepository.findPage(BoardType.boardTypeToSubClass(boardType), page);

        List<BoardDto> boardDtoList = new ArrayList<>();

        for(Board board: boardList){
            boardDtoList.add(BoardType.boardTypeAndDtoToSubClassDto(boardType, board ));
        }

        return boardDtoList;
    }
}
