package com.gmarket.api.domain.board.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.board.dto.subclass.NoticeBoardDto;
import com.gmarket.api.domain.board.dto.subclass.PresentGoodsBoardDto;
import com.gmarket.api.domain.board.dto.subclass.UsedGoodsBoardDto;
import com.gmarket.api.domain.board.enums.BoardStatus;
import com.gmarket.api.domain.board.enums.BoardType;
import com.gmarket.api.domain.board.subclass.presentgoodsboard.PresentGoodsBoard;
import com.gmarket.api.domain.board.subclass.presentgoodsboard.enums.PresentGoodsCategory;
import com.gmarket.api.domain.board.subclass.presentgoodsboard.enums.PresentGoodsStatus;
import com.gmarket.api.domain.board.subclass.usedgoodsboard.UsedGoodsBoard;
import com.gmarket.api.domain.board.subclass.usedgoodsboard.enums.UsedGoodsCategory;
import com.gmarket.api.domain.board.subclass.usedgoodsboard.enums.UsedGoodsStatus;
import com.gmarket.api.domain.user.enums.UserType;
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
public class BoardDto {

    private BoardType boardType;

    private UserType userType;

    private Long boardId;

    private Long userId;

    private String name;

    private BoardStatus status;

    private String title;

    private String description;

    public void entityToDto(Board board){
        this.userType = board.getUserType();
        this.boardId = board.getBoardId();
        this.userId = board.getUser().getUserId();
        this.name = board.getUser().getName();
        this.status = board.getStatus();
        this.title = board.getTitle();
        this.description = board.getDescription();
    }

}
