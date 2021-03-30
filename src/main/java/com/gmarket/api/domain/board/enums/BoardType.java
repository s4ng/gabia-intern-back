package com.gmarket.api.domain.board.enums;

import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.board.dto.BoardDto;
import com.gmarket.api.domain.board.dto.subclass.NoticeBoardDto;
import com.gmarket.api.domain.board.dto.subclass.PresentGoodsBoardDto;
import com.gmarket.api.domain.board.dto.subclass.UsedGoodsBoardDto;
import com.gmarket.api.domain.board.subclass.noticeboard.NoticeBoard;
import com.gmarket.api.domain.board.subclass.presentgoodsboard.PresentGoodsBoard;
import com.gmarket.api.domain.board.subclass.usedgoodsboard.UsedGoodsBoard;
import lombok.Getter;

@Getter
public enum BoardType {
    NOTICE, USED, PRESENT;

    private String type;

    public static class Values {
        public static final String NOTICE = "NOTICE";
        public static final String USED = "USED";
        public static final String PRESENT = "PRESENT";
    }

    // BoardType -> Board SubClass
    public static Board boardTypeToSubClass(BoardType boardType){
        switch (boardType){
            case NOTICE:
                return new NoticeBoard();
            case USED:
                return new UsedGoodsBoard();
            case PRESENT:
                return new PresentGoodsBoard();
            default:
                throw new IllegalStateException("정확한 게시판 타입을 입력하세요");
        }
    }

    // BoardType, BoardDto SubClass -> Board SubClass
    public static Board boardTypeAndDtoToSubClass(BoardType boardType, BoardDto boardDto){
        switch (boardType){
            case NOTICE:
                NoticeBoard noticeBoard = new NoticeBoard();
                NoticeBoardDto noticeBoardDto = (NoticeBoardDto) boardDto;
                if(noticeBoardDto.getNoticeCategory() == null){
                    throw new IllegalStateException("공지사항 카테고리를 지정해야합니다.");
                }
                return noticeBoard.dtoToNoticeBoard(noticeBoardDto);
            case USED:
                UsedGoodsBoard usedGoodsBoard = new UsedGoodsBoard();
                UsedGoodsBoardDto usedGoodsBoardDto = (UsedGoodsBoardDto) boardDto;
                if(usedGoodsBoardDto.getUsedGoodsCategory()== null){
                    throw new IllegalStateException("판매 상품 카테고리를 지정해야합니다.");
                }
                if(usedGoodsBoardDto.getUsedGoodsStatus() == null){
                    throw new IllegalStateException("판매 상품의 상태를 지정해야합니다.");
                }
                if(usedGoodsBoardDto.getSellPrice() == null){
                    throw new IllegalStateException("판매 상품의 가격은 필수로 작성해야합니다.");
                }
                return usedGoodsBoard.dtoToUsedGoodsBoard(usedGoodsBoardDto);
            case PRESENT:
                PresentGoodsBoard presentGoodsBoard = new PresentGoodsBoard();
                PresentGoodsBoardDto presentGoodsBoardDto = (PresentGoodsBoardDto) boardDto;
                if(presentGoodsBoardDto.getPresentGoodsCategory() == null){
                    throw new IllegalStateException("나눔 상품 카테고리를 지정해야합니다.");
                }
                if(presentGoodsBoardDto.getPresentGoodsStatus() == null){
                    throw new IllegalStateException("나눔 상품의 상태를 지정해야합니다.");
                }
                if(presentGoodsBoardDto.getRaffleClosedAt() == null){
                    throw new IllegalStateException("래플 종료 시간을 설정해야합니다.");
                }
                return presentGoodsBoard.dtoToPresentGoodsBoard(presentGoodsBoardDto);
            default:
                throw new IllegalStateException("정확한 게시판 타입을 입력하세요");
        }
    }

    // BoardType, Board SubClass -> BoardDto SubClass
    public static BoardDto boardTypeAndDtoToSubClassDto(BoardType boardType, Board board){
        switch (boardType){
            case NOTICE:
                NoticeBoardDto noticeBoardDto = new NoticeBoardDto();
                return noticeBoardDto.noticeGoodsBoardToDto((NoticeBoard) board);
            case USED:
                UsedGoodsBoardDto usedGoodsBoardDto = new UsedGoodsBoardDto();
                return usedGoodsBoardDto.usedGoodsBoardToDto((UsedGoodsBoard) board);
            case PRESENT:
                PresentGoodsBoardDto presentGoodsBoardDto = new PresentGoodsBoardDto();
                return presentGoodsBoardDto.presentGoodsBoardToDto((PresentGoodsBoard) board);
            default:
                throw new IllegalStateException("정확한 게시판 타입을 입력하세요");
        }
    }
}
