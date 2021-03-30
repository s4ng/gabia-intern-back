package com.gmarket.api.domain.alertkeyword;

import com.gmarket.api.domain.alertkeyword.dto.AlertKeywordDto;
import com.gmarket.api.domain.alertkeyword.enums.AlertKeywordStatus;
import com.gmarket.api.domain.user.User;
import com.gmarket.api.domain.user.UserRepository;
import com.gmarket.api.domain.user.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlertKeywordService {

    private final AlertKeywordRepository alertKeywordRepository;

    private final UserRepository userRepository;
    @Transactional
    public AlertKeywordDto save(AlertKeywordDto alertKeywordDto){

        if(alertKeywordDto.getUserId()==null){
            throw new IllegalStateException("유저 식별 값이 입력되지 않았습니다");
        }

        User user = userRepository.findById(alertKeywordDto.getUserId())
                .orElseThrow( () -> new IllegalStateException("존재하지 않은 회원입니다"));

        if(user.getStatus().equals(UserStatus.DELETED)){
            throw new IllegalStateException("이미 탈퇴한 유저입니다");
        }


        AlertKeyword alertKeyword = alertKeywordRepository.findByUserAndKeyword(user,
                alertKeywordDto.getKeyword());

        if(alertKeyword == null ){
            AlertKeyword alertKeyword1 = new AlertKeyword();
            alertKeyword1.dtoToEntity(alertKeywordDto);
            alertKeyword1.createdSetting(user);

            return alertKeywordDto.entityToDto(alertKeywordRepository.save(alertKeyword1));
        }

        else {
            if(alertKeyword.getStatus().equals(AlertKeywordStatus.CREATED)){
                throw new IllegalStateException("이미 등록한 알림 키워드 입니다");
            }
            alertKeyword.createdStatus();
            return alertKeywordDto.entityToDto(alertKeywordRepository.save(alertKeyword));
        }
    }

    public List<AlertKeywordDto> list(Long userId){
        if(userId==null){
            throw new IllegalStateException("유저 식별 값이 입력되지 않았습니다");
        }

        Optional<User> optionalUser = userRepository.findById(userId);

        if(optionalUser.isEmpty()){
            throw new IllegalStateException("존재 하지 않는 유저 입니다");
        }

        if(optionalUser.get().getStatus().equals(UserStatus.DELETED)){
            throw new IllegalStateException("이미 탈퇴한 유저입니다");
        }

        List<AlertKeyword> alertKeywordList = alertKeywordRepository.findByUserAndStatus(optionalUser.get(), AlertKeywordStatus.CREATED);

        List<AlertKeywordDto> alertKeywordDtoList = new ArrayList<>();

        for(AlertKeyword alertKeyword: alertKeywordList){
            AlertKeywordDto alertKeywordDto = new AlertKeywordDto();
            alertKeywordDtoList.add(alertKeywordDto.entityToDto(alertKeyword));
        }

        return alertKeywordDtoList;
    }

    @Transactional
    public void delete(Long id){

        Optional<AlertKeyword> optionalAlertKeyword = alertKeywordRepository.findById(id);

        if(optionalAlertKeyword.isEmpty()){
            throw new IllegalStateException("식별 되지 않는 키워드 입니다.");
        }

        if(optionalAlertKeyword.get().getStatus().equals(AlertKeywordStatus.DELETED)){
            throw new IllegalStateException("이미 삭제 처리 된 키워드 입니다.");
        }

        AlertKeyword alertKeyword = optionalAlertKeyword.get();

        alertKeyword.deletedStatus();

        alertKeywordRepository.save(alertKeyword);
    }
}
