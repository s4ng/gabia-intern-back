package com.gmarket.api.domain.raffle;

import com.fasterxml.jackson.annotation.JsonView;
import com.gmarket.api.domain.raffle.dto.RaffleDto;
import com.gmarket.api.global.util.JsonViews;
import com.gmarket.api.global.util.ResponseWrapperDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "raffles")
@RequiredArgsConstructor
public class RaffleController {

    private final RaffleService raffleService;

    @JsonView(JsonViews.Response.class)
    @PostMapping(consumes = "application/json")
    public ResponseEntity<ResponseWrapperDto> saveRaffle(@RequestBody RaffleDto raffleDto) {

        return ResponseEntity.ok(ResponseWrapperDto.builder().data(raffleService.save(raffleDto)).build());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseWrapperDto> getRaffleByPostId(@PathVariable Long id) {

        ResponseWrapperDto wrapperDto = ResponseWrapperDto.builder().data(raffleService.findByPostId(id)).build();
        return ResponseEntity.ok(wrapperDto);
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteRaffle(
            @RequestParam(name = "postId", required = true) Long postId,
            @RequestParam(name = "userId", required = true) Long userId) {

        raffleService.delete(postId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
