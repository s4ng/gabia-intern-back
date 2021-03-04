package com.gmarket.api.domain.user.dto;

import com.gmarket.api.domain.user.User;
import com.gmarket.api.domain.user.member.Member;

public class MemberDto extends UserDto{

    @Override
    public User loginDtoToEntity(){
        Member member = new Member();
        member.loginDto( this );
        return member;
    }

    @Override
    public User joinDtoToEntity() {
        Member member = new Member();
        member.joinDto( this );
        return member;
    }

    @Override
    public User updateDtoToEntity() {
        Member member = new Member();
        member.updateDto( this );
        return member;
    }

    @Override
    public User deleteDtoToEntity() {
        Member member = new Member();
        member.joinDto( this );
        return member;
    }

    @Override
    public UserDto EntityToResponseDto(User user) {
        MemberDto memberDto = new MemberDto();
        memberDto.setLoginId(user.getLoginId());
        memberDto.setNickname(user.getNickname());
        memberDto.setActivityPoint(user.getActivityPoint());
        return memberDto;
    }

}
