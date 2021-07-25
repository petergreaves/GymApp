package com.springfirst.solutions.gym.bootstrap;

import com.springfirst.solutions.gym.domain.*;
import com.springfirst.solutions.gym.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class Dataloader implements CommandLineRunner {

    private TrainerSpecialityRepository trainerSpecialityRepository;
    private TrainerRepository trainerRepository;
    private MembershipRepository membershipRepository;
    private MembershipTypeRepository membershipTypeRepository;
    private GymRepository gymRepository;
    private AddressRepository addressRepository;
    private MemberRepository memberRepository;

    public Dataloader(TrainerSpecialityRepository trainerSpecialityRepository, TrainerRepository trainerRepository,
                      MembershipRepository membershipRepository, MembershipTypeRepository membershipTypeRepository,
                      GymRepository gymRepository, AddressRepository addressRepository, MemberRepository memberRepository) {
        this.trainerSpecialityRepository = trainerSpecialityRepository;
        this.trainerRepository = trainerRepository;
        this.membershipRepository = membershipRepository;
        this.membershipTypeRepository = membershipTypeRepository;
        this.gymRepository = gymRepository;
        this.addressRepository = addressRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (gymRepository.count() > 0){
            log.info("Data already loaded...");
        }
        else {

            log.info("Starting data load...");

            Map<String, TrainerSpeciality> savedSpecialities = loadSpecialities();
            long count = trainerSpecialityRepository.count();
            log.info("Loaded {} trainer specialities...", count);

            // trainers
            Map<String, Trainer> savedTrainers = loadTrainers(savedSpecialities);

            count = trainerRepository.count();
            log.info("Loaded {} trainers...", count);

            // the gym

            Gym gym = loadGym(savedTrainers);
            log.info("Created gym {} with {} associated trainers...", gym.getName(), gym.getTrainers().size());

            // membership types
            Map<String, MembershipType> savedMembershipTypes = loadMembershipTypes();
            count = membershipTypeRepository.count();
            log.info("Loaded {} membership types...", count);

            // members

            Map<String, Member> members = loadMembers(savedMembershipTypes);
            count = memberRepository.count();
            log.info("Loaded {} members..", count);

        }

    }

    private Gym loadGym(Map<String, Trainer> savedTrainers){

        Address savedGymAddress =addressRepository.save(Address.builder()
                .buildingIdentifier("11a")
                .street("Swan St")
                .postcode("RR1 9DD")
                .county("Rutland")
                .build());

       return gymRepository.save(Gym.builder()
                .name("Muscles Unlimited")
                .gymInfo("The place to get buff!")
                .address(savedGymAddress)
                .trainer(savedTrainers.get("AB002"))
                .trainer(savedTrainers.get("BC889"))
                .build());
    }

    private Map<String, Trainer> loadTrainers(Map<String, TrainerSpeciality> savedSpecialities) {

        Map<String, Trainer> saved = new HashMap<>();

        Trainer savedJoe = trainerRepository.save(Trainer.builder()
                .name("Joe Smith")
                .telNo("049939-8129993")
                .employeeNumber("AB002")
                .speciality(savedSpecialities.get("Classes"))
                .build());

        Trainer savedKelly = trainerRepository.save(Trainer.builder()
                .name("Kelly Strong")
                .telNo("043239-8129993")
                .employeeNumber("BC889")
                .speciality(savedSpecialities.get("Yoga"))
                .build());

        saved.put(savedJoe.getEmployeeNumber(), savedJoe);
        saved.put(savedKelly.getEmployeeNumber(), savedKelly);
        return saved;

    }

    private Map<String, TrainerSpeciality> loadSpecialities() {

        Map<String, TrainerSpeciality> saved = new HashMap<>();

        saved.put("Pilates", trainerSpecialityRepository.save(TrainerSpeciality.builder().description("Pilates").build()));
        saved.put("Yoga", trainerSpecialityRepository.save(TrainerSpeciality.builder().description("Yoga").build()));
        saved.put("Strength", trainerSpecialityRepository.save(TrainerSpeciality.builder().description("Strength").build()));
        saved.put("Core", trainerSpecialityRepository.save(TrainerSpeciality.builder().description("Core").build()));
        saved.put("Classes", trainerSpecialityRepository.save(TrainerSpeciality.builder().description("Classes").build()));
        saved.put("Strength", trainerSpecialityRepository.save(TrainerSpeciality.builder().description("Strength").build()));

        return saved;


    }

    private Map<String, MembershipType> loadMembershipTypes() {

        Map<String, MembershipType> saved = new HashMap<>();

        saved.put("Full", membershipTypeRepository.save(MembershipType.builder().description("Full").build()));
        saved.put("Gym", membershipTypeRepository.save(MembershipType.builder().description("Gym").build()));
        saved.put("Classes", membershipTypeRepository.save(MembershipType.builder().description("Classes").build()));
        saved.put("Trial", membershipTypeRepository.save(MembershipType.builder().description("Trial").build()));
        return saved;


    }

    private Map<String, Member> loadMembers(Map<String, MembershipType> membershipTypes) {

        Map<String, Member> saved = new HashMap<>();

        Membership savedM1Membership = membershipRepository.save(Membership.builder()
                .active(true)
                .start(LocalDate.of(2021, 7, 8))
                .end(LocalDate.of(2022, 7, 7))
                .membershipType(membershipTypes.get("Full"))
                .build());
        Membership savedM2Membership = membershipRepository.save(Membership.builder()
                .active(true)
                .start(LocalDate.of(2021, 2, 12))
                .end(LocalDate.of(2022, 2, 11))
                .membershipType(membershipTypes.get("Classes"))
                .build());

        Membership savedM3Membership = membershipRepository.save(Membership.builder()
                .active(true)
                .start(LocalDate.of(2021, 2, 12))
                .end(LocalDate.of(2022, 2, 11))
                .membershipType(membershipTypes.get("Trial"))
                .build());

        Member m1 = Member.builder()
                .name("Arnie Liftalot")
                .memberID("M001")
                .telNo("039940-2832388")
                .membership(savedM1Membership).build();

        Member m2= Member.builder()
                .name("Karen Kettlebells")
                .memberID("M002")
                .telNo("0190940-2832388")
                .membership(savedM2Membership).build();

        Member m3= Member.builder()
                .name("Billy Biceps")
                .memberID("M003")
                .telNo("0378-832388")
                .membership(savedM3Membership).build();


        saved.put("M001", memberRepository.save(m1));
        saved.put("M002", memberRepository.save(m2));
        saved.put("M003", memberRepository.save(m3));
        return saved;


    }

}
