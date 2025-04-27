package com.nnk.springboot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurveService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class CurveControllerTest {

    private static MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private CurveService service;
    @InjectMocks
    private CurveController controller;
    
    private static CurvePoint curvePoint;

    @BeforeAll
    static void setUp() {
        curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(1);
        curvePoint.setTerm(2.0);
        curvePoint.setValue(3.0);
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
        List<CurvePoint> curvePoints = Arrays.asList(curvePoint);
        when(service.getAllCurvePoints()).thenReturn(curvePoints);

        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attributeExists("curvePoints"));

        verify(service, times(1)).getAllCurvePoints();
    }

    @Test
    void testAddCurveForm() throws Exception {
        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    void testValidate_success() throws Exception {
        when(service.getAllCurvePoints()).thenReturn(Arrays.asList(curvePoint));
        mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId", "1")
                        .param("term", "2.0")
                        .param("value", "3.0")
                )
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/curvePoint/list"));

        verify(service, times(1))
                .createCurvePoint(any(CurvePoint.class));
    }

    @Test
    void testValidate_fail() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                .param("term", "2.0")
                .param("value", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));

        verify(service, never()).createCurvePoint(any(CurvePoint.class));
    }

    @Test
    void testShowUpdateForm() throws Exception {
        when(service.getCurvePointById(1)).thenReturn(curvePoint);

        mockMvc.perform(get("/curvePoint/update/1"))
                .andExpect(status().isOk())
        .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeExists("curvePoint"));

        verify(service, times(1)).getCurvePointById(1);
    }

    @Test
    void testUpdateBid_WhenValid() throws Exception {
        when(service.getAllCurvePoints()).thenReturn(Arrays.asList(curvePoint));

        mockMvc.perform(post("/curvePoint/update/1")
                        .param("curveId", "1")
                        .param("term", "2.0")
                        .param("value", "6.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(service, times(1)).updateCurvePoint(any(CurvePoint.class));
    }

    @Test
    void testUpdateBid_fail() throws Exception {
        mockMvc.perform(post("/curvePoint/update/1")
                .param("curveId", "1")
                .param("term", "2.0")
                .param("value", ""))
        .andExpect(status().isOk())
        .andExpect(view().name("curvePoint/update"));

        verify(service, never()).updateCurvePoint(any(CurvePoint.class));
    }

    @Test
    void testDeleteBid() throws Exception {
        when(service.getAllCurvePoints()).thenReturn(Arrays.asList(curvePoint));

        mockMvc.perform(get("/curvePoint/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(service, times(1)).deleteCurvePoint(curvePoint.getId());
    }
}