package com.nnk.springboot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    private static MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private UserService service;
    @InjectMocks
    private UserController controller;

    private static User user;

    @BeforeAll
    public static void setUp() {
        user = new User();
        user.setId(1);
        user.setUsername("johnDoe");
        user.setPassword("JohnP@ssw0rd");
        user.setFullname("John Doe");
        user.setRole("USER");
    }

    @BeforeEach
    public void setUpMock() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setValidator(new LocalValidatorFactoryBean())
                .build();
        objectMapper = new ObjectMapper();
    }

}
