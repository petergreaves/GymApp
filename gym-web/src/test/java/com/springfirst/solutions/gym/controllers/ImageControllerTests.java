package com.springfirst.solutions.gym.controllers;

import com.springfirst.solutions.gym.commands.TrainerCommand;
import com.springfirst.solutions.gym.repositories.TrainerRepository;
import com.springfirst.solutions.gym.services.ImageService;
import com.springfirst.solutions.gym.services.TrainerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;

@ExtendWith(MockitoExtension.class)
public class ImageControllerTests {

    @Mock
    ImageService imageService;

    @Mock
    TrainerService trainerService;

    @InjectMocks
    ImageController imageController;

    MockMvc mockMvc;
    TrainerCommand command;

    @BeforeEach
    public void setup() {

    mockMvc =MockMvcBuilders
            .standaloneSetup(imageController)
            .build();

    command = TrainerCommand.builder().employeeID("A0002").build();

    }

    @Test
    public void testGetUploadForm_success() throws Exception {


        when(trainerService.getTrainerByEmployeeID(ArgumentMatchers.anyString())).thenReturn(command);

        mockMvc.perform(get("/images/trainers/{id}/upload","A9340"))
                .andExpect(view().name("trainers/image-upload-form"))
                .andExpect(model().attributeExists("trainer"))
                .andExpect(status().isOk());
    }

    @Test
    void renderImageFromDB() throws Exception {

        byte[] bytesPrimitive = new String("hello world").getBytes(StandardCharsets.UTF_8);

        Byte[] bytesObject = new Byte[bytesPrimitive.length];

        int k = 0;
        for (byte b : bytesPrimitive){
            bytesObject[k++] = b;
        }

        TrainerCommand command = TrainerCommand.builder().employeeID("A0001").image(bytesObject).build();
        when(trainerService.getTrainerByEmployeeID(anyString())).thenReturn(command);

        MockHttpServletResponse resp=mockMvc.perform(get("/images/trainers/A0001/image"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        assertEquals(resp.getContentType(), "image/jpeg");
        assertTrue(resp.getContentAsByteArray().length==bytesObject.length);
    }


    @Test
    public void testHandleImageUploadPost_success() throws Exception {

        MockMultipartFile multipartFile =
                new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                        "some content".getBytes());
       // doNothing().when(imageService.saveImageFile(any(), any()));

        mockMvc.perform(multipart("/images/trainers/A0011/save").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/trainers/A0011/show"));

        verify(imageService, times(1)).saveImageFile(any(), any());
    }
}
