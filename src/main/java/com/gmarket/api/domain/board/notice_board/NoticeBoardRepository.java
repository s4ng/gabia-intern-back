package com.gmarket.api.domain.board.notice_board;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;

@Repository
public interface NoticeBoardRepository extends PagingAndSortingRepository<NoticeBoard, Long> {
}