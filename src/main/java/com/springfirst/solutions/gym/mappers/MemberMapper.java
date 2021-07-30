package com.springfirst.solutions.gym.mappers;

import com.springfirst.solutions.gym.commands.MemberCommand;
import com.springfirst.solutions.gym.domain.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {MembershipMapper.class})
public interface MemberMapper {

    MemberMapper INSTANCE = Mappers.getMapper( MemberMapper.class);

    @Mapping(source = "memberships", target = "membershipCommands")
    MemberCommand memberToMemberCommand(Member member);

    @Mapping(source = "membershipCommands", target = "memberships")
    Member memberCommandToMember(MemberCommand memberCommand);
}
