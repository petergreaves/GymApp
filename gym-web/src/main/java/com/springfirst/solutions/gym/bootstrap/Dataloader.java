package com.springfirst.solutions.gym.bootstrap;

import com.springfirst.solutions.gym.domain.*;
import com.springfirst.solutions.gym.domain.member.Member;
import com.springfirst.solutions.gym.domain.member.Membership;
import com.springfirst.solutions.gym.domain.member.MembershipType;
import com.springfirst.solutions.gym.domain.registration.RegistrationState;
import com.springfirst.solutions.gym.domain.registration.Stage;
import com.springfirst.solutions.gym.domain.security.Authority;
import com.springfirst.solutions.gym.domain.security.Role;
import com.springfirst.solutions.gym.domain.security.User;
import com.springfirst.solutions.gym.domain.trainer.Trainer;
import com.springfirst.solutions.gym.domain.trainer.TrainerSpeciality;
import com.springfirst.solutions.gym.repositories.*;
import com.springfirst.solutions.gym.repositories.registration.RegistrationRepository;
import com.springfirst.solutions.gym.repositories.security.AuthorityRepository;
import com.springfirst.solutions.gym.repositories.security.RoleRepository;
import com.springfirst.solutions.gym.repositories.security.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Component
@Slf4j
public class Dataloader implements CommandLineRunner {

    private final TrainerSpecialityRepository trainerSpecialityRepository;
    private final TrainerRepository trainerRepository;
    private final MembershipRepository membershipRepository;
    private final MembershipTypeRepository membershipTypeRepository;
    private final GymRepository gymRepository;
    private final AddressRepository addressRepository;
    private final MemberRepository memberRepository;
    private final VisitRepository visitRepository;
    private final RegistrationRepository registrationRepository;

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Dataloader(TrainerSpecialityRepository trainerSpecialityRepository,
                      TrainerRepository trainerRepository, MembershipRepository membershipRepository,
                      MembershipTypeRepository membershipTypeRepository, GymRepository gymRepository,
                      AddressRepository addressRepository, MemberRepository memberRepository,
                      VisitRepository visitRepository, RegistrationRepository registrationRepository, UserRepository userRepository,
                      AuthorityRepository authorityRepository, RoleRepository roleRepository) {
        this.trainerSpecialityRepository = trainerSpecialityRepository;
        this.trainerRepository = trainerRepository;
        this.membershipRepository = membershipRepository;
        this.membershipTypeRepository = membershipTypeRepository;
        this.gymRepository = gymRepository;
        this.addressRepository = addressRepository;
        this.memberRepository = memberRepository;
        this.visitRepository = visitRepository;
        this.registrationRepository = registrationRepository;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (gymRepository.count() > 0) {
            log.info("Data already loaded...");
        } else {

            log.info("Starting data load...");

            Map<String, TrainerSpeciality> savedSpecialities = loadSpecialities();
            long count = trainerSpecialityRepository.count();
            log.info("Loaded {} trainer specialities...", count);


            // registrations
            loadRegistrations();
            count = registrationRepository.count();
            log.info("Loaded {} registrations...", count);

            // trainers
            Map<String, Trainer> savedTrainers = loadTrainers(savedSpecialities);

            count = trainerRepository.count();
            log.info("Loaded {} trainers...", count);

            // the gym

            Gym gym = loadGym(savedTrainers);
            log.info("Created gym \"{}\" with {} associated trainers...", gym.getName(), gym.getTrainers().size());

            // membership types
            Map<String, MembershipType> savedMembershipTypes = loadMembershipTypes();
            count = membershipTypeRepository.count();
            log.info("Loaded {} membership types...", count);

            // members

            Map<String, Member> savedMembers = loadMembers(savedMembershipTypes);
            count = memberRepository.count();
            log.info("Loaded {} members..", count);

            //create a list of collections of the users
            List<Map<String, ? extends AbstractPerson>> allUsers = new ArrayList<>();
            allUsers.add(savedMembers);
            allUsers.add(savedTrainers);

            loadUsersAndSecurityData(allUsers);

            count = authorityRepository.count();
            log.info("Loaded {} authorities..", count);

            count = userRepository.count();
            log.info("Loaded {} users..", count);

            count = visitRepository.count();
            log.info("Loaded {} visits..", count);

            // done
            log.info("Bootstrap dataload complete.");


        }

    }

    private void loadUsersAndSecurityData(List<Map<String, ? extends AbstractPerson>> users) {

        // create all the authorities
        Authority createTrainerAdmin = authorityRepository.save(Authority.builder().permission("admin.trainer.create").build());
        Authority updateTrainerAdmin = authorityRepository.save(Authority.builder().permission("admin.trainer.update").build());
        Authority readTrainerAdmin = authorityRepository.save(Authority.builder().permission("admin.trainer.read").build());
        Authority deleteTrainerAdmin = authorityRepository.save(Authority.builder().permission("admin.trainer.delete").build());

        Authority updateTrainerTrainer = authorityRepository.save(Authority.builder().permission("trainer.trainer.update").build());
        Authority readTrainerTrainer = authorityRepository.save(Authority.builder().permission("trainer.trainer.read").build());

        Authority readTrainerMember = authorityRepository.save(Authority.builder().permission("member.trainer.read").build());
        Authority createMember = authorityRepository.save(Authority.builder().permission("member.create").build());
        Authority updateMember = authorityRepository.save(Authority.builder().permission("member.update").build());
        Authority readMember = authorityRepository.save(Authority.builder().permission("member.read").build());
        Authority readMemberTrainer = authorityRepository.save(Authority.builder().permission("trainer.member.read").build());
        Authority deleteMember = authorityRepository.save(Authority.builder().permission("member.delete").build());

        //create the roles
        Role adminRole = Role.builder()
                .roleName("ADMIN")
                .authorities(Set.of(readTrainerAdmin,updateTrainerAdmin,deleteTrainerAdmin,createTrainerAdmin,readMember,createMember,updateMember,deleteMember))
                .build();
        Role memberRole = Role.builder()
                .roleName("MEMBER")
                .authorities(Set.of(readTrainerMember,readMember, updateMember,createMember))
                .build();

        Role trainerRole = Role.builder()
                .roleName("TRAINER")
                .authorities(Set.of(readTrainerTrainer,updateTrainerTrainer,readMemberTrainer))
                .build();

        roleRepository.saveAll(Arrays.asList(memberRole, adminRole, trainerRole));


        users.stream().forEach(userMap -> {

            for (AbstractPerson user : userMap.values()) {
                userRepository.save(User.builder()
                        .username(user.getEmail())
                        .trainer(user instanceof Trainer?trainerRepository.findByEmployeeID(((Trainer) user).getEmployeeID()).orElseThrow():null)
                        .member(user instanceof Member?memberRepository.findByMemberID((((Member) user).getMemberID())).orElseThrow():null)
                        .password(user instanceof Member
                                ?"{bcrypt}$2a$10$WZWeOwLPFMI04JF.OuyCkeFfgwrIb35KHYeX3sBZTusDx5Q8tF5HS" //userMember
                                :"{bcrypt}$2a$10$3Oj0vJzDtHUs2DaJp9W4Oe.y/VAHhv9H/731H1DF5E911X.8TaaLS") //userTrainer
                        .role(user instanceof Member?memberRole:trainerRole)
                        .build());
            }
        });

        // load an admin account
        userRepository.save(User.builder()
                .username("admin")
                .password("{bcrypt}$2a$10$ggssAWNb6c/1utOpI/pf1.YRPjsTpxDgARlrbkJMaNdf7yoRF1OPq")
                .role(adminRole)
                .build());



    }

    private Gym loadGym(Map<String, Trainer> savedTrainers) {

        Address savedGymAddress = addressRepository.save(Address.builder()
                .buildingIdentifier("11a")
                .street("Swan St")
                .postcode("RR1 9DD")
                .city("Newtown")
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
                .telNo("049129993")
                .email("smith@bar.com")
                .employeeID("AB002")
                .biography("Joe has been a trainer for 10 years, he knows his stuff.")
                //            .image(new Byte['3'])
                .trainerSpeciality(savedSpecialities.get("Classes"))
                .build());

        Trainer savedKelly = trainerRepository.save(Trainer.builder()
                .name("Kelly Strong")
                .telNo("04328129993")
                .email("kelly@strong.com")
                .employeeID("BC889")
                //             .image(new Byte['3'])
                .biography("Kelly can help you with your goals, whatever they are, she has lots of experience.")
                .trainerSpeciality(savedSpecialities.get("Yoga"))
                .trainerSpeciality(savedSpecialities.get("Classes"))
                .trainerSpeciality(savedSpecialities.get("Strength"))
                .build());

        saved.put(savedJoe.getEmployeeID(), savedJoe);
        saved.put(savedKelly.getEmployeeID(), savedKelly);
        log.info("Loaded trainers : " + savedJoe.getEmail() + "," + savedKelly.getEmail());
        return saved;

    }

    private Map<String, TrainerSpeciality> loadSpecialities() {

        Map<String, TrainerSpeciality> saved = new HashMap<>();

        saved.put("Pilates", trainerSpecialityRepository.save(TrainerSpeciality
                .builder()
                .id(1L)
                .name("Pilates")
                .description("Pilates is the class for you if you want to get schwifty.")
                .build()));
        saved.put("Yoga", trainerSpecialityRepository.save(TrainerSpeciality
                .builder()
                .id(2L)
                .name("Yoga")
                .description("All the bendy magic of the east!")
                .build()));
        saved.put("Strength", trainerSpecialityRepository.save(TrainerSpeciality
                .builder()
                .id(3L)
                .name("Strength")
                .description("Bring your own telephone directories, and destroy them.")
                .build()));
        saved.put("Core", trainerSpecialityRepository.save(TrainerSpeciality
                .builder()
                .id(4L)
                .name("Core")
                .description("Take the core class to get ripped!")
                .build()));
        saved.put("Classes", trainerSpecialityRepository.save(TrainerSpeciality
                .builder()
                .id(5L)
                .name("Spinning")
                .description("In the cycle studio...get ready to hit the heights!!")
                .build()));

        return saved;


    }


    private void loadRegistrations(){

        RegistrationState r1 = RegistrationState
                .builder()
                .id(1L)
                .stage(Stage.PENDING)
                .email("a@b.com")
                .created(LocalDate.now())
                .updated(LocalDate.now())
                .password("password")
                .build();
        RegistrationState r2 = RegistrationState
                .builder()
                .id(2L)
                .stage(Stage.APPROVED)
                .email("b@b.com")
                .created(LocalDate.now())
                .updated(LocalDate.now())
                .password("password")
                .build();

        RegistrationState r3= RegistrationState
                .builder()
                .id(3L)
                .stage(Stage.PENDING)
                .email("c@b.com")
                .created(LocalDate.now())
                .updated(LocalDate.now())
                .password("password")
                .build();
        RegistrationState r4= RegistrationState
                .builder()
                .id(4L)
                .stage(Stage.PENDING)
                .email("d@b.com")
                .created(LocalDate.now())
                .updated(LocalDate.now())
                .password("password")
                .build();

        registrationRepository.save(r1);
        registrationRepository.save(r2);
        registrationRepository.save(r3);
        registrationRepository.save(r4);



    }

    private Map<String, MembershipType> loadMembershipTypes() {

        Map<String, MembershipType> saved = new HashMap<>();

        saved.put("Full", membershipTypeRepository.save(
                MembershipType.builder()
                        .description("Full access to all classes and gym.")
                        .type("Full").build()));
        saved.put("Gym", membershipTypeRepository.save
                (MembershipType.builder()
                        .description("Gym only membership")
                        .type("GymOnly")
                        .build()));
        saved.put("Classes", membershipTypeRepository.save
                (MembershipType.builder()
                        .description("Classes only membership")
                        .type("Classes").build()));
        saved.put("Trial", membershipTypeRepository.save
                (MembershipType.builder()
                        .description("Trial membership, full access for 2 weeks")
                        .type("Trial")
                        .build()));
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


        Visit visit = Visit.builder()
                .purposeOfVisit("cardio session")
                .visitDateTime(LocalDateTime.of(2020, 8, 14, 10, 34))
                .build();

        Visit savedVisit = visitRepository.save(visit);

        Member m1 = Member.builder()
                .name("Arnie Liftalot")
                .memberID("M001")
                .telNo("039940-2832388")
                .email("ironman@foo.com")
                .trainingGoals("get fitter")
                .visit(savedVisit)
                .dateOfBirth(LocalDate.of(1963, 3, 22))
                .membership(savedM1Membership).build();

        Member m2 = Member.builder()
                .name("Karen Kettlebells")
                .memberID("M002")
                .email("kk@buns.com")
                .telNo("0190940-2832388")
                .trainingGoals("strength")
                .dateOfBirth(LocalDate.of(1993, 7, 12))
                .membership(savedM2Membership).build();

        Member m3 = Member.builder()
                .name("Billy Biceps")
                .memberID("M003")
                .email("billy@bt.net")
                .telNo("0378-832388")
                .dateOfBirth(LocalDate.of(1977, 11, 19))
                .trainingGoals("flexibility")
                .membership(savedM3Membership).build();

        saved.put(m1.getMemberID(), memberRepository.save(m1));
        saved.put(m2.getMemberID(), memberRepository.save(m2));
        saved.put(m3.getMemberID(), memberRepository.save(m3));
        log.info("Loaded members : " + m1.getEmail() + "," + m2.getEmail() + "," + m3.getEmail());
        return saved;
    }

}
