package com.gmarket.api.domain.raffle;

import com.gmarket.api.domain.alert.Alert;
import com.gmarket.api.domain.alert.AlertRepository;
import com.gmarket.api.domain.alert.dto.AlertDto;
import com.gmarket.api.domain.alert.enums.AlertType;
import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.board.BoardRepositoryInterface;
import com.gmarket.api.domain.board.enums.BoardStatus;
import com.gmarket.api.domain.board.enums.BoardType;
import com.gmarket.api.domain.board.subclass.presentgoodsboard.PresentGoodsBoard;
import com.gmarket.api.domain.raffle.dto.RaffleDto;
import com.gmarket.api.domain.raffle.enums.RaffleStatus;
import com.gmarket.api.domain.user.User;
import com.gmarket.api.domain.user.UserRepository;
import com.gmarket.api.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RaffleService{

    private final RaffleRepository raffleRepository;
    private final BoardRepositoryInterface boardRepositoryInterface;
    private final UserRepository userRepository;
    private final AlertRepository alertRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Transactional
    public RaffleDto save(RaffleDto raffleDto) {

        /** Exception Handling 수정본
         * 확인하고 넘어가기
         */
        Board presentBoard = boardRepositoryInterface
                .findById(raffleDto.getBoardId())
                .orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다."));
        User user = userRepository
                .findById(raffleDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("유효하지않은 사용자입니다."));

        userRepository.save(user);

        Raffle findResult = raffleRepository
                .findByBoardAndUser(presentBoard, user)
                .orElse(null);

        // DELETE인 상태라면 CREATE로 바꾸고, CREATE 상태라면 그대로 다시 반환한다.
        // 없는 경우 새로 생성하여 저장한다.
        if(findResult != null){

            if (findResult.getStatus() == RaffleStatus.DELETED) {
                findResult.reInsert();
                raffleRepository.save(findResult);
                return raffleDto.entityToDto(findResult);
            }

            return raffleDto.entityToDto(findResult);
        }

        return raffleDto.entityToDto(raffleRepository.save(Raffle.builder()
                .board(presentBoard)
                .user(user)
                .status(RaffleStatus.CREATED).build()));

    }

    public List<RaffleDto> findByPostId(Long postId) {

        Board presentBoard =
                boardRepositoryInterface.findById(postId).orElseThrow(
                        () -> new EntityNotFoundException("존재하지 않는 게시글입니다"));

        return raffleRepository.findAllByBoard(presentBoard).stream()
                .filter(entity -> entity.getStatus() !=  RaffleStatus.DELETED)
                .map(entity -> new RaffleDto().entityToDto(entity))
                .collect(Collectors.toList());
    }

    public void delete(Long postId, Long userId) {

        Board presentBoard = boardRepositoryInterface
                .findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다."));
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("유효하지않은 사용자입니다."));

        userRepository.save(user);

        Raffle findRaffle = raffleRepository
                .findByBoardAndUser(presentBoard, user)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않습니다."));

        if(findRaffle.getStatus() == RaffleStatus.DELETED) {
            throw new EntityNotFoundException("이미 삭제되어있습니다.");
        }

        findRaffle.delete();
        raffleRepository.save(findRaffle);
    }

    // 래플 종료라는 메서드가 실행되어져야 할 때를 알수 있는 것은, 나눔 게시글에 래플 종료 시간이 입력되어 저장되는 순간
    // 따라서 raffleClose가 비동기적으로 실행되어야하므로 @Async 어노테이션을 활용
    // 그 중에 당첨자를 뽑아내서 당첨자 정보를 나눔 게시글 내용에 추가하고, 상태 CLOSED 하여 DB에 저장
    // 래플 참여자들 전원 모두에게 알림을 보냄, 알림은 DB에 저장되어져야하고, 로그인한 사람들 중에 보내야함.
    @Async
    public void raffleClose(Board board) throws InterruptedException {


        try {
            PresentGoodsBoard presentGoodsBoard = (PresentGoodsBoard) board;

            Thread.sleep( ChronoUnit.MILLIS.between(LocalDateTime.now(),presentGoodsBoard.getRaffleClosedAt()));

            Board raffleBoard = boardRepositoryInterface.findById(board.getBoardId())
                    .orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다."));

            if(raffleBoard.getStatus().equals(BoardStatus.DELETED)){
                throw new EntityNotFoundException("게시글이 삭제되어있습니다.");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
            throw e;
        }

        List<Raffle> raffleList = raffleRepository.findAllByBoard(board);

        if(raffleList.size() == 0){
            throw new EntityNotFoundException("래플 참여자가 없습니다");
        }

        raffleRepository.raffleLose(RaffleStatus.LOSE, board);

        List<User> userList = raffleList
                .stream()
                .map( raffle ->  raffle.getUser())
                .collect(Collectors.toList());

        User raffleUser = raffleComplete(userList);

        raffleRepository.raffleWin(RaffleStatus.WIN, board, raffleUser);

        List<Raffle> raffleList1 = raffleRepository.findAllByBoard(board);

        board.closedStatus();

        boardRepositoryInterface.save(board);

        User user = board.getUser();

        user.addPoint(30);

        userRepository.save(user);

        for(Raffle raffle:raffleList1){
            Alert alert = new Alert();
            if(raffle.getStatus().equals(RaffleStatus.LOSE)){
                alert.createAlert(
                        raffle.getUser(),
                        raffle.getBoard(),
                        "래플 신청하신 "+raffle.getBoard().getTitle()+" 게시글에서 래플 미당첨되셨습니다.",
                        AlertType.RAFFLE, BoardType.PRESENT );
            } else if(raffle.getStatus().equals(RaffleStatus.WIN)) {
                alert.createAlert(
                        raffle.getUser(),
                        raffle.getBoard(),
                        "래플 신청하신 "+raffle.getBoard().getTitle()+" 게시글에서 래플 당첨되셨습니다.",
                        AlertType.RAFFLE , BoardType.PRESENT);
            }

            alertRepository.save(alert);

            AlertDto alertDto = new AlertDto().entityToDto(alert);

            if(board.getClass().getSimpleName().equals("NoticeBoard")){
                alertDto.setBoardType(BoardType.NOTICE);
            }
            else if(board.getClass().getSimpleName().equals("UsedGoodsBoard")){
                alertDto.setBoardType(BoardType.USED);
            }
            else if(board.getClass().getSimpleName().equals("PresentGoodsBoard")){
                alertDto.setBoardType(BoardType.PRESENT);
            }

            // 래플 알림 웹 소켓 전송
            messagingTemplate.convertAndSend(
                    "/sub/alert/" + raffle.getUser().getUserId() , alertDto);
        }

    }


    public User raffleComplete(List<User> userList){

        int allPoint = 0;

        for(User user: userList){
            user.addPoint(10);
            userRepository.save(user);
            allPoint += user.getPoint();
        }

        Random random = new Random();

        int rand = random.nextInt(allPoint+1);

        for(User user: userList){
            rand -= user.getPoint();
            if(rand <= 0){
                return user;
            }
        }
        return userList.get(userList.size()-1);
    }
}
