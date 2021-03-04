package com.gmarket.api.domain.raffle;

import com.gmarket.api.domain.raffle.dto.RaffleRequestDto;
import com.gmarket.api.domain.raffle.dto.RaffleResponseDto;
import com.gmarket.api.global.util.ResponseWrapperDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "raffles")
@RequiredArgsConstructor
public class RaffleController {

    private final RaffleService raffleService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ResponseWrapperDto> saveRaffle(@RequestBody RaffleRequestDto raffleRequestDto) {

        RaffleResponseDto saveResult;
        try {
            saveResult = raffleService.save(raffleRequestDto);
        } catch(EntityNotFoundException e) {
            return new ResponseEntity<>(ResponseWrapperDto.builder().data("Save failed").build(),
                    HttpStatus.NOT_FOUND);
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

        try {
            raffleService.delete(postId, userId);
        } catch(EntityNotFoundException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Cannot found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
