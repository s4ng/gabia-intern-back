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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RaffleService {

    private final RaffleRepository raffleRepository;
    private final PresentGoodsBoardRepository presentGoodsBoardRepository;
    private final UserRepository userRepository;

    public RaffleResponseDto save(RaffleRequestDto raffleRequestDto) {

        PresentGoodsBoard presentBoard =
                presentGoodsBoardRepository.findById(raffleRequestDto.getPresentBoardId()).orElse(null);
        User user =
                userRepository.findById(raffleRequestDto.getUserId()).orElse(null);

        /** Exception Handling 수정본
         * 확인하고 넘어가기
         *
         * PresentGoodsBoard presentBoard = presentGoodsBoardRepository.findById(raffleRequestDto.getPresentBoardId()).orElseThrow(() -> new NotFoundException("게시글이 존재하지 않습니다."));
         * User user = userRepository.findById(raffleRequestDto.getUserId()).orElseThrow(() -> new NotFoundException("유효하지않은 사용자입니다."));
         */

        // 나눔글이나 유저가 존재하지 않는다면 null 반환
        if(presentBoard == null || user == null) {
            return null;
        }

        Raffle findResult = raffleRepository.findByPresentBoardAndParticipant(presentBoard, user).orElse(null);

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

        PresentGoodsBoard presentBoard =
                presentGoodsBoardRepository.findById(postId).orElse(null);
        User user =
                userRepository.findById(userId).orElse(null);

        Raffle findRaffle = raffleRepository.findByPresentBoardAndParticipant(presentBoard, user).orElse(null);

        if(findRaffle == null || findRaffle.getStatus() == Raffle.Status.DELETE) {
            return false;
        }

        findRaffle.delete();
        raffleRepository.save(findRaffle);
        return true;
    }
}
