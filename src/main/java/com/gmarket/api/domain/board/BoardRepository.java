package com.gmarket.api.domain.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor // final -> 생성자
public class BoardRepository {

    private final EntityManager em;

    // 다형성 구현을 위해서는 JPA 쿼리가 Board 엔티티가 아닌, Board SubType 엔티티를 조회해야 합니다.
    // 또한, 가급적 쿼리를 사용하지 않고 JPA에서 제공하는 메서드로 Repository를 구현하고자 합니다.
    // Spring data JPA를 활용한 Repository에서 Board 엔티티가 아닌 Board SubType 엔티티로 조회하는 메서드 구현의 어려움으로 인해
    // 현재로서는 EntityManager 메서드를 이용하여 처리하였습니다.
    // 추후에 공부하여 Spring data jpa Repository의 메서드로 Board SubType 엔티티로 조회하도록 변경할 예정입니다.(또는 QueryDsl)

    public Board findById(Board board, Long boardId){
        return em.find(board.getClass(), boardId);
    }

    public List<Board> findPage(Board board, int page){
        return (List<Board>) em.createQuery("SELECT m FROM "+board.getClass().getSimpleName()+
                " m WHERE m.status != 'DELETED' ORDER BY m.boardId DESC").
                setFirstResult((page-1)*10).setMaxResults(10).getResultList();
    }
}
