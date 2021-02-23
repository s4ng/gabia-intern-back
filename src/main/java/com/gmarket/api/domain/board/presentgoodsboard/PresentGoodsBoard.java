package com.gmarket.api.domain.board.presentgoodsboard;

import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.board.presentgoodsboard.dto.PresentGoodsBoardRequestDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("PRESENT")
@Setter
@Getter
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "present_board_id")
public class PresentGoodsBoard extends Board {

    @Enumerated(EnumType.STRING)
    private GoodsCategory goodsCategory;

    @Enumerated(EnumType.STRING)
    private GoodsStatus goodsStatus;

    private LocalDateTime raffleCloseAt;

    public enum GoodsCategory {
        DIGITAL, TICKET
    }

    public enum GoodsStatus {
        NEW, ALMOST, USED
    }

    public void setRaffleCloseAt(PresentGoodsBoardRequestDto requestDto) {
        LocalDateTime dateTime = LocalDateTime.of(
                requestDto.getRaffleCloseAtYear(),
                requestDto.getRaffleCloseAtMonth(),
                requestDto.getRaffleCloseAtDate(),
                requestDto.getRaffleCloseAtHour(),
                requestDto.getRaffleCloseAtMinute()
        );
        this.raffleCloseAt = dateTime;
    }
}
