package com.gmarket.api.domain.board.notice_board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeBoardRepository extends PagingAndSortingRepository<NoticeBoard, Long> {

    @Query("select n from NoticeBoard n where n.deletedTime is null")
    @Override
    List<NoticeBoard> findAll();

    @Query("select n from NoticeBoard n where n.deletedTime is null")
    @Override
    Page<NoticeBoard> findAll(Pageable pageable);

}