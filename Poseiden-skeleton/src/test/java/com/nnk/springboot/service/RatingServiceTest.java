package com.nnk.springboot.service;

import static org.mockito.Mockito.*;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Optional;


@SpringBootTest
public class RatingServiceTest {

    @Mock
    private RatingRepository repository;
    @InjectMocks
    private RatingService service;

    private Rating rating;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("AAA");
        rating.setSandPRating("BBB");
        rating.setFitchRating("CCC");
        rating.setOrderNumber(2);

        service = new RatingService(repository);
    }
    // create __________________________________
    @DisplayName("Create rating")
    @Test
    void createRating_success() {
        // Arrange
        when(repository.save(any(Rating.class))).thenReturn(rating);
        // Act
        service.createRating(rating);
        // Assert
        verify(repository, times(1)).save(rating);
    }
    // read ____________________________________
    @DisplayName("Get rating")
    @Test
    void getRating_success() {
        // Arrange
        when(repository.findById(anyInt())).thenReturn(Optional.of(rating));
        // Act
        service.getRatingById(rating.getId());
        // Assert
        verify(repository, times(1)).findById(rating.getId());
    }
    @DisplayName("Get all ratings")
    @Test
    void getAllRatings_success() {
        // Arrange
        when(repository.findAll()).thenReturn(Arrays.asList(rating));
        // Act
        service.getAllRatings();
        // Assert
        verify(repository, times(1)).findAll();
    }
    // update __________________________________
    @DisplayName("Update a rating")
    @Test
    void updateRating_success() {
        // Arrange
        when(repository.findById(anyInt())).thenReturn(Optional.of(rating));
        rating.setMoodysRating("III");
        // Act
        service.updateRating(rating);
        // Assert
        verify(repository, times(1)).save(rating);
        assert(rating.getMoodysRating().equals("III"));
    }
    // delete __________________________________
    @DisplayName("Delete a rating")
    @Test
    void deleteRating_success() {
        // Arrange
        when(repository.existsById(anyInt())).thenReturn(true);
        // Act
        service.deleteRating(rating.getId());
        // Assert
        verify(repository, times(1)).deleteById(rating.getId());
    }
}
