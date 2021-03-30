package com.gmarket.api.domain.board.subclass.presentgoodsboard;

import com.gmarket.api.domain.board.dto.subclass.PresentGoodsBoardDto;
import com.gmarket.api.domain.board.enums.BoardStatus;
import com.gmarket.api.domain.board.subclass.presentgoodsboard.enums.PresentGoodsCategory;
import com.gmarket.api.domain.board.subclass.presentgoodsboard.enums.PresentGoodsStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PresentGoodsBoardService {

    private final PresentGoodsBoardRepository presentGoodsBoardRepository;

    public Map search(
            BoardStatus status,
            PresentGoodsCategory presentGoodsCategory,
            PresentGoodsStatus presentGoodsStatus,
            String title,
            Integer page){

        if( page == null) {
            page = 1;
        }

        if(title == null){ // 제목 키워드 검색어가 null 일 때
            title = "%%";
        }

        // 정렬 조건 & 페이징
        PageRequest pageRequest = PageRequest.of(page-1, 10, Sort.by( Sort.Direction.DESC, "boardId"));

        Map map = new HashMap();


        // 게시글 상태 not null, 상품 카테고리 not null, 상품 상태 not null
        if(status != null && presentGoodsCategory!=null && presentGoodsStatus != null) {

            Page<PresentGoodsBoard> presentGoodsBoardPage = presentGoodsBoardRepository
                    .findByStatusAndPresentGoodsCategoryAndPresentGoodsStatusAndTitleLike(
                            status,
                            presentGoodsCategory,
                            presentGoodsStatus,
                            "%"+title+"%",
                            pageRequest
                    );

            map.put( "board_list",
                    presentGoodsBoardPage
                            .getContent()
                            .stream()
                            .map(presentGoodsBoard -> new PresentGoodsBoardDto().presentGoodsBoardToDto(presentGoodsBoard))
                            .collect(Collectors.toList())
            );

            map.put("total_page", presentGoodsBoardPage.getTotalPages());
        }

        // 게시글 상태 not null, 상품 카테고리 not null
        else if(status != null && presentGoodsCategory!=null) {

            Page<PresentGoodsBoard> presentGoodsBoardPage = presentGoodsBoardRepository
                    .findByStatusAndPresentGoodsCategoryAndTitleLike(
                            status,
                            presentGoodsCategory,
                            "%"+title+"%",
                            pageRequest
                    );

            map.put( "board_list",
                    presentGoodsBoardPage
                            .getContent()
                            .stream()
                            .map(presentGoodsBoard -> new PresentGoodsBoardDto().presentGoodsBoardToDto(presentGoodsBoard))
                            .collect(Collectors.toList())
            );

            map.put("total_page", presentGoodsBoardPage.getTotalPages());
        }

        // 게시글 상태 not null, 상품 상태 not null
        else if(status != null && presentGoodsStatus != null) {

            Page<PresentGoodsBoard> presentGoodsBoardPage  = presentGoodsBoardRepository
                    .findByStatusAndPresentGoodsStatusAndTitleLike(
                            status,
                            presentGoodsStatus,
                            "%"+title+"%",
                           pageRequest
            );

            map.put( "board_list",
                    presentGoodsBoardPage
                            .getContent()
                            .stream()
                            .map(presentGoodsBoard -> new PresentGoodsBoardDto().presentGoodsBoardToDto(presentGoodsBoard))
                            .collect(Collectors.toList())
            );

            map.put("total_page", presentGoodsBoardPage.getTotalPages());
        }

        // 상품 카테고리 not null, 상품 상태 not null
        else if(presentGoodsCategory!=null && presentGoodsStatus != null) {

            Page<PresentGoodsBoard> presentGoodsBoardPage  = presentGoodsBoardRepository
                    .findByStatusNotAndPresentGoodsCategoryAndPresentGoodsStatusAndTitleLike(
                            BoardStatus.DELETED,
                            presentGoodsCategory,
                            presentGoodsStatus,
                            "%"+title+"%",
                            pageRequest
                    );

            map.put( "board_list",
                    presentGoodsBoardPage
                            .getContent()
                            .stream()
                            .map(presentGoodsBoard -> new PresentGoodsBoardDto().presentGoodsBoardToDto(presentGoodsBoard))
                            .collect(Collectors.toList())
            );

            map.put("total_page", presentGoodsBoardPage.getTotalPages());
        }

        // 게시글 상태 not null
        else if(status != null ) {

            Page<PresentGoodsBoard> presentGoodsBoardPage  = presentGoodsBoardRepository
                    .findByStatusAndTitleLike(
                            status,
                            "%"+title+"%",
                            pageRequest
                    );

            map.put( "board_list",
                    presentGoodsBoardPage
                            .getContent()
                            .stream()
                            .map(presentGoodsBoard -> new PresentGoodsBoardDto().presentGoodsBoardToDto(presentGoodsBoard))
                            .collect(Collectors.toList())
            );

            map.put("total_page", presentGoodsBoardPage.getTotalPages());
        }

        // 상품 카테고리 not null
        else if(presentGoodsCategory != null) {

            Page<PresentGoodsBoard> presentGoodsBoardPage  = presentGoodsBoardRepository
                    .findByStatusNotAndPresentGoodsCategoryAndTitleLike(
                            BoardStatus.DELETED,
                            presentGoodsCategory,
                            "%" + title + "%",
                            pageRequest
                    );


            map.put("board_list",
                    presentGoodsBoardPage
                            .getContent()
                            .stream()
                            .map(presentGoodsBoard -> new PresentGoodsBoardDto().presentGoodsBoardToDto(presentGoodsBoard))
                            .collect(Collectors.toList())
            );

            map.put("total_page", presentGoodsBoardPage.getTotalPages());
        }

        // 상품 상태 not null
        else if(presentGoodsStatus != null) {

            Page<PresentGoodsBoard> presentGoodsBoardPage  =  presentGoodsBoardRepository
                    .findByStatusNotAndPresentGoodsStatusAndTitleLike(
                            BoardStatus.DELETED,
                            presentGoodsStatus,
                            "%"+title+"%",
                            pageRequest
                    );

            map.put( "board_list",
                    presentGoodsBoardPage
                            .getContent()
                            .stream()
                            .map(presentGoodsBoard -> new PresentGoodsBoardDto().presentGoodsBoardToDto(presentGoodsBoard))
                            .collect(Collectors.toList())
            );

            map.put("total_page", presentGoodsBoardPage.getTotalPages());
        }

        // 게시글 상태 null, 상품 카테고리 null, 상품 상태 null
        else if(status == null && presentGoodsCategory == null && presentGoodsStatus == null) {

            Page<PresentGoodsBoard> presentGoodsBoardPage  =  presentGoodsBoardRepository
                    .findByStatusNotAndTitleLike(
                            BoardStatus.DELETED,
                            "%"+title+"%",
                            pageRequest
                    );

            map.put( "board_list",
                    presentGoodsBoardPage
                            .getContent()
                            .stream()
                            .map(presentGoodsBoard -> new PresentGoodsBoardDto().presentGoodsBoardToDto(presentGoodsBoard))
                            .collect(Collectors.toList())
            );

            map.put("total_page", presentGoodsBoardPage.getTotalPages());
        }

        return map;
    }
}
