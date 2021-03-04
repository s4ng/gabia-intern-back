package com.gmarket.api.domain.raffle;

import com.gmarket.api.domain.board.presentgoodsboard.PresentGoodsBoard;
import com.gmarket.api.domain.board.presentgoodsboard.PresentGoodsBoardRepository;
import com.gmarket.api.domain.raffle.dto.RaffleMapper;
import com.gmarket.api.domain.raffle.dto.RaffleRequestDto;
import com.gmarket.api.domain.raffle.dto.RaffleResponseDto;
import com.gmarket.api.domain.user.User;
import com.gmarket.api.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RaffleService {

    private final RaffleRepository raffleRepository;
    private final PresentGoodsBoardRepository presentGoodsBoardRepository;
    private final UserRepository userRepository;

    public RaffleResponseDto save(RaffleRequestDto raffleRequestDto) {

        /** Exception Handling 수정본
         * 확인하고 넘어가기
         */
        PresentGoodsBoard presentBoard = presentGoodsBoardRepository
                .findById(raffleRequestDto.getPresentBoardId())
                .orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다."));
        User user = userRepository
                .findById(raffleRequestDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("유효하지않은 사용자입니다."));

        Raffle findResult = raffleRepository
                .findByPresentBoardAndParticipant(presentBoard, user)
                .orElse(null);

        // DELETE인 상태라면 CREATE로 바꾸고, CREATE 상태라면 그대로 다시 반환한다.
        // 없는 경우 새로 생성하여 저장한다.
       if(findResult != null){

             if (findResult.getStatus() == Raffle.Status.DELETE) {
                findResult.reInsert();
                raffleRepository.save(findResult);
                return RaffleMapper.INSTANCE.entityToDto(findResult);
            }

            return RaffleMapper.INSTANCE.entityToDto(findResult);
       }

        return RaffleMapper.INSTANCE.entityToDto(raffleRepository.save(Raffle.builder()
                .presentBoard(presentBoard)
                .participant(user)
                .status(Raffle.Status.CREATE).build()));

    }

    public List<RaffleResponseDto> findByPostId(Long postId) {

        PresentGoodsBoard presentBoard =
                presentGoodsBoardRepository.findById(postId).orElse(null);

        return raffleRepository.findAllByPresentBoard(presentBoard).stream()
                .filter(entity -> entity.getStatus() != Raffle.Status.DELETE)
                .map(RaffleMapper.INSTANCE::entityToDto)
                .collect(Collectors.toList());
    }

    public boolean delete(Long postId, Long userId) {

        PresentGoodsBoard presentBoard = presentGoodsBoardRepository
                .findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다."));
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("유효하지않은 사용자입니다."));

        Raffle findRaffle = raffleRepository
                .findByPresentBoardAndParticipant(presentBoard, user)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않습니다."));

        if(findRaffle.getStatus() == Raffle.Status.DELETE) {
            throw new EntityNotFoundException("이미 삭제되어있습니다.");
        }

        findRaffle.delete();
        raffleRepository.save(findRaffle);
        return true;
    }
}
