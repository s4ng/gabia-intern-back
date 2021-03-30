package com.gmarket.api.domain.board.subclass.usedgoodsboard;

import com.gmarket.api.domain.board.enums.BoardStatus;
import com.gmarket.api.domain.board.subclass.usedgoodsboard.enums.UsedGoodsCategory;
import com.gmarket.api.domain.board.subclass.usedgoodsboard.enums.UsedGoodsStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsedGoodsBoardRepository extends JpaRepository<UsedGoodsBoard, Long> {

    // 게시글 상태 not null, 상품 카테고리 not null, 상품 상태 not null
    public Page<UsedGoodsBoard> findByStatusAndUsedGoodsCategoryAndUsedGoodsStatusAndTitleLikeAndSellPriceAfterAndSellPriceBefore(
            BoardStatus status,
            UsedGoodsCategory usedGoodsCategory,
            UsedGoodsStatus usedGoodsStatus,
            String title,
            Integer minPrice,
            Integer maxPrice,
            Pageable pageable
    );

    // 게시글 상태 not null, 상품 카테고리 not null
    public Page<UsedGoodsBoard> findByStatusAndUsedGoodsCategoryAndTitleLikeAndSellPriceAfterAndSellPriceBefore(
            BoardStatus status,
            UsedGoodsCategory usedGoodsCategory,
            String title,
            Integer minPrice,
            Integer maxPrice,
            Pageable pageable
    );

    // 게시글 상태 not null, 상품 상태 not null
    public Page<UsedGoodsBoard> findByStatusAndUsedGoodsStatusAndTitleLikeAndSellPriceAfterAndSellPriceBefore(
            BoardStatus status,
            UsedGoodsStatus usedGoodsStatus,
            String title,
            Integer minPrice,
            Integer maxPrice,
            Pageable pageable
    );

    // 상품 카테고리 not null, 상품 상태 not null
    public Page<UsedGoodsBoard> findByStatusNotAndUsedGoodsCategoryAndUsedGoodsStatusAndTitleLikeAndSellPriceAfterAndSellPriceBefore(
            BoardStatus status,
            UsedGoodsCategory usedGoodsCategory,
            UsedGoodsStatus usedGoodsStatus,
            String title,
            Integer minPrice,
            Integer maxPrice,
            Pageable pageable
    );



    // 상품 카테고리 not null
    public Page<UsedGoodsBoard> findByStatusNotAndUsedGoodsCategoryAndTitleLikeAndSellPriceAfterAndSellPriceBefore(
            BoardStatus status,
            UsedGoodsCategory usedGoodsCategory,
            String title,
            Integer minPrice,
            Integer maxPrice,
            Pageable pageable
    );


    // 상품 상태 not null
    public Page<UsedGoodsBoard> findByStatusNotAndUsedGoodsStatusAndTitleLikeAndSellPriceAfterAndSellPriceBefore(
            BoardStatus status,
            UsedGoodsStatus usedGoodsStatus,
            String title,
            Integer minPrice,
            Integer maxPrice,
            Pageable pageable
    );

    // 게시글 상태 not null
    public Page<UsedGoodsBoard> findByStatusAndTitleLikeAndSellPriceAfterAndSellPriceBefore(
            BoardStatus status,
            String title,
            Integer minPrice,
            Integer maxPrice,
            Pageable pageable
    );

    // 게시글 상태 null, 상품 카테고리 null, 상품 상태 null
    public Page<UsedGoodsBoard> findByStatusNotAndTitleLikeAndSellPriceAfterAndSellPriceBefore(
            BoardStatus status,
            String title,
            int minPrice,
            int maxPrice,
            Pageable pageable);
}
