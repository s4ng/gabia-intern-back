package com.gmarket.api.domain.board.subclass.presentgoodsboard;

import com.gmarket.api.domain.board.enums.BoardStatus;
import com.gmarket.api.domain.board.subclass.presentgoodsboard.enums.PresentGoodsCategory;
import com.gmarket.api.domain.board.subclass.presentgoodsboard.enums.PresentGoodsStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PresentGoodsBoardRepository extends JpaRepository<PresentGoodsBoard, Long> {

    // 게시글 상태 not null, 상품 카테고리 not null, 상품 상태 not null
    public Page<PresentGoodsBoard> findByStatusAndPresentGoodsCategoryAndPresentGoodsStatusAndTitleLike(
            BoardStatus status,
            PresentGoodsCategory presentGoodsCategory,
            PresentGoodsStatus presentGoodsStatus,
            String title,
            Pageable pageable
    );

    // 게시글 상태 not null, 상품 카테고리 not null
    public Page<PresentGoodsBoard> findByStatusAndPresentGoodsCategoryAndTitleLike(
            BoardStatus status,
            PresentGoodsCategory presentGoodsCategory,
            String title,
            Pageable pageable
    );

    // 게시글 상태 not null, 상품 상태 not null
    public Page<PresentGoodsBoard> findByStatusAndPresentGoodsStatusAndTitleLike(
            BoardStatus status,
            PresentGoodsStatus presentGoodsStatus,
            String title,
            Pageable pageable
    );

    // 상품 카테고리 not null, 상품 상태 not null
    public Page<PresentGoodsBoard> findByStatusNotAndPresentGoodsCategoryAndPresentGoodsStatusAndTitleLike(
            BoardStatus status,
            PresentGoodsCategory presentGoodsCategory,
            PresentGoodsStatus presentGoodsStatus,
            String title,
            Pageable pageable
    );


    // 게시글 상태 not null
    public Page<PresentGoodsBoard> findByStatusAndTitleLike(
            BoardStatus status,
            String title,
            Pageable pageable
    );


    // 상품 카테고리 not null
    public Page<PresentGoodsBoard> findByStatusNotAndPresentGoodsCategoryAndTitleLike(
            BoardStatus status,
            PresentGoodsCategory presentGoodsCategory,
            String title,
            Pageable pageable
    );

    // 상품 상태 not null
    public Page<PresentGoodsBoard> findByStatusNotAndPresentGoodsStatusAndTitleLike(
            BoardStatus status,
            PresentGoodsStatus presentGoodsStatus,
            String title,
            Pageable pageable
    );

    // 게시글 상태 null, 상품 카테고리 null, 상품 상태 null
    public  Page<PresentGoodsBoard> findByStatusNotAndTitleLike(BoardStatus boardStatus,
                                                                String title,
                                                                Pageable pageable);
}


