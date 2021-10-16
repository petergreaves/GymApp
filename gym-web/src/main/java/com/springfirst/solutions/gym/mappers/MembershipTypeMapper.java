package com.springfirst.solutions.gym.mappers;

import com.springfirst.solutions.gym.commands.MembershipTypeCommand;
import com.springfirst.solutions.gym.domain.member.MembershipType;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MembershipTypeMapper {
    MembershipTypeMapper INSTANCE = Mappers.getMapper( MembershipTypeMapper.class);
    MembershipTypeCommand membershipTypeToMembershipTypeCommand(MembershipType membershipType);
    MembershipType membershipTypeCommandToMembershipType(MembershipTypeCommand membershipTypeCommand);

}
