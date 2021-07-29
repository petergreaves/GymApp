package com.springfirst.solutions.gym.mappers;

import com.springfirst.solutions.gym.commands.MembershipTypeCommand;
import com.springfirst.solutions.gym.configs.MapperConfigs;
import com.springfirst.solutions.gym.domain.MembershipType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(classes = {MapperConfigs.class})
public class MembershipTypeMapperTest {

    @Autowired
    private MembershipTypeMapper membershipTypeMapper;

    @Test
    public void toCommand(){

        MembershipType membershipType  = MembershipType.builder()
                .type("Full")
                .description("Access to all classes and gym")
                .build();
        MembershipTypeCommand membershipTypeCommand = membershipTypeMapper
                .membershipTypeToMembershipTypeCommand(membershipType);

        Assertions.assertAll(

                () -> {Assertions.assertEquals(membershipType.getType(), membershipTypeCommand.getType());},
                () -> {Assertions.assertEquals(membershipType.getDescription(), membershipTypeCommand.getDescription());}
        );
    }


    @Test
    public void toEntity(){

        MembershipTypeCommand membershipTypeCommand  = MembershipTypeCommand.builder()
                .type("Full")
                .description("Access to all classes and gym")
                .build();
        MembershipType membershipType = membershipTypeMapper.membershipTypeCommandToMembershipType(membershipTypeCommand);

        Assertions.assertAll(

                () -> {Assertions.assertEquals(membershipType.getType(), membershipTypeCommand.getType());},
                () -> {Assertions.assertEquals(membershipType.getDescription(), membershipTypeCommand.getDescription());}
        );
    }
}
