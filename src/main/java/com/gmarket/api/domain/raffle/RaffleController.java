package com.gmarket.api.domain.raffle;

import com.gmarket.api.domain.raffle.dto.RaffleRequestDto;
import com.gmarket.api.domain.raffle.dto.RaffleResponseDto;
import com.gmarket.api.global.util.ResponseWrapperDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "raffles")
@RequiredArgsConstructor
public class RaffleController {

    private final RaffleService raffleService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ResponseWrapperDto> saveRaffle(@RequestBody RaffleRequestDto raffleRequestDto) {

        RaffleResponseDto saveResult = raffleService.save(raffleRequestDto);
        if(saveResult == null) {
            return new ResponseEntity<>(ResponseWrapperDto.builder().data("Save failed").build(),
                    HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(ResponseWrapperDto.builder().data(saveResult).build(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseWrapperDto> getRaffleByPostId(@PathVariable Long id) {

        ResponseWrapperDto wrapperDto = ResponseWrapperDto.builder().data(raffleService.findByPostId(id)).build();
        return new ResponseEntity<>(wrapperDto, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteRaffle(
            @RequestParam(name = "postid", required = true) Long postId,
            @RequestParam(name = "userid", required = true) Long userId) {

        if(!raffleService.delete(postId, userId)) {
            // 삭제 실패
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Cannot found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            // 삭제 성공
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
