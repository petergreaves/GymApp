package com.springfirst.solutions.gym.services;

import com.springfirst.solutions.gym.commands.TrainerCommand;
import javassist.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    void saveImageFile(MultipartFile image, TrainerCommand trainer);
}
