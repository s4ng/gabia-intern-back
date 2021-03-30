package com.gmarket.api.domain.board;

import com.gmarket.api.domain.alert.AlertRepository;
import com.gmarket.api.domain.alertkeyword.AlertKeyword;
import com.gmarket.api.domain.alertkeyword.AlertKeywordRepository;
import com.gmarket.api.domain.alertkeyword.KeywordAlertService;
import com.gmarket.api.domain.alertkeyword.enums.AlertKeywordStatus;
import com.gmarket.api.domain.board.dto.BoardDto;
import com.gmarket.api.domain.board.dto.subclass.NoticeBoardDto;
import com.gmarket.api.domain.board.dto.subclass.UsedGoodsBoardDto;
import com.gmarket.api.domain.board.enums.BoardStatus;
import com.gmarket.api.domain.board.enums.BoardType;
import com.gmarket.api.domain.raffle.RaffleService;
import com.gmarket.api.domain.user.User;
import com.gmarket.api.domain.user.UserRepository;
import com.gmarket.api.domain.user.enums.UserStatus;
import com.gmarket.api.domain.user.enums.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepositoryInterface boardRepositoryInterface;

    private final BoardRepository boardRepository;

    private final UserRepository userRepository;

    private final AlertKeywordRepository alertKeywordRepository;

    private final AlertRepository alertRepository;

    private final RaffleService raffleService;

    private final SimpMessagingTemplate messagingTemplate;

    // 게시글 저장 서비스
    @Transactional
    public BoardDto save(BoardType boardType, BoardDto boardDto) throws InterruptedException {
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

        User user = userRepository.findById(boardDto.getUserId())
                .orElseThrow( () -> new IllegalStateException("비회원은 글을 작성할 수 없습니다."));;

        if(user.getStatus().equals(UserStatus.DELETED)){
            throw new IllegalStateException("탈퇴 회원은 글을 작성할 수 없습니다.");
        }

        user.addPoint(5);

        userRepository.save(user);

        Board board = BoardType.boardTypeAndDtoToSubClass(boardType, boardDto);

        board.userSetting(user);

        boardRepositoryInterface.save(board); // 게시글 저장

        // 저장 상태인 알림 키워드 리스트
        List<AlertKeyword> alertKeywordList = alertKeywordRepository.findLocateKeyword(boardDto.getTitle(), AlertKeywordStatus.CREATED);

        KeywordAlertService keywordAlertService =
                new KeywordAlertService(alertRepository, messagingTemplate);

        keywordAlertService.keywordAlertFor(alertKeywordList, board);


        if(boardDto.getBoardType().equals(BoardType.PRESENT)){
            raffleService.raffleClose(board);
        }

        return BoardType.boardTypeAndDtoToSubClassDto(boardType, board);
    }

    // 게시글 조회 서비스
    @Transactional
    public BoardDto findId(BoardType boardType, Long boardId){
        Board board = boardRepositoryInterface.findById(boardId)
                .orElseThrow( () -> new IllegalStateException("존재하지 않는 게시글 입니다"));

        if(board.getStatus().equals(BoardStatus.DELETED)){
            throw new IllegalStateException("삭제 된 게시글 입니다");
        }
        board.addViewCount();
        return BoardType.boardTypeAndDtoToSubClassDto(boardType, boardRepositoryInterface.save(board));
    }

    // 게시글 수정 서비스
    @Transactional
    public BoardDto update(BoardType boardType, BoardDto boardDto){

        // Dto check, 검증 관련 코드 모듈화 필요
        if (!boardType.equals(boardDto.getBoardType())){
            throw new IllegalStateException("게시판 타입이 일치하지 않습니다.");
        }

        if(boardDto.getBoardId() == null) {
            throw new IllegalStateException("게시글 식별 값이 입력되지 않았습니다.");
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


        User user = userRepository.findById(boardDto.getUserId())
                .orElseThrow( () -> new IllegalStateException("존재하지 않은 회원입니다"));

        if(user.getStatus().equals(UserStatus.DELETED)){
            throw new IllegalStateException("탈퇴 회원은 글을 작성할 수 없습니다.");
        }

        Board board = boardRepositoryInterface.findById(boardDto.getBoardId())
                .orElseThrow( () -> new IllegalStateException("존재하지 않는 게시글 입니다"));

        if(!board.getUser().getUserId().equals(user.getUserId())){
            throw new IllegalStateException("작성자와 일치하지 않습니다, 작성자만 게시글을 수정할 수 있습니다");
        }

        if(board.getStatus().equals(BoardStatus.DELETED)) {
            throw new IllegalStateException("해당 글은 이미 삭제 상태입니다.");
        }

        if(board.getClass().getSimpleName().equals("NoticeBoard")){
            NoticeBoardDto noticeBoardDto = (NoticeBoardDto) boardDto;
            if(noticeBoardDto.getNoticeCategory() == null){
                throw new IllegalStateException("공지사항 카테고리를 입력해야 합니다");
            }
        } else if (board.getClass().getSimpleName().equals("usedGoodsBoard")){
            UsedGoodsBoardDto usedGoodsBoardDto = (UsedGoodsBoardDto) boardDto;
            if(usedGoodsBoardDto.getUsedGoodsCategory() == null){
                throw new IllegalStateException("상품 카테고리를 입력해야 합니다");
            }
            if(usedGoodsBoardDto.getUsedGoodsStatus() == null){
                throw new IllegalStateException("상품 상태를 입력해야 합니다");
            }
            if(usedGoodsBoardDto.getUsedGoodsStatus() == null){
                throw new IllegalStateException("상품 상태를 입력해야 합니다");
            }
        }

        board = BoardType.boardTypeAndDtoToSubClass(boardType, boardDto);

        board.userSetting(user);

        board.modifiedStatus(boardDto.getUserId());

        return BoardType.boardTypeAndDtoToSubClassDto(boardType, boardRepositoryInterface.save(board));
    }


    // 게시글 삭제 서비스
    @Transactional
    public void delete(BoardType boardType, Long boardId, Long userId){

        Board board = boardRepositoryInterface.findById(boardId)
                .orElseThrow( () -> new IllegalStateException("존재하지 않는 게시글 입니다"));

        if(board.getStatus().equals(BoardStatus.DELETED)){
            throw new IllegalStateException("이미 삭제된 게시글 입니다.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow( () -> new IllegalStateException("존재하지 않은 회원입니다"));

        if(!board.getUser().getUserId().equals(userId)){
            throw new IllegalStateException("작성자만 해당 글을 삭제 할 수 있습니다");
        }

        user.addPoint(-5);

        userRepository.save(user);

        board.deletedStatus();

        boardRepositoryInterface.save(board);
    }

    public Map findPage(BoardType boardType, int page){
        List<Board> boardList = boardRepository.findPage(BoardType.boardTypeToSubClass(boardType), page);

        List<BoardDto> boardDtoList = new ArrayList<>();

        for(Board board: boardList){
            boardDtoList.add(BoardType.boardTypeAndDtoToSubClassDto(boardType, board ));
        }

        Map map = new HashMap();

        map.put("board_list",boardDtoList);

        Long boardCount = boardRepository.findBoardCount(BoardType.boardTypeToSubClass(boardType));

        if( boardCount % 10 == 0) {
            map.put("page_count",boardCount/10 );
        } else {
            map.put("page_count",boardCount/10 +1);
        }

        return map;
    }

}
