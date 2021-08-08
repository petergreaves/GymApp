package com.springfirst.solutions.gym.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    void saveImageFile(String personID, MultipartFile file);

}
