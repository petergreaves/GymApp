package com.springfirst.solutions.gym.mappers;

import com.springfirst.solutions.gym.commands.TrainerCommand;
import com.springfirst.solutions.gym.commands.VisitCommand;
import com.springfirst.solutions.gym.domain.Trainer;
import com.springfirst.solutions.gym.domain.Visit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VisitMapper {

    VisitMapper INSTANCE = Mappers.getMapper( VisitMapper.class);
    VisitCommand visitToVisitCommand(Visit visit);
    Visit visitCommandToVisit(VisitCommand visitCommand);

}
