package com.springfirst.solutions.gym.services;

import com.springfirst.solutions.gym.commands.TrainerCommand;

import com.springfirst.solutions.gym.domain.trainer.Trainer;
import com.springfirst.solutions.gym.repositories.TrainerRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    private final TrainerRepository trainerRepository;

    public ImageServiceImpl(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    @Override
    public void saveImageFile(MultipartFile imageFile, TrainerCommand trainerCommand) {

        Optional<Trainer> trainerOptional = trainerRepository.findById(trainerCommand.getId());

        if (trainerOptional.isEmpty()) {

            throw new RuntimeException("No trainer found with id " + trainerCommand.getId());
        } else {
            Trainer trainerToUpdate = trainerOptional.get();
            Byte[] byteObjects = new Byte[0];
            try {
                byteObjects = new Byte[imageFile.getBytes().length];
                int i = 0;
                for (byte b : imageFile.getBytes()) {
                    byteObjects[i++] = b;
                }

                trainerToUpdate.setImage(byteObjects);
                trainerRepository.save(trainerToUpdate);

            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
