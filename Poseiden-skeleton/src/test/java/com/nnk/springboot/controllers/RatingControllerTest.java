package com.nnk.springboot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class RatingControllerTest {

    private static MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private RatingService service;
    @InjectMocks
    private RatingController controller;

    private static Rating rating;

    @BeforeAll
    static void setUp() {
        rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("AAA");
        rating.setSandPRating("BBB");
        rating.setFitchRating("CCC");
        rating.setOrderNumber(2);
    }
    @BeforeEach
    void setUpMock() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setValidator(new LocalValidatorFactoryBean())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testHome() throws Exception {
        List<Rating> bids = Arrays.asList(rating);
        when(service.getAllRatings()).thenReturn(bids);

        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attributeExists("ratingsList"));

        verify(service, times(1)).getAllRatings();
    }

    @Test
    void testAddRating() throws Exception {
        mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Test
    void testValidate_success() throws Exception {
        when(service.getAllRatings()).thenReturn(Arrays.asList(rating));

        mockMvc.perform(post("/rating/validate")
                .param("moodysRating", rating.getMoodysRating())
                .param("sandPRating", rating.getSandPRating())
                .param("fitchRating", rating.getFitchRating())
        )
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/rating/list"));

        verify(service, times(1)).createRating(any(Rating.class));
    }

    @Test
    void testValidate_fail() throws Exception {
        mockMvc.perform(post("/rating/validate")
                .param("moodysRating", "AAA")
                .param("sandPRating", "BBB")
                .param("fitchRating", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));

        verify(service, never()).createRating(any(Rating.class));
    }

    @Test
    void testShowUpdateForm() throws Exception {
        when(service.getRatingById(1)).thenReturn(rating);

        mockMvc.perform(get("/rating/update/1"))
        .andExpect(status().isOk())
        .andExpect(view().name("rating/update"))
        .andExpect(model().attributeExists("rating"));

        verify(service, times(1)).getRatingById(1);
    }

    @Test
    void testUpdateRating_success() throws Exception {
        when(service.getAllRatings()).thenReturn(Arrays.asList(rating));

        mockMvc.perform(post("/rating/update/1")
                .param("moodysRating", "AAA")
                .param("sandPRating", "BBB")
                .param("fitchRating", "GGG")
        );
    }

}
