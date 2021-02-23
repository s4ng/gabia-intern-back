package com.gmarket.api.domain.board.presentgoodsboard;

import com.gmarket.api.domain.board.noticeboard.NoticeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PresentGoodsBoardRepository extends JpaRepository<PresentGoodsBoard, Long> {

    @Query("select p from PresentGoodsBoard p where p.status <> com.gmarket.api.domain.board.Board$Status.DELETE")
    @Override
    List<PresentGoodsBoard> findAll();

    @Query("select p from PresentGoodsBoard p where p.status <> com.gmarket.api.domain.board.Board$Status.DELETE")
    @Override
    Page<PresentGoodsBoard> findAll(Pageable pageable);
}
