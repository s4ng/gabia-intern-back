package com.gmarket.api.domain.board.dto.subclass;

import com.gmarket.api.domain.board.dto.BoardDto;
import com.gmarket.api.domain.board.enums.BoardType;
import com.gmarket.api.domain.board.subclass.noticeboard.NoticeBoard;
import com.gmarket.api.domain.board.subclass.noticeboard.enums.NoticeCategory;
import com.gmarket.api.domain.user.enums.UserType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NoticeBoardDto extends BoardDto {

    private NoticeCategory noticeCategory;

    public NoticeBoardDto noticeGoodsBoardToDto(NoticeBoard noticeBoard){
        entityToDto(noticeBoard);
        this.setUserType(UserType.MANAGER);
        this.noticeCategory = noticeBoard.getNoticeCategory();
        return this;
    }

    public NoticeBoardDto(){
        this.setBoardType(BoardType.NOTICE);
    }
}
