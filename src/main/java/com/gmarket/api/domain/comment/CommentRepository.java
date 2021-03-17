package com.gmarket.api.domain.comment;

import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.comment.enums.CommentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    public Page<Comment> findByBoardAndStatusNot(Board board, CommentStatus commentStatus, Pageable pageable);

    public int countByBoard(Board board);

}