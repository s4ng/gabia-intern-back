package com.gmarket.api.domain.board.subclass.noticeboard;

import com.gmarket.api.domain.board.dto.subclass.NoticeBoardDto;
import com.gmarket.api.domain.board.enums.BoardStatus;
import com.gmarket.api.domain.board.enums.BoardType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeBoardService {

    private final NoticeBoardRepository noticeBoardRepository;

    public Map findPage(BoardType boardType, int page){

        Map map = new HashMap();

        PageRequest pageRequest = PageRequest.of(page-1, 10, Sort.by( Sort.Direction.DESC, "boardId"));

        Page<NoticeBoard> noticeBoardPage =  noticeBoardRepository.findByStatusNot(BoardStatus.DELETED, pageRequest);

        map.put(
                "board_list", noticeBoardPage
                        .getContent()
                        .stream()
                        .map(noticeBoard -> new NoticeBoardDto().entityToDto(noticeBoard))
                        .collect(Collectors.toList())
        );

        int pageCount = noticeBoardPage.getTotalPages();

        return map;
    }
}
