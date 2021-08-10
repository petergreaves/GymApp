package com.springfirst.solutions.gym.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HomeControllerTests {


    private HomeController homeController;

    @BeforeEach
    public void setup(){

      // mockMvc= MockMvcBuilders.standaloneSetup(homeController).build();


    }

    @Test
    public void testHomePage() throws Exception {

        homeController = new HomeController();
        String s = homeController.home();
        Assertions.assertEquals(s, "home/index");
    }

    @Test
    public void testHomePageMembersNotImplemented() throws Exception {

        homeController = new HomeController();
        String s = homeController.members();
        Assertions.assertEquals(s, "not-implemented");
    }
    @Test
    public void testHomePageRegisterNotImplementedL() throws Exception {

        homeController = new HomeController();
        String s = homeController.register();
        Assertions.assertEquals(s, "not-implemented");
    }
    @Test
    public void testHomePageClassesNotImplemented() throws Exception {

        homeController = new HomeController();
        String s = homeController.classes();
        Assertions.assertEquals(s, "not-implemented");

    }

}
