package com.springfirst.solutions.gym.mappers;

import com.springfirst.solutions.gym.commands.TrainerCommand;
import com.springfirst.solutions.gym.domain.Trainer;
import com.springfirst.solutions.gym.domain.TrainerSpeciality;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {TrainerSpecialityMapper.class})
public interface TrainerMapper {

    TrainerMapper INSTANCE = Mappers.getMapper( TrainerMapper.class);

    @Mapping(source = "trainerSpecialities", target = "trainerSpecialityCommandIDs")
    TrainerCommand trainerToTrainerCommand(Trainer t);

    Trainer trainerCommandToTrainer(TrainerCommand t);

    default Set<Long> map(Set<TrainerSpeciality> trainerSpecialitySet){

        return trainerSpecialitySet
                .stream()
                .map(ts -> ts.getId())
                .collect(Collectors.toSet());

    }


}
