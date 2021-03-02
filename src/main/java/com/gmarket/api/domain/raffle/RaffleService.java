package com.gmarket.api.domain.raffle;

import com.gmarket.api.domain.board.presentgoodsboard.PresentGoodsBoard;
import com.gmarket.api.domain.board.presentgoodsboard.PresentGoodsBoardRepository;
import com.gmarket.api.domain.raffle.dto.RaffleRequestDto;
import com.gmarket.api.domain.raffle.dto.RaffleResponseDto;
import com.gmarket.api.domain.user.User;
import com.gmarket.api.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

        // 나눔글이나 유저가 존재하지 않는다면 null 반환
        if(presentBoard == null || user == null) {
            return null;
        }

        // 래플을 user와 글 entity로 찾아본다.
        // 이 경우 pk로 찾는 경우가 아니기 때문에 리스트로 반환되게 된다.
        List<Raffle> raffleList = raffleRepository.findByPresentBoardAndParticipant(presentBoard, user);

        // Raffle이 존재하는지 확인. 존재하면 그 raffle의 pk로 다시 한 번 검색을 시작한다.
        // DELETE인 상태라면 CREATE로 바꾸고, CREATE 상태라면 그대로 다시 반환한다.
        // 없는 경우 새로 생성하여 저장한다.
        if(!raffleList.isEmpty()) {

            Raffle findResult = raffleRepository.findById(raffleList.get(0).getRaffleId()).orElse(null);

            if (findResult.getStatus() == Raffle.Status.DELETE) {
                findResult.reInsert();
                raffleRepository.save(findResult);
                return entityToDto(findResult);
            } else {
                return entityToDto(findResult);
            }
        } else {

            return entityToDto(raffleRepository.save(Raffle.builder()
                    .presentBoard(presentBoard)
                    .participant(user)
                    .status(Raffle.Status.CREATE).build()));
        }
    }

    public List<RaffleResponseDto> findByPostId(Long postId) {

        List<RaffleResponseDto> responseDtoList = new ArrayList<>();

        PresentGoodsBoard presentBoard =
                presentGoodsBoardRepository.findById(postId).orElse(null);

        raffleRepository.findAllByPresentBoard(presentBoard).forEach(entity -> {
                    if(entity.getStatus() == Raffle.Status.DELETE) return;
                    responseDtoList.add(entityToDto(entity));
                }
        );

        return responseDtoList;
    }

    public boolean delete(Long postId, Long userId) {

        PresentGoodsBoard presentBoard =
                presentGoodsBoardRepository.findById(postId).orElse(null);
        User user =
                userRepository.findById(userId).orElse(null);

        long raffleId;
        List<Raffle> raffleList = raffleRepository.findByPresentBoardAndParticipant(presentBoard, user);
        if(!raffleList.isEmpty()) {
            raffleId = raffleList.get(0).getRaffleId();
        } else {
            return false;
        }
        Raffle findRaffle = raffleRepository.findById(raffleId).orElse(null);

        if(findRaffle == null || findRaffle.getStatus() == Raffle.Status.DELETE) {
            return false;
        }

        findRaffle.delete();
        raffleRepository.save(findRaffle);
        return true;
    }

    public RaffleResponseDto entityToDto(Raffle raffle) {
        return RaffleResponseDto.builder()
                .id(raffle.getRaffleId())
                .presentBoardId(raffle.getPresentBoard().getBoardId())
                .userId(raffle.getParticipant().getUserId())
                .build();
    }
}
