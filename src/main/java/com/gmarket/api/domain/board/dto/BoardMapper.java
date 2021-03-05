package com.gmarket.api.domain.board.dto;

import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.board.enums.BoardType;
import com.gmarket.api.domain.board.noticeboard.NoticeBoard;
import com.gmarket.api.domain.board.presentgoodsboard.PresentGoodsBoard;
import com.gmarket.api.domain.board.usedgoodsboard.UsedGoodsBoard;

public class BoardMapper {
    public static Board dtoToEntity(BoardType boardType, BoardDto boardDto){
        if ( boardDto == null ) {
            return null;
        }
        else if(boardType.equals(BoardType.notice)){
            NoticeBoard noticeBoard = new NoticeBoard();
            noticeBoard.copySuper(noticeBoard, boardDto);
            noticeBoard.copySub(noticeBoard, boardDto);
            return noticeBoard;
        }
        else if(boardType.equals(BoardType.used)){
            UsedGoodsBoard usedGoodsBoard = new UsedGoodsBoard();
            usedGoodsBoard.copySuper(usedGoodsBoard, boardDto);
            usedGoodsBoard.copySub(usedGoodsBoard, boardDto);
            return usedGoodsBoard;
        }
        else if(boardType.equals(BoardType.present)) {
            PresentGoodsBoard presentGoodsBoard = new PresentGoodsBoard();
            presentGoodsBoard.copySuper(presentGoodsBoard, boardDto);
            presentGoodsBoard.copySub(presentGoodsBoard, boardDto);
            return presentGoodsBoard;
        } else {
            return null;
        }
    }
}
