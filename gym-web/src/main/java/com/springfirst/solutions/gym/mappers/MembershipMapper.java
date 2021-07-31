package com.springfirst.solutions.gym.mappers;

import com.springfirst.solutions.gym.commands.MembershipCommand;
import com.springfirst.solutions.gym.domain.Membership;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {MembershipTypeMapper.class})
public interface MembershipMapper {

    MembershipMapper INSTANCE = Mappers.getMapper( MembershipMapper.class);

    @Mapping(source = "membershipType", target = "membershipTypeCommand")
    MembershipCommand membershipToMembershipCommand(Membership membership);

    @Mapping(source = "membershipTypeCommand", target = "membershipType")
    Membership membershipCommandToMembership(MembershipCommand membershipCommand);
}
