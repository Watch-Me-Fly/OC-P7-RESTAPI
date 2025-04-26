package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TradeServiceTest {
    @Mock
    private TradeRepository repository;
    @InjectMocks
    private TradeService service;

    private Trade trade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("account-123");
        trade.setType("Buy");
        trade.setBuyQuantity(15.00);
        trade.setSellQuantity(0.0);
        trade.setBuyPrice(500.25);
        trade.setSellPrice(null);

        service = new TradeService(repository);
    }
    // Create _____________________________
    @DisplayName("Create a trade")
    @Test
    void createTrade_success() {
        // Arrange
        when(repository.save(any(Trade.class))).thenReturn(trade);
        // Act
        service.createTrade(trade);
        // Assert
        verify(repository, times(1)).save(any(Trade.class));
    }
    // Read   _____________________________
    @DisplayName("Get a trade by id")
    @Test
    void getTrade_success() {
        // Arrange
        when(repository.findById(anyInt())).thenReturn(Optional.of(trade));
        // Act
        Trade retrieved = service.getTradeById(trade.getTradeId());
        // Assert
        assertNotNull(retrieved);
        assertEquals(trade.getTradeId(), retrieved.getTradeId());
    }
    @DisplayName("Get a list of trades")
    @Test
    void getTrades_success() {
        // Arrange
        when(repository.findAll()).thenReturn(List.of(trade));
        // Act
        List<Trade> allTrades = service.getAllTrades();
        // Assert
        assertNotNull(allTrades);
        assertEquals(1, allTrades.size());
    }
    // Update _____________________________
    @DisplayName("Update a trade")
    @Test
    void updateTrade_success() {
        // Arrange
        when(repository.findById(anyInt())).thenReturn(Optional.of(trade));
        trade.setAccount("account-456");
        // Act
        service.updateTrade(trade);
        // Assert
        verify(repository, times(1)).findById(anyInt());
        verify(repository, times(1)).save(any(Trade.class));
        assert("account-456".equals(trade.getAccount()));
    }
    // Delete _____________________________
    @DisplayName("Delete a trade")
    @Test
    void deleteTrade_success() {
        // Arrange
        when(repository.existsById(anyInt())).thenReturn(true);
        // Act
        service.deleteTrade(trade.getTradeId());
        // Assert
        verify(repository, times(1)).existsById(anyInt());
        verify(repository, times(1)).deleteById(anyInt());
    }

}
