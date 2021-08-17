package com.springfirst.solutions.gym.controllers.rest;

import com.springfirst.solutions.gym.commands.TrainerCommand;
import com.springfirst.solutions.gym.controllers.BaseIT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest()
@SpringBootTest
public class TrainerRestControllersTest extends BaseIT{

    TrainerCommand trainerCommand1;
    TrainerCommand trainerCommand2;

    List<TrainerCommand> ret;

    @BeforeEach
    void setUp() {
        super.setup();

        trainerCommand1 = TrainerCommand.builder()
                .employeeID("A3971")
                .name("Bill Bicep")
                .telNo("0129348 03993")
                .trainerSpecialityCommandID(1L)
                .trainerSpecialityCommandID(2L)
                .biography("Been a personal trainer for 10 years")
                .image(new Byte['3'])
                .build();

        trainerCommand2 = TrainerCommand.builder()
                .employeeID("A9971")
                .name("Bill Bicep")
                .telNo("0129348 03993")
                .trainerSpecialityCommandID(1L)
                .trainerSpecialityCommandID(2L)
                .biography("these swans are sick")
                .image(new Byte['4'])
                .build();


        ret = new ArrayList<>();

        ret.add(trainerCommand1);
        ret.add(trainerCommand2);
    }

    @Test
    public void getAllTrainers() throws Exception{

        when(trainerService.getAllTrainers()).thenReturn(ret);

        mockMvc.perform(get("/api/v1/trainers"))
           //     .with(httpBasic("userMember", "userMember")))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(status().isOk());

    }
    @Test
    public void getTrainerByID() throws Exception{

        when(trainerService.getTrainerByEmployeeID(anyString())).thenReturn(ret.get(0));
        mockMvc.perform(get("/api/v1/trainers/{id}","A8238"))
       //         .with(httpBasic("userTrainer", "pa55w0rd")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Bill Bicep")));

        verify(trainerService, times(1)).getTrainerByEmployeeID(anyString());
    }

    @Test
    public void deleteTrainerByID() throws Exception{

        mockMvc.perform(delete("/api/v1/trainers/{id}/delete","A8238")
                .with(httpBasic("admin", "admin")))
                .andExpect(status().isOk());

        verify(trainerService, times(1)).deleteTrainer(anyString());

    }

    @Test
    public void createTrainer() throws Exception{
        TrainerCommand created = TrainerCommand.builder()
                .employeeID("A8888")
                .name("Anew Trainer")
                .telNo("039903899")
                .biography("now is time for all good men to come to the aid of the party")
                .build();

        when(trainerService.createOrUpdateTrainer(any())).thenReturn(created);

        StringBuffer content = new StringBuffer();
        content.append("{ \"name\": \"Anew Trainer\", \"employeeID\": \"A8888\",\"telNo\": \"039903899\",");
        content.append("\"biography\" :\"now is time for all good men to come to the aid of the party\",");
        content.append("\"imagePresent\" : false,");
        content.append("\"trainerSpecialityCommandIDs\": [2,5,6]");
        content.append("}");

        mockMvc.perform(post("/api/v1/trainers/new")
                .content(content.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .with(httpBasic("admin", "admin"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string("Location", "/api/v1/trainers/A8888"))
                .andExpect(jsonPath("$.name").value("Anew Trainer"))
                .andExpect(jsonPath("$.telNo").value("039903899"));

        verify(trainerService, times(1))
                .createOrUpdateTrainer(ArgumentMatchers.any(TrainerCommand.class));

    }

    @Test
    public void updateTrainer_success() throws Exception{
        TrainerCommand updated = TrainerCommand.builder()
                .employeeID("A8888")
                .name("Anew Trainer")
                .telNo("039903899")
                .id(99L)
                .biography("now is time for all good men to come to the aid of the party")
                .build();

        when(trainerService.createOrUpdateTrainer(any())).thenReturn(updated);

        StringBuffer content = new StringBuffer();
        content.append("{ \"name\": \"Anew Trainer\", \"employeeID\": \"A8864\",\"telNo\": \"039903899\",");
        content.append("\"biography\" :\"now is time for all good men to come to the aid of the party\",");
        content.append("\"imagePresent\" : false,");
        content.append("\"trainerSpecialityCommandIDs\": [2,5,6]");
        content.append("}");

        mockMvc.perform(put("/api/v1/trainers/{id}/update", "A8864")
                .content(content.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .with(httpBasic("admin", "admin"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(header().string("Location", "/api/v1/trainers/A8864"))
                .andExpect(jsonPath("$").doesNotExist());

        verify(trainerService, times(1))
                .createOrUpdateTrainer(ArgumentMatchers.any(TrainerCommand.class));

    }

    @Test // TODO
    public void updateTrainerNoTrainerWithID_failure(){


    }
}
