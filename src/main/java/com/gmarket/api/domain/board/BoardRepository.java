package com.gmarket.api.domain.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository // Bean Component
@RequiredArgsConstructor // final 선언한 변수를 매개 변수로 받는 생성자 만듬, @NonNull 필드 값만 파라미터로 받는 생성자를 만듬
public class BoardRepository {

    private final EntityManager em;

    public List<Board> findBoardPage(String sql, int pageNumber) {
        return em.createQuery(sql, Board.class)
                .setFirstResult((pageNumber-1)*10)
                .setMaxResults(10)
                .getResultList();
    }

    public Board findPost(String sql) {
        Board board = em.createQuery(sql, Board.class).getSingleResult();
        board.addViewCount();
        return board;
    }

    public Board createPost(Board board) {
        em.persist(board);
        return board;
    }

    public Board updatePost(Board board) {
        return em.find(board.getClass(), board.getBoardId());
    }

    public Long deletePost(Board board) {
        em.find(board.getClass(), board.getBoardId());
        return board.getBoardId();
    }
}
