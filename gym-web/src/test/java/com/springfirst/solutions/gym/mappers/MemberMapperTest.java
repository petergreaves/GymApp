package com.springfirst.solutions.gym.mappers;

import com.springfirst.solutions.gym.commands.MemberCommand;
import com.springfirst.solutions.gym.commands.MembershipCommand;
import com.springfirst.solutions.gym.commands.MembershipTypeCommand;
import com.springfirst.solutions.gym.configs.MapperConfigs;
import com.springfirst.solutions.gym.domain.member.Member;
import com.springfirst.solutions.gym.domain.member.Membership;
import com.springfirst.solutions.gym.domain.member.MembershipType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(classes = {MapperConfigs.class})
public class MemberMapperTest {

    @Autowired
    MemberMapper memberMapper;

    @Test
    public void toCommand() {

        Member member = Member.builder()
                .trainingGoals("Get fit")
                .name("Bill Flimsy")
                .telNo("0129348 03993")
                .memberID("M003")
                .membership(Membership
                        .builder()
                        .membershipType(MembershipType
                                .builder()
                                .type("Full").description("All you can eat")
                                .build())
                        .start(LocalDate.of(2020,1,1))
                        .end(LocalDate.of(2021,1,1))
                        .active(true)
                        .build())
                .dateOfBirth(LocalDate.of(1963,3,1))
                .build();

        MemberCommand memberCommand = memberMapper.memberToMemberCommand(member);Assertions.assertAll(

               () -> {
                    Assertions.assertEquals(member.getName(), memberCommand.getName());
                },
                () -> {
                    Assertions.assertEquals(member.getTelNo(), memberCommand.getTelNo());
                }
                , () -> {
                    Assertions.assertEquals(member.getDateOfBirth(), memberCommand.getDateOfBirth());
               },
                () -> {
                    Assertions.assertEquals(member.getMemberships().size(), memberCommand.getMembershipCommands().size());
                },
                () -> {
                    Assertions.assertEquals(
                            member.getMemberships().stream().findFirst().get().getMembershipType().getType(),
                            memberCommand.getMembershipCommands()
                                    .stream().findFirst().get().getMembershipTypeCommand().getType());
                },
                () -> {
                    Assertions.assertEquals(
                            member.getMemberships().stream().findFirst().get().getMembershipType().getDescription(),
                            memberCommand.getMembershipCommands()
                                    .stream().findFirst().get().getMembershipTypeCommand().getDescription());
                }
        );
    }

    @Test
    public void toEntity() {

        MemberCommand memberCommand = MemberCommand.builder()
                .trainingGoals("Get fit")
                .name("Bill Flimsy")
                .telNo("0129348 03993")
                .memberID("M003")
                .membershipCommand(MembershipCommand
                        .builder()
                        .membershipTypeCommand(MembershipTypeCommand
                                .builder()
                                .type("Full").description("All you can eat")
                                .build())
                        .start(LocalDate.of(2020,1,1))
                        .end(LocalDate.of(2021,1,1))
                        .active(true)
                        .build())
                .dateOfBirth(LocalDate.of(1963,3,1))
                .build();

        Member member = memberMapper.memberCommandToMember(memberCommand);

                ;Assertions.assertAll(

                () -> {
                    Assertions.assertEquals(member.getName(), memberCommand.getName());
                },
                () -> {
                    Assertions.assertEquals(member.getTelNo(), memberCommand.getTelNo());
                }
                , () -> {
                    Assertions.assertEquals(member.getDateOfBirth(), memberCommand.getDateOfBirth());
                },
                () -> {
                    Assertions.assertEquals(member.getMemberships().size(), memberCommand.getMembershipCommands().size());
                },
                () -> {
                    Assertions.assertEquals(
                            member.getMemberships().stream().findFirst().get().getMembershipType().getType(),
                            memberCommand.getMembershipCommands()
                                    .stream().findFirst().get().getMembershipTypeCommand().getType());
                },
                () -> {
                    Assertions.assertEquals(
                            member.getMemberships().stream().findFirst().get().getMembershipType().getDescription(),
                            memberCommand.getMembershipCommands()
                                    .stream().findFirst().get().getMembershipTypeCommand().getDescription());
                }
        );
    }
}
