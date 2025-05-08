package com.nnk.springboot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListService;
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
public class BidListControllerTest {

    private static MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private BidListService service;

    @InjectMocks
    private BidListController controller;

    private static BidList bid;

    @BeforeAll
    static void setUp() {
        bid = new BidList();
        bid.setBidListId(1);
        bid.setAccount("account");
        bid.setType("type X");
        bid.setBidQuantity(10.0);
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
        List<BidList> bids = Arrays.asList(bid);
        when(service.getAllBidLists()).thenReturn(bids);

        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attributeExists("bidLists"));

        verify(service, times(1)).getAllBidLists();
    }

    @Test
    void testAddBidForm() throws Exception {
        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    @Test
    void testValidate_success() throws Exception {
        when(service.getAllBidLists()).thenReturn(Arrays.asList(bid));

        mockMvc.perform(post("/bidList/validate")
                        .param("account", "account")
                        .param("type", "type X")
                        .param("bidQuantity", "10.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(service, times(1)).createBidList(any(BidList.class));
    }

    @Test
    void testValidate_fail() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                        .param("account", "")
                        .param("type", "type X")
                        .param("bidQuantity", "10.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));

        verify(service, never()).createBidList(any(BidList.class));
    }

    @Test
    void testShowUpdateForm() throws Exception {
        when(service.getBidList(1)).thenReturn(bid);

        mockMvc.perform(get("/bidList/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeExists("bidList"));

        verify(service, times(1)).getBidList(1);
    }

    @Test
    void testUpdateBid_success() throws Exception {
        when(service.getAllBidLists()).thenReturn(Arrays.asList(bid));

        mockMvc.perform(post("/bidList/update/1")
                        .param("account", "account")
                        .param("type", "type X")
                        .param("bidQuantity", "20.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(service, times(1)).updateBidList(any(BidList.class));
    }

    @Test
    void testUpdateBid_fail() throws Exception {
        mockMvc.perform(post("/bidList/update/1")
                        .param("account", "") // Invalid field
                        .param("type", "type X")
                        .param("bidQuantity", "20.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"));

        verify(service, never()).updateBidList(any(BidList.class));
    }

    @Test
    void testDeleteBid() throws Exception {
        when(service.getAllBidLists()).thenReturn(Arrays.asList(bid));

        mockMvc.perform(get("/bidList/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        verify(service, times(1)).deleteBidList(1);
    }
}