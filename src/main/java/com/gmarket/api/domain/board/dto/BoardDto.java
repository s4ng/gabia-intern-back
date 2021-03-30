package com.gmarket.api.domain.board.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.board.dto.subclass.NoticeBoardDto;
import com.gmarket.api.domain.board.dto.subclass.PresentGoodsBoardDto;
import com.gmarket.api.domain.board.dto.subclass.UsedGoodsBoardDto;
import com.gmarket.api.domain.board.enums.BoardStatus;
import com.gmarket.api.domain.board.enums.BoardType;
import com.gmarket.api.domain.user.enums.UserType;
import com.gmarket.api.global.util.JsonViews;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@JsonTypeInfo( // controller 에서 SubClass 로 json 주입 받기 위해
        use= JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "board_type" // "board_type": "NOTICE" 일시 NoticeBoardDto
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = NoticeBoardDto.class, name = BoardType.Values.NOTICE),
        @JsonSubTypes.Type(value = UsedGoodsBoardDto.class, name = BoardType.Values.USED),
        @JsonSubTypes.Type(value = PresentGoodsBoardDto.class, name = BoardType.Values.PRESENT)
})
@JsonView(JsonViews.Response.class)
public class BoardDto{

    private BoardType boardType;

    @JsonView(JsonViews.Request.class)
    private UserType userType;

    private Long boardId;

    private Long userId;

    private String name;

    private BoardStatus status;

    private String title;

    private String description;

    private int viewCount;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public BoardDto entityToDto(Board board){
        this.userType = board.getUserType();
        this.boardId = board.getBoardId();
        this.userId = board.getUser().getUserId();
        this.name = board.getUser().getName();
        this.status = board.getStatus();
        this.title = board.getTitle();
        this.description = board.getDescription();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.viewCount = board.getViewCount();
        return this;
    }

}
