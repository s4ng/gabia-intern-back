package com.gmarket.api.domain.board.subclass.usedgoodsboard.enums;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public enum UsedGoodsSortOption {
    DEFAULT, LOW_PRICE, HIGH_PRICE;

    static public PageRequest sortOptionToPageable(UsedGoodsSortOption sortOption, Integer page){
        switch (sortOption){
            case LOW_PRICE:
                return PageRequest.of(page-1, 10, Sort.by( Sort.Direction.ASC, "sellPrice"));
            case HIGH_PRICE:
                return PageRequest.of(page-1, 10, Sort.by( Sort.Direction.DESC, "sellPrice"));
            default:
                return PageRequest.of(page-1, 10, Sort.by( Sort.Direction.DESC, "boardId"));
        }
    }

}
