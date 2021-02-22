package com.gmarket.api.domain.board.noticeboard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Long> {

    @Query("select n from NoticeBoard n where n.status <> com.gmarket.api.domain.board.Board$Status.DELETE")
    @Override
    List<NoticeBoard> findAll();

    @Query("select n from NoticeBoard n where n.status <> com.gmarket.api.domain.board.Board$Status.DELETE")
    @Override
    Page<NoticeBoard> findAll(Pageable pageable);

}