package com.gmarket.api.domain.raffle;

import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.raffle.enums.RaffleStatus;
import com.gmarket.api.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface RaffleRepository extends JpaRepository<Raffle, Long> {

    Optional<Raffle> findByBoardAndUser(Board presentBoard, User participant);

    List<Raffle> findAllByBoard(Board presentBoard);

    @Transactional
    @Modifying
    @Query("UPDATE Raffle m SET m.status = ?1 WHERE m.board = ?2")
    public void raffleLose(RaffleStatus raffleStatus, Board board);

    @Transactional
    @Modifying
    @Query("UPDATE Raffle m SET m.status = ?1 WHERE m.board = ?2 AND m.user =?3")
    public void raffleWin(RaffleStatus raffleStatus, Board board, User user);

}
