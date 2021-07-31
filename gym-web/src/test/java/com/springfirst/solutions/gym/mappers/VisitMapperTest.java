package com.springfirst.solutions.gym.mappers;

import com.springfirst.solutions.gym.commands.VisitCommand;
import com.springfirst.solutions.gym.configs.MapperConfigs;
import com.springfirst.solutions.gym.domain.Visit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(classes = {MapperConfigs.class})
public class VisitMapperTest {


    @Autowired
    private VisitMapper visitMapper;

        @Test
        public void toCommand(){

            Visit visit = Visit.builder()
                    .purposeOfVisit("cardio session")
                    .build();
            VisitCommand visitCommand = visitMapper.visitToVisitCommand(visit);

            Assertions.assertAll(

                    () -> {Assertions.assertEquals(visit.getVisitDateTime(), visitCommand.getVisitDateTime());},
                    () -> {Assertions.assertEquals(visit.getPurposeOfVisit(), visitCommand.getPurposeOfVisit());}
            );
        }


    @Test
    public void toEntity(){

        VisitCommand visitCommand = VisitCommand.builder()
                .purposeOfVisit("cardio session")
                .build();
        Visit visit = visitMapper.visitCommandToVisit(visitCommand);

        Assertions.assertAll(

                () -> {Assertions.assertEquals(visit.getVisitDateTime(), visitCommand.getVisitDateTime());},
                () -> {Assertions.assertEquals(visit.getPurposeOfVisit(), visitCommand.getPurposeOfVisit());}
        );
    }
}
