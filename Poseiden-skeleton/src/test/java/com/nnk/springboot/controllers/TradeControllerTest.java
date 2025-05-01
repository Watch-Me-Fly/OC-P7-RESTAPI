package com.nnk.springboot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeService;
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
public class TradeControllerTest {

    private static MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private TradeService service;
    @InjectMocks
    private TradeController controller;

    private static Trade trade;

    @BeforeAll
    static void setUp() {
        trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("account-123");
        trade.setType("Buy");
        trade.setBuyQuantity(15.00);
        trade.setSellQuantity(0.0);
        trade.setBuyPrice(500.25);
        trade.setSellPrice(null);
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
        List<Trade> trades = Arrays.asList(trade);
        when(service.getAllTrades()).thenReturn(trades);

        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attributeExists("trades"));

        verify(service, times(1)).getAllTrades();
    }
    @Test
    void testAddTrade() throws Exception {
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }
    /*
    @Test
    void testValidate_success() throws Exception {
        when(service.getAllTrades()).thenReturn(Arrays.asList(trade));

        mockMvc.perform(post("/trade/validate")
                        .param("account", trade.getAccount())
                        .param("type", trade.getType())
                        .param("quantity", String.valueOf(trade.getBuyQuantity()))
                        .param("sellQuantity", String.valueOf(trade.getSellQuantity()))
                        .param("buyPrice", String.valueOf(trade.getBuyPrice()))
                        .param("sellPrice", String.valueOf(trade.getSellPrice()))
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        verify(service, times(1)).createTrade(any(Trade.class));
    }

     */
    @Test
    void testValidate_fail() throws Exception {
        mockMvc.perform(post("/trade/validate")
                        .param("account", "")
                        .param("type", trade.getType())
                        .param("quantity", String.valueOf(trade.getBuyQuantity())))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));

        verify(service, never()).createTrade(any(Trade.class));
    }
    @Test
    void testShowUpdateForm() throws Exception {
        when(service.getAllTrades()).thenReturn(Arrays.asList(trade));

        mockMvc.perform(post("/trade/update/1")
                .param("account", trade.getAccount())
                .param("type", trade.getType())
                .param("quantity", String.valueOf(trade.getBuyQuantity())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        verify(service, times(1)).updateTrade(any(Trade.class));
    }
    @Test
    void testUpdateTrade() throws Exception {
        mockMvc.perform(post("/trade/update/1")
                .param("account", "updated account")
                .param("account", trade.getAccount())
                .param("type", trade.getType())
                .param("quantity", String.valueOf(trade.getBuyQuantity()))
                .param("sellQuantity", String.valueOf(trade.getSellQuantity()))
                .param("buyPrice", String.valueOf(trade.getBuyPrice()))
                .param("sellPrice", String.valueOf(trade.getSellPrice())))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"));

        verify(service, never()).updateTrade(any(Trade.class));
    }
    @Test
    void testDeleteTrade() throws Exception {
        when(service.getAllTrades()).thenReturn(Arrays.asList(trade));

        mockMvc.perform(get("/trade/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        verify(service, times(1)).deleteTrade(1);
    }

}
