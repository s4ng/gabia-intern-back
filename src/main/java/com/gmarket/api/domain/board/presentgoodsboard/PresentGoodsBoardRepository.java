package com.gmarket.api.domain.board.presentgoodsboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PresentGoodsBoardRepository extends JpaRepository<PresentGoodsBoard, Long> {
}
