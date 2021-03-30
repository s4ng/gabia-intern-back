package com.gmarket.api.domain.board.subclass.noticeboard;

import com.gmarket.api.domain.board.enums.BoardStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Long> {

    public Page<NoticeBoard> findByStatusNot(BoardStatus boardStatus, Pageable pageable);
}
