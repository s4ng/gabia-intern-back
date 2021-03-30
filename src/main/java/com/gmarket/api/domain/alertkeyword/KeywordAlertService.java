package com.gmarket.api.domain.alertkeyword;


import com.gmarket.api.domain.alert.Alert;
import com.gmarket.api.domain.alert.AlertRepository;
import com.gmarket.api.domain.alert.dto.AlertDto;
import com.gmarket.api.domain.alert.enums.AlertType;
import com.gmarket.api.domain.board.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

@RequiredArgsConstructor
public class KeywordAlertService implements Runnable{

    private final AlertRepository alertRepository;
    private final SimpMessagingTemplate messagingTemplate;

    private List<AlertKeyword> alertKeywordList; // 유저 정보

    private int seq; // 메시지를 보낼 순서

    private String message; // 보낼 메시지

    private Board board; // 게시글 정보

    public void userListAndSeqSetting(List<AlertKeyword> alertKeywordList, int seq, String message, Board board){
        this.alertKeywordList = alertKeywordList;
        this.seq = seq;
        this.message = message;
        this.board = board;
    }


    @Override
    public void run() {
        System.out.println("키워드 알림 쓰레드 생성");
        if( seq + 50 > alertKeywordList.size()){
            for(int i=seq; i<alertKeywordList.size(); i++){
                // 키워드 알림 웹 소켓 전송
                messagingTemplate.convertAndSend("/sub/alert/" + alertKeywordList.get(i).getUser().getUserId() ,
                        new AlertDto().entityToDto(
                                alertRepository.save(new Alert().createAlert(alertKeywordList.get(i).getUser(), board,
                                        message, AlertType.KEYWORD))
                        )
                );
            }
        }
        else {
            for(int i=seq; i<seq+50; i++){
                // 키워드 알림 웹 소켓 전송
                messagingTemplate.convertAndSend("/sub/alert/" + alertKeywordList.get(i).getUser().getUserId() ,
                        new AlertDto().entityToDto(
                                alertRepository.save(new Alert().createAlert(alertKeywordList.get(i).getUser(), board,
                                        message, AlertType.KEYWORD))
                        )
                );
            }
        }
    }


    @Async
    public void keywordAlertFor(List<AlertKeyword> alertKeywords, Board board){

        System.out.println("키워드 알림 for문 시작");
        for(int seq=0; seq<alertKeywords.size(); seq+=50){

            KeywordAlertService keywordAlertService =
                    new KeywordAlertService(alertRepository, messagingTemplate);

            keywordAlertService.userListAndSeqSetting(alertKeywords,
                    seq,
                    board.getTitle() + "글이 생성되었습니다",
                    board);

            keywordAlertService.run();
        }

        System.out.println("키워드 알림 for문 종료");

    }
}