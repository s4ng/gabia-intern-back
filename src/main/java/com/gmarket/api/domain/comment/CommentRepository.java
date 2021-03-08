package com.gmarket.api.domain.comment;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository // Bean Component
@RequiredArgsConstructor // @RequiredArgsConstructor 어노테이션은 final, @NonNull 필드 값만 파라미터로 받는 생성자를 만듬
public class CommentRepository {
    private final EntityManager em;

    public List<Comment> findPostComments(String sql) {
        em.createQuery("select m from NoticeComment m");
        return em.createQuery(sql, Comment.class)
                .getResultList();
    }

    public Comment findComment(long commentId) {
        return em.find(Comment.class, commentId);
    }

    public Comment updateComment(Comment comment){
        return em.find(comment.getClass(), comment.getCommentId());
    }

    public Long deleteComment(Comment comment){
        em.find(comment.getClass(), comment.getCommentId());
        return comment.getCommentId();
    }
}
