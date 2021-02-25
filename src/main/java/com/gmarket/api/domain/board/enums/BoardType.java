package com.gmarket.api.domain.board.enums;

public enum BoardType {
    notice, used, present;

    // BoardType -> Jpql: Entity
    static public String enumToJpql(BoardType boardType){
        switch (boardType) {
            case notice:
                return "NoticeBoard";
            case used:
                return "UsedGoodsBoard";
            case present:
                return "PresentGoodsBoard";
            default:
                return "";
        }
    }
}
