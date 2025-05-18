package com.nnk.springboot.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class BidListServiceTest {

    @Mock
    private BidListRepository repository;

    @InjectMocks
    private BidListService service;

    private BidList bid;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bid = new BidList();
        bid.setId(1);
        bid.setAccount("account");
        bid.setType("type X");
        bid.setBidQuantity(10.0);

        service = new BidListService(repository);
    }

    // Create _____________________________
    @DisplayName("Create a bid successfully")
    @Test
    void createBidTest_success() {
        // Arrange
        when(repository.save(any(BidList.class))).thenReturn(bid);
        // Act
        service.createBidList(bid);
        // Assert
        verify(repository, times(1)).save(any(BidList.class));
    }
    @DisplayName("Create a bid, no account")
    @Test
    void createBidTest_ThrowsException_NoAccount() {
        // Arrange
        bid.setAccount(null);
        // Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                service.createBidList(bid));
        assertEquals("Un compte est obligatoire", exception.getMessage());
    }
    // Read   _____________________________
    @DisplayName("Get a bid by id")
    @Test
    void getBidByIdTest_success() {
        // Arrange
        when(repository.findById(anyInt())).thenReturn(Optional.of(bid));
        // Act
        BidList retrievedBidList = service.getBidList(bid.getId());
        // Assert
        assertNotNull(retrievedBidList);
        assertEquals(bid, retrievedBidList);
    }
    @DisplayName("Get all bids")
    @Test
    void getAllBidsTest_success() {
        // Arrange
        when(repository.findAll()).thenReturn(List.of(bid));
        // Act
        List<BidList> allBids = service.getAllBidLists();
        // Assert
        assertEquals(bid, allBids.get(0));
        assertEquals(1, allBids.size());
    }
    // Update _____________________________
    @DisplayName("Update a bid")
    @Test
    void updateBidTest_success() {
        // Arrange
        when(repository.existsById(anyInt())).thenReturn(true);
        bid.setAccount("new account");
        // Act
        service.updateBidList(bid);
        // Assert
        verify(repository, times(1)).existsById(anyInt());
        verify(repository, times(1)).save(any(BidList.class));
        assert("new account".equals(bid.getAccount()));
    }
    // Delete _____________________________
    @DisplayName("Delete a bid")
    @Test
    void deleteBidTest_success() {
        // Arrange
        when(repository.existsById(anyInt())).thenReturn(true);
        // Act
        service.deleteBidList(bid.getId());
        // Assert
        verify(repository, times(1)).existsById(anyInt());
        verify(repository, times(1)).deleteById(anyInt());
    }

}
