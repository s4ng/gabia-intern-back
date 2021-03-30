package com.gmarket.api.domain.attentiongoods;

import com.gmarket.api.domain.attentiongoods.enums.AttentionGoodsStatus;
import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttentionGoodsRepositoryInterface extends JpaRepository<AttentionGoods, Long> {

    public List<AttentionGoods> findByStatusAndUser(AttentionGoodsStatus attentionGoodsStatus, User user);

    public AttentionGoods findByUserAndBoard(User user,Board board);
}
