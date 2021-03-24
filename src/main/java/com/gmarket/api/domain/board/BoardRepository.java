package com.gmarket.api.domain.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor // final -> 생성자
public class BoardRepository {

    private final EntityManager em;

    public List<Board> findPage(Board board, int page){
        return (List<Board>) em.createQuery("SELECT m FROM "+board.getClass().getSimpleName()+
                " m WHERE m.status != 'DELETED' ORDER BY m.boardId DESC").
                setFirstResult((page-1)*10).setMaxResults(10).getResultList();
    }

    public Long findBoardCount(Board board){
        return (Long) em.createQuery("SELECT COUNT(m.boardId) FROM "+board.getClass().getSimpleName()+
                " m WHERE m.status != 'DELETED'").getSingleResult();
    }
}
