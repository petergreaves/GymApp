package com.springfirst.solutions.gym.services;

import com.springfirst.solutions.gym.commands.TrainerCommand;
import com.springfirst.solutions.gym.domain.trainer.Trainer;
import com.springfirst.solutions.gym.repositories.MemberRepository;
import com.springfirst.solutions.gym.repositories.TrainerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ImageServiceTests {

    @Mock
    TrainerRepository trainerRepository;

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    private ImageServiceImpl imageService;

    private AutoCloseable closeable;

    @BeforeEach
    public void openMocks() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void releaseMocks() throws Exception {
        closeable.close();
    }

    @Test
    public void saveImageTrainer_success() throws IOException {


        Trainer trainer = Trainer.builder()
                .id(33L)
                .build();

        TrainerCommand trainerCommand = TrainerCommand.builder()
                .id(33L)
                .build();

        MultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                "this is an image of a trainer".getBytes());

        when(trainerRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(trainer));
        when(trainerRepository.save(any(Trainer.class))).thenReturn(trainer);

        ArgumentCaptor<Trainer> captor = ArgumentCaptor.forClass(Trainer.class);

        imageService.saveImageFile(multipartFile, trainerCommand);
        verify(trainerRepository, times(1)).save(captor.capture());

        Trainer postUpdate = captor.getValue();

        assertEquals(multipartFile.getBytes().length,postUpdate.getImage().length);



    }
}
