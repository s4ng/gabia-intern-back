package com.gmarket.api.domain.raffle;

import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RaffleRepository extends JpaRepository<Raffle, Long> {

    List<Raffle> findByPresentBoardAndParticipant(Board presentBoard, User participant);

    List<Raffle> findAllByPresentBoard(Board presentBoard);
}
