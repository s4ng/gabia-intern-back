package com.gmarket.api.domain.alertkeyword;

import com.gmarket.api.domain.alertkeyword.enums.AlertKeywordStatus;
import com.gmarket.api.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertKeywordRepository extends JpaRepository<AlertKeyword, Long> {

    public List<AlertKeyword> findByUserAndStatus(User user, AlertKeywordStatus alertKeywordStatus);

    public AlertKeyword findByUserAndKeyword(User user, String keyword);


    @Query("select m FROM AlertKeyword m where LOCATE(  m.keyword, ?1  ) > 0 AND m.status = ?2")
    public List<AlertKeyword> findLocateKeyword(String boardTitle, AlertKeywordStatus alertKeywordStatus);
}
