package com.gmarket.api.domain.board.subclass.usedgoodsboard;

import com.gmarket.api.domain.board.dto.subclass.UsedGoodsBoardDto;
import com.gmarket.api.domain.board.enums.BoardStatus;
import com.gmarket.api.domain.board.subclass.usedgoodsboard.enums.UsedGoodsCategory;
import com.gmarket.api.domain.board.subclass.usedgoodsboard.enums.UsedGoodsSortOption;
import com.gmarket.api.domain.board.subclass.usedgoodsboard.enums.UsedGoodsStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UsedGoodsBoardService {

    private final UsedGoodsBoardRepository usedGoodsBoardRepository;

    // 중고 상품 게시판 검색 조회 서비스
    // 검색 입력 조건: 제목 키워드, 상품 카테고리, 판매 상태, 상품 상태, 최소 가격, 최대 가격
    // 정렬 조건: 최신 등록 순, 낮은 가격 순, 높은 가격 순
    public Map search( BoardStatus status,
                       UsedGoodsCategory usedGoodsCategory,
                       UsedGoodsStatus usedGoodsStatus,
                       String title,
                       Integer minPrice,
                       Integer maxPrice,
                       Integer page,
                       UsedGoodsSortOption sortOption) {

        if( page == null) {
            page = 1;
        }
        if(sortOption == null){
            sortOption = UsedGoodsSortOption.DEFAULT;
        }
        // 정렬 조건 & 페이징
        PageRequest pageRequest = UsedGoodsSortOption.sortOptionToPageable(sortOption, page);

        Map map = new HashMap();

        if(title == null){ // 제목 키워드 검색어가 null 일 때
            title = "%%";
        }

        if(minPrice == null){ // 최소 가격이 null 일 때
            minPrice = 1;
        }

        if(maxPrice == null){ // 최대 가격이 null 일 때
            maxPrice = Integer.MAX_VALUE-1;
        }

        // 게시글 상태 not null, 상품 카테고리 not null, 상품 상태 not null
        if( status != null && usedGoodsCategory!=null && usedGoodsStatus != null) {

            Page<UsedGoodsBoard> usedGoodsBoardPage = usedGoodsBoardRepository
                    .findByStatusAndUsedGoodsCategoryAndUsedGoodsStatusAndTitleLikeAndSellPriceAfterAndSellPriceBefore(
                            status,
                            usedGoodsCategory,
                            usedGoodsStatus,
                            "%"+title+"%",
                            minPrice-1,
                            maxPrice+1,
                            pageRequest
                    );

            map.put( "board_list",
                    usedGoodsBoardPage
                            .getContent()
                            .stream()
                            .map(usedGoodsBoard -> new UsedGoodsBoardDto().usedGoodsBoardToDto(usedGoodsBoard))
                            .collect(Collectors.toList())
            );

            map.put("total_page", usedGoodsBoardPage.getTotalPages());
        }

        // 게시글 상태 not null, 상품 카테고리 not null
        else if(status != null && usedGoodsCategory!=null) {

            Page<UsedGoodsBoard> usedGoodsBoardPage = usedGoodsBoardRepository
                    .findByStatusAndUsedGoodsCategoryAndTitleLikeAndSellPriceAfterAndSellPriceBefore(
                            status,
                            usedGoodsCategory,
                            "%"+title+"%",
                            minPrice-1,
                            maxPrice+1,
                            pageRequest
                    );

            map.put( "board_list",
                    usedGoodsBoardPage
                            .getContent()
                            .stream()
                            .map(usedGoodsBoard -> new UsedGoodsBoardDto().usedGoodsBoardToDto(usedGoodsBoard))
                            .collect(Collectors.toList())
            );

            map.put("total_page", usedGoodsBoardPage.getTotalPages());
        }
        // 게시글 상태 not null, 상품 상태 not null
        else if(status != null && usedGoodsStatus != null) {

            Page<UsedGoodsBoard> usedGoodsBoardPage = usedGoodsBoardRepository
                    .findByStatusAndUsedGoodsStatusAndTitleLikeAndSellPriceAfterAndSellPriceBefore(
                            status,
                            usedGoodsStatus,
                            "%"+title+"%",
                            minPrice-1,
                            maxPrice+1,
                            pageRequest
                    );

            map.put( "board_list",
                    usedGoodsBoardPage
                            .getContent()
                            .stream()
                            .map(usedGoodsBoard -> new UsedGoodsBoardDto().usedGoodsBoardToDto(usedGoodsBoard))
                            .collect(Collectors.toList())
            );

            map.put("total_page", usedGoodsBoardPage.getTotalPages());
        }
        // 상품 카테고리 not null, 상품 상태 not null
        else if(usedGoodsCategory!=null && usedGoodsStatus != null) {

            Page<UsedGoodsBoard> usedGoodsBoardPage = usedGoodsBoardRepository
                    .findByStatusNotAndUsedGoodsCategoryAndUsedGoodsStatusAndTitleLikeAndSellPriceAfterAndSellPriceBefore(
                            BoardStatus.DELETED,
                            usedGoodsCategory,
                            usedGoodsStatus,
                            "%"+title+"%",
                            minPrice-1,
                            maxPrice+1,
                            pageRequest
                    );

            map.put( "board_list",
                    usedGoodsBoardPage
                            .getContent()
                            .stream()
                            .map(usedGoodsBoard -> new UsedGoodsBoardDto().usedGoodsBoardToDto(usedGoodsBoard))
                            .collect(Collectors.toList())
            );

            map.put("total_page", usedGoodsBoardPage.getTotalPages());
        }

        // 게시글 상태 not null
        else if(status != null ) {

            Page<UsedGoodsBoard> usedGoodsBoardPage = usedGoodsBoardRepository
                    .findByStatusAndTitleLikeAndSellPriceAfterAndSellPriceBefore(
                            status,
                            "%"+title+"%",
                            minPrice-1,
                            maxPrice+1,
                            pageRequest
                    );

            map.put( "board_list",
                    usedGoodsBoardPage
                            .getContent()
                            .stream()
                            .map(usedGoodsBoard -> new UsedGoodsBoardDto().usedGoodsBoardToDto(usedGoodsBoard))
                            .collect(Collectors.toList())
            );

            map.put("total_page", usedGoodsBoardPage.getTotalPages());
        }

        // 상품 카테고리 not null
        else if(usedGoodsCategory != null) {

            Page<UsedGoodsBoard> usedGoodsBoardPage =  usedGoodsBoardRepository
                    .findByStatusNotAndUsedGoodsCategoryAndTitleLikeAndSellPriceAfterAndSellPriceBefore(
                            BoardStatus.DELETED,
                            usedGoodsCategory,
                            "%" + title + "%",
                            minPrice - 1,
                            maxPrice + 1,
                            pageRequest
                    );

            map.put("board_list",
                    usedGoodsBoardPage
                            .getContent()
                            .stream()
                            .map(usedGoodsBoard -> new UsedGoodsBoardDto().usedGoodsBoardToDto(usedGoodsBoard))
                            .collect(Collectors.toList())
            );

            map.put("total_page", usedGoodsBoardPage.getTotalPages());
        }

        // 상품 상태 not null
        else if(usedGoodsStatus != null) {

            Page<UsedGoodsBoard> usedGoodsBoardPage =  usedGoodsBoardRepository
                    .findByStatusNotAndUsedGoodsStatusAndTitleLikeAndSellPriceAfterAndSellPriceBefore(
                            BoardStatus.DELETED,
                            usedGoodsStatus,
                            "%"+title+"%",
                            minPrice-1,
                            maxPrice+1,
                            pageRequest
                    );

            map.put( "board_list",
                    usedGoodsBoardPage
                            .getContent()
                            .stream()
                            .map(usedGoodsBoard -> new UsedGoodsBoardDto().usedGoodsBoardToDto(usedGoodsBoard))
                            .collect(Collectors.toList())
            );

            map.put("total_page", usedGoodsBoardPage.getTotalPages());
        }

        // 게시글 상태 null, 상품 카테고리 null, 상품 상태 null
        else if(status == null && usedGoodsCategory == null && usedGoodsStatus == null) {

            Page<UsedGoodsBoard> usedGoodsBoardPage =  usedGoodsBoardRepository
                    .findByStatusNotAndTitleLikeAndSellPriceAfterAndSellPriceBefore(
                            BoardStatus.DELETED,
                            "%"+title+"%",
                            minPrice-1,
                            maxPrice+1,
                            pageRequest
                    );

            map.put( "board_list", usedGoodsBoardPage
                    .getContent()
                    .stream()
                    .map(usedGoodsBoard -> new UsedGoodsBoardDto().usedGoodsBoardToDto(usedGoodsBoard))
                    .collect(Collectors.toList())
            );

            map.put("total_page", usedGoodsBoardPage.getTotalPages());
        }

        return map;
    }
}