package com.springfirst.solutions.gym.configs;

import com.springfirst.solutions.gym.mappers.*;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfigs {

    @Bean
    public TrainerSpecialityMapper trainerSpecialityMapper() {
        return Mappers.getMapper(TrainerSpecialityMapper.class);
    }

    @Bean
    public TrainerMapper trainerMapper() {
        return Mappers.getMapper(TrainerMapper.class);
    }

    @Bean
    public AddressMapper addressMapper() {
        return Mappers.getMapper(AddressMapper.class);
    }

    @Bean
    public VisitMapper visitMapper() {
        return Mappers.getMapper(VisitMapper.class);
    }

    @Bean
    public MembershipTypeMapper membershipTypeMapper() {
        return Mappers.getMapper(MembershipTypeMapper.class);
    }
}
