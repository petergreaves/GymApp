package com.springfirst.solutions.gym.controllers.registration;

import com.springfirst.solutions.gym.commands.TrainerCommand;
import com.springfirst.solutions.gym.commands.registration.RegistrationStateCommand;
import com.springfirst.solutions.gym.controllers.RegistrationController;
import com.springfirst.solutions.gym.domain.registration.RegistrationState;
import com.springfirst.solutions.gym.domain.registration.Stage;
import com.springfirst.solutions.gym.mappers.registration.RegistrationMapper;
import com.springfirst.solutions.gym.services.registration.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationControllerTests {

    @Mock
    RegistrationService registrationService;

    @InjectMocks
    RegistrationController registrationController;

    MockMvc mockMvc;

    private List<RegistrationStateCommand> regCommandsList;
    private RegistrationMapper registrationMapper;

    @BeforeEach
    public void setup() {

        mockMvc = MockMvcBuilders
                .standaloneSetup(registrationController)
                .build();

        registrationMapper = RegistrationMapper.INSTANCE;

        regCommandsList = new ArrayList<>();
        regCommandsList.add(registrationMapper.registrationStateToRegistrationStateCommand(RegistrationState
                .builder()
                .email("a@b.com")
                .id(1L)
                .stage(Stage.PENDING)
                .created(LocalDate.now())
                .updated(LocalDate.now())
                .build()));

        regCommandsList.add(registrationMapper.registrationStateToRegistrationStateCommand(RegistrationState
                .builder()
                .email("b@c.com")
                .id(2L)
                .stage(Stage.PENDING)
                .created(LocalDate.now())
                .updated(LocalDate.now())
                .build()));

    }

    @Test
    public void getAllRegistrations() throws Exception {

        when(registrationService.getAllRegistrations()).thenReturn(regCommandsList);

        mockMvc.perform(get("/register/list"))
                .andExpect(model().attributeExists("registrations"))
                .andExpect(model().attribute("registrations", hasSize(2)))
                .andExpect(view().name("register/view-registrations-list"))
                .andExpect(status().isOk());

    }


    @Test
    public void getRegByEmail() throws Exception {

        when(registrationService.findRegistrationByEmail(anyString())).thenReturn(regCommandsList.get(0));

        mockMvc.perform(get("/register/{email}/view", "a@foo.com"))
                .andExpect(model().attributeExists("registration"))
                .andExpect(model().attribute("registration", hasProperty("id", is(1L))))
                .andExpect(view().name("register/view-registration-detail"))
                .andExpect(status().isOk());

        verify(registrationService, times(1)).findRegistrationByEmail((anyString()));
    }


    @Test
    public void getNewRegForm() throws Exception {


        mockMvc.perform(get("/register/new"))
                .andExpect(model().attributeExists("registration"))
                .andExpect(model().attribute("registration", hasProperty("isNew", is(true))))
                .andExpect(view().name("register/create-edit-registration"))
                .andExpect(status().isOk());

    }


    @Test
    public void createRegistration_success() throws Exception {
        RegistrationStateCommand newRegCommand = RegistrationStateCommand.builder()
                .email("A001@foo.com")
                .password("changeMe")
                .stage(Stage.PENDING)
                .build();

        when(registrationService.saveOrUpdateRegistration(ArgumentMatchers.any(RegistrationStateCommand.class))).
                thenReturn(newRegCommand);

        mockMvc.perform(post("/register/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("email", "A001@foo.com")
                .param("password", "changeMe"))
                .andExpect(model().hasNoErrors())
                .andExpect(status().is3xxRedirection());

        verify(registrationService, times(1)).saveOrUpdateRegistration(any());

    }
}


//    @Test
//    public void saveNewRegForm() throws Exception {
//
//        RegistrationStateCommand newReg =  registrationMapper.registrationStateToRegistrationStateCommand(RegistrationState
//                .builder()
//                .email("foo@bar.com")
//                .id(122L)
//                .stage(Stage.PENDING)
//                .created(LocalDate.now())
//                .updated(LocalDate.now())
//                .build());
//
//       when(registrationService.saveOrUpdateRegistration(ArgumentMatchers.any(RegistrationStateCommand.class)))
//        .thenReturn(newReg);
//
//        mockMvc.perform(post("/trainers/new")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//                .param("name", "Big Jim")
//                .param("employeeID", "A1001")
//                .param("imagePath", "/images/A002.jpg")
//                .param("telNo", "4884988438")
//                .param("biography", "this is the story of Big Jim")
//                .param("trainerSpecialityCommandIDs", "1", "2"))
//                .andExpect(model().hasNoErrors())
//                .andExpect(status().is3xxRedirection());
//
//        verify(trainerService, times(1)).createOrUpdateTrainer(any());


//    @Test
//    public void createTrainer_success() throws Exception {
//        TrainerCommand newTrainerCommand = TrainerCommand.builder()
//                .employeeID("A001")
//                .name("Big Jim")
//                .telNo("838438")
//                .trainerSpecialityCommandID(55L)
//                .trainerSpecialityCommandID(66L)
//                .build();
//
//        when(trainerService.createOrUpdateTrainer(ArgumentMatchers.any(TrainerCommand.class))).thenReturn(newTrainerCommand);
//
//        mockMvc.perform(post("/trainers/new")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//                .param("name", "Big Jim")
//                .param("employeeID", "A1001")
//                .param("imagePath", "/images/A002.jpg")
//                .param("telNo", "4884988438")
//                .param("biography", "this is the story of Big Jim")
//                .param("trainerSpecialityCommandIDs", "1", "2"))
//                .andExpect(model().hasNoErrors())
//                .andExpect(status().is3xxRedirection());
//
//        verify(trainerService, times(1)).createOrUpdateTrainer(any());
//    }
//
//
//    @Test
//    public void createTrainer_failsValidation() throws Exception {
//        TrainerCommand newTrainerCommand = TrainerCommand.builder()
//                .employeeID("A001")
//                .name("Big Jim")
//                .telNo("838438")
//                .trainerSpecialityCommandID(22L)
//                .trainerSpecialityCommandID(11L)
//                .build();
//
//        //  when(trainerService.createTrainer(ArgumentMatchers.any(TrainerCommand.class))).thenReturn(newTrainerCommand);
//
//        mockMvc.perform(post("/trainers/new")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//                .param("name", "") // blank
//                .param("employeeID", "A01") //too short
//                .param("imagePath", "/images/A002.jpg")  //ok
//                .param("telNo", "48849884274576486838") //too long
//                .param("biography", "this is the story of Big Jim")
//                .param("trainerSpecialityCommandIDs", "1", "2"))
//                .andExpect(model().attributeExists("trainer"))
//                .andExpect(model().hasErrors())
//                .andExpect(status().is3xxRedirection());
//
//        verifyNoInteractions(trainerService);
//    }
//
//
//    @Test
//    public void deleteTrainer_success() throws Exception {
//
//
//        mockMvc.perform(post("/trainers/{id}/delete", "A001")
//                .param("id", "A001"))
//                .andExpect(status().is3xxRedirection());
//    }
//
//    @Test
//    public void getCreateTrainerForm_success() throws Exception {
//
//        TrainerCommand newInstance = TrainerCommand.builder().isNew(true).build();
//
//        when(trainerService.getNewTrainerInstance()).thenReturn(newInstance);
//        when(trainerSpecialityService.getTrainerSpecialities()).thenReturn(trainerSpecialityCommands);
//
//        mockMvc.perform(get("/trainers/new"))
//                .andExpect(model().attributeExists("trainer"))
//                .andExpect(model().attributeExists("specialities"))
//                .andExpect(model().attribute("trainer", hasProperty("isNew", is(true))))
//                .andExpect(view().name("trainers/create-update-trainer-form"))
//                .andExpect(status().isOk());
//
//        verify(trainerService, times(1)).getNewTrainerInstance();
//    }
//
//    @Test
//    public void getUpdateTrainerForm_success() throws Exception {
//
//        when(trainerService.getTrainerByEmployeeID(anyString())).thenReturn(trainerCommandsList.get(0));
//        when(trainerSpecialityService.getTrainerSpecialities()).thenReturn(trainerSpecialityCommands);
//
//        mockMvc.perform(get("/trainers/{id}/update", "A001"))
//                .andExpect(model().attributeExists("trainer"))
//                .andExpect(model().attributeExists("specialities"))
//                .andExpect(model().attribute("trainer", hasProperty("isNew", nullValue())))
//                .andExpect(view().name("trainers/create-update-trainer-form"))
//                .andExpect(status().isOk());
//
//        verify(trainerService, times(1)).getTrainerByEmployeeID(anyString());
//    }
//
//    @Test
//    public void updateTrainer_success() throws Exception {
//        TrainerCommand updatedTrainerCommand = TrainerCommand.builder().build();
//
//        when(trainerService.createOrUpdateTrainer(ArgumentMatchers.any(TrainerCommand.class))).thenReturn(updatedTrainerCommand);
//
//        mockMvc.perform(post("/trainers/{id}/update", "A001")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//                .param("name", "Big Jim")
//                .param("employeeID", "A0301")
//                .param("biography", "now is the time for all good men to come to the aid of the party.")
//                .param("telNo", "8384389993")
//                .param("id", "1")
//                .param("imagePath", "/images/a.jpg")
//                .param("trainerSpecialityCommandIDs", "3", "1"))
//                .andExpect(status().is3xxRedirection());
//
//        verify(trainerService, times(1)).createOrUpdateTrainer(ArgumentMatchers.any(TrainerCommand.class));
//    }

