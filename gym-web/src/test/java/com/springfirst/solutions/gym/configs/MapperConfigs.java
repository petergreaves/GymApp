package com.springfirst.solutions.gym.configs;

import com.springfirst.solutions.gym.mappers.*;
import com.springfirst.solutions.gym.mappers.registration.RegistrationMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Registration;

@Configuration
public class MapperConfigs {

    @Bean
    public TrainerSpecialityMapper trainerSpecialityMapper() {
        return Mappers.getMapper(TrainerSpecialityMapper.class);
    }

    @Bean
    public RegistrationMapper registrationMapper() {
        return Mappers.getMapper(RegistrationMapper.class);
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
    @Bean
    public MemberMapper memberMapper() {
        return Mappers.getMapper(MemberMapper.class);
    }

    @Bean
    public MembershipMapper membershipMapper() {
        return Mappers.getMapper(MembershipMapper.class);
    }

    @Bean
    public GymMapper gymMapper() {
        return Mappers.getMapper(GymMapper.class);
    }
}
