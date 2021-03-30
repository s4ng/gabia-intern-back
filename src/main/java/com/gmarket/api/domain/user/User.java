package com.gmarket.api.domain.user;

import com.gmarket.api.domain.board.dto.BoardDto;
import com.gmarket.api.domain.user.dto.UserDto;
import com.gmarket.api.domain.user.dto.UserUpdateDto;
import com.gmarket.api.domain.user.enums.UserStatus;
import com.gmarket.api.domain.user.enums.UserType;
import com.gmarket.api.global.util.BaseTimeEntity;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED) // jpa 상속 - 조인전략
@DiscriminatorColumn(name="user_type") // 상속 타입 구분 컬럼 - DB
public abstract class User extends BaseTimeEntity {
    @Id // 식별자
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB increment 따름
    @Column(name = "user_id")
    private Long userId;

    private String gabiaId;

    private String name;

    private String password;

    private int point;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Transient // DB Column X
    private UserType userType; // UserType 확인을 위함

//    @OneToMany(mappedBy = "user") // 양방향 관계 설정
//    private List<Board> boardList = new ArrayList<>();

    public User dtoToEntity(UserDto userDto){
        this.gabiaId = userDto.getGabiaId();
        this.name = userDto.getName();
        this.password = userDto.getPassword();
        this.userType = userDto.getUserType();
        return this;
    }

    // 유저 가입 상태
    public void createdStatus(){
        this.status = UserStatus.CREATED;
    }

    // 유저 탈퇴 상태
    public void deletedStatus(){
        this.status = UserStatus.DELETED;
    }

    public void userIdInput(Long userId){
        this.userId = userId;
    }

    public void userTypeInput(UserType userType){
        this.userType = userType;
    }

    public void update(UserUpdateDto userUpdateDto){
        this.name = userUpdateDto.getName();
        this.password = userUpdateDto.getNewPassword();
    }

    // board 연관 관계
    public void boardDtoToSetId(BoardDto boardDto){
        this.userId = boardDto.getUserId();
    }

    public void addPoint(int point){
        this.point += point;
    }

}
