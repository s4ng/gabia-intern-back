package com.gmarket.api.domain.board;

import com.gmarket.api.domain.board.dto.BoardMapper;
import com.gmarket.api.domain.board.dto.BoardDto;
import com.gmarket.api.domain.board.enums.BoardType;
import com.gmarket.api.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service // Bean Component
@Transactional(readOnly = true) // readOnly 현재 해당 그 트랜잭션 내에서 데이터를 읽기
@RequiredArgsConstructor // @RequiredArgsConstructor 어노테이션은 final, @NonNull 필드 값만 파라미터로 받는 생성자를 만듬
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    private String fbpJpql1 ="SELECT m FROM ";
    private String fbpJpql2 =" m where m.status!='DELETE' ORDER BY m.boardId DESC";

    private String fpJpql1 ="SELECT m FROM ";
    private String fpJpql2 =" m where m.status!='DELETE' AND m.boardId =";


    // 게시판 페이지 조회 서비스
    public List<Board> findBoardPage(BoardType boardType, int page) {
        return boardRepository.findBoardPage(fbpJpql1+BoardType.enumToJpql(boardType)+fbpJpql2, page);
    }

    // 게시글 하나 조회 서비스
    public Board findPost(BoardType boardType, Long boardId) {
        return boardRepository.findPost(fpJpql1+BoardType.enumToJpql(boardType)+fpJpql2+boardId);
    }

    @Transactional // 게시글 하나 생성 서비스, DTO 활용
    public Board createPost(BoardType boardType, BoardDto boardDto){
        Board board = BoardMapper.dtoToEntity(boardType, boardDto);
        board.setUser(userRepository.findOne(boardDto.getUserId()));
        return boardRepository.createPost(board);
    }

    @Transactional // 게시글 하나 수정 서비스, DTO 활용
    public Board updatePost(BoardType boardType, BoardDto boardDto){
        Board board = BoardMapper.dtoToEntity(boardType, boardDto);
        board.modified();
        board.setUser(userRepository.findOne(boardDto.getUserId()));
        return boardRepository.updatePost(board);
    }

    @Transactional // 게시글 하나 삭제 서비스
    public Long deletePost(BoardType boardType, Long boardId){
        Board board = BoardService.this.findPost(boardType, boardId);
        board.delete();
        return boardRepository.deletePost(board);
    }

}
