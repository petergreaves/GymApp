package com.springfirst.solutions.gym.controllers;

import com.springfirst.solutions.gym.commands.TrainerCommand;
import com.springfirst.solutions.gym.services.ImageService;
import com.springfirst.solutions.gym.services.TrainerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/images")
@Slf4j
public class ImageController {

    private final TrainerService trainerService;
    private final ImageService imageService;

    public ImageController(TrainerService trainerService, ImageService imageService) {
        this.trainerService = trainerService;
        this.imageService = imageService;
    }

    // get the image upload form
    @GetMapping("/trainers/{id}/upload")
    @ResponseStatus(HttpStatus.OK)
    public String getImageUploadForm(@PathVariable("id") String id, Model model){

        log.debug("got image form request for {}", id);
        TrainerCommand command = trainerService.getTrainerByEmployeeID(id);

        model.addAttribute("trainer", command);
        return "trainers/image-upload-form";
    }

    // handle the image upload post
    @PostMapping("/trainers/{id}/save")
    public String handleImageUploadForm(@RequestParam("imagefile") MultipartFile image, @PathVariable("id") String employeeID){

        log.debug("got image post request for {}", employeeID);

        TrainerCommand trainerCommand = trainerService.getTrainerByEmployeeID(employeeID);
        imageService.saveImageFile(image,trainerCommand);

        return "redirect:/trainers/" + employeeID + "/show";
    }


    @GetMapping("/trainers/{id}/image")
    public void getImageStream(@PathVariable("id") String empID, HttpServletResponse response) throws IOException {

        TrainerCommand command = trainerService.getTrainerByEmployeeID(empID);
        if (command==null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Trainer not found with id " +empID);
        }

        Byte[] imageBytesObj = command.getImage();
        if (imageBytesObj != null & imageBytesObj.length > 0) {
            byte[] imageBytesPrim = new byte[imageBytesObj.length];

            int k = 0;

            for (byte imageByte : imageBytesObj) {
                imageBytesPrim[k++] = imageByte;
            }

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(imageBytesPrim);
            IOUtils.copy(is, response.getOutputStream());
        }
    }

}
