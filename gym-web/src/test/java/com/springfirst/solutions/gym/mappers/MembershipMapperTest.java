package com.springfirst.solutions.gym.mappers;

import com.springfirst.solutions.gym.commands.MembershipCommand;
import com.springfirst.solutions.gym.configs.MapperConfigs;
import com.springfirst.solutions.gym.domain.Membership;
import com.springfirst.solutions.gym.domain.MembershipType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(classes = {MapperConfigs.class})
public class MembershipMapperTest {

    @Autowired
    private MembershipMapper membershipMapper;


    @Test
    public void toCommand() {

        Membership membership = Membership.builder()
                .active(true)
                .start(LocalDate.of(2020, 1, 1))
                .end(LocalDate.of(2021, 1, 1))
                .membershipType(MembershipType.builder().type("Full").description("All you can eat").build())
                .build();
        MembershipCommand membershipCommand = membershipMapper
                .membershipToMembershipCommand(membership);

        Assertions.assertAll(

                () -> {
                    Assertions.assertEquals(membership.getActive(), membershipCommand.getActive());
                },
                () -> {
                    Assertions.assertEquals(membership.getStart(), membershipCommand.getStart());
                },
                () -> {
                    Assertions.assertEquals(membership.getEnd(), membershipCommand.getEnd());
                },
                () -> {
                    Assertions.assertEquals(membership.getMembershipType().getType(),
                            membershipCommand.getMembershipTypeCommand().getType());
                },
                () -> {
                    Assertions.assertEquals(membership.getMembershipType().getDescription(),
                            membershipCommand.getMembershipTypeCommand().getDescription());
                }
        );
    }
}
