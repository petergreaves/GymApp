package com.springfirst.solutions.gym.controllers;


import com.springfirst.solutions.gym.commands.AddressCommand;
import com.springfirst.solutions.gym.commands.GymCommand;
import com.springfirst.solutions.gym.domain.Address;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.ui.Model;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class HomeControllerTests extends BaseIT{


    @Mock
    Model model;


    private AddressCommand addressCommand;
    private GymCommand gymCommand;

    @BeforeEach
    public void setup(){

        super.setup();
         addressCommand= AddressCommand.builder()
                .buildingIdentifier("44")
                .street("Beesley Street")
                .county("Rutland")
                .postcode("GG4 9LL")
                .build();
         gymCommand = GymCommand.builder()
                .name("Fat Al's")
                .gymInfo("some info")
                .address(addressCommand)
                .build();

        when(gymService.getGym()).thenReturn(gymCommand);


    }

    @Test
    public void testHomePageMVCAuth_fail() throws Exception {


        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser("nemo")   // can be anything
    public void testHomePageMVCAuthMockuser_success() throws Exception {



        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void testHomePageMVCHttpBasicAuth_fail() throws Exception {



        mockMvc.perform(get("/").with(httpBasic("foo", "bar")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testHomePageMVCHttpBasicAuth_success() throws Exception {



        mockMvc.perform(get("/").with(httpBasic("user", "pa55w0rd")))
                .andExpect(status().isOk());
    }

    @Test
    public void testHomePageMVCAllowAnonHomepage_success() throws Exception {



        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void testHomePageMVCAllowAnonImageView_success() throws Exception {



        mockMvc.perform(get("/img/gym.jpg"))
                .andExpect(status().isOk());
    }

    @Test
    public void testHomePage() {

        homeController = new HomeController(gymService);
        String s = homeController.home(model);
        Assertions.assertEquals(s, "home/index");
    }

    @Test
    public void testHomePageMembersNotImplemented() throws Exception {

        homeController = new HomeController(gymService);
        String s = homeController.members();
        Assertions.assertEquals(s, "not-implemented");
    }
    @Test
    public void testHomePageRegisterNotImplementedL(){

        homeController = new HomeController(gymService);
        String s = homeController.register();
        Assertions.assertEquals(s, "not-implemented");
    }
    @Test
    public void testHomePageClassesNotImplemented() {

        homeController = new HomeController(gymService);
        String s = homeController.classes();
        Assertions.assertEquals(s, "not-implemented");

    }

    @Test
    public void testHomePageHasGymAttribute_success() throws Exception {


        mockMvc.perform(get("/"))
                .andExpect(model().attributeExists("gym"))
                .andExpect(status().isOk());
    }


}
