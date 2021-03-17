package com.gmarket.api.domain.alert;

import com.gmarket.api.domain.alert.enums.AlertType;
import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {

    public List<Alert> findByUserOrderByAlertIdDesc(User user);

    public Alert findTop1ByUserAndAlertTypeAndBoard(User user, AlertType alertType, Board board);

    @Transactional
    @Modifying
    @Query("UPDATE Alert m SET m.status = 'READ' WHERE m.status = 'YET' AND m.user = ?1")
    public void updateStatus(User user);

}
