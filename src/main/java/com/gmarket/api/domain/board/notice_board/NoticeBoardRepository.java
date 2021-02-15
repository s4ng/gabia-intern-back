package com.gmarket.api.domain.board.notice_board;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeBoardRepository extends CrudRepository<NoticeBoard, Long> {
}