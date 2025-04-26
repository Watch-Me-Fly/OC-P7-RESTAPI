package com.nnk.springboot.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class CurveServiceTest {

    @Mock
    private CurvePointRepository repository;
    @InjectMocks
    private CurveService service;

    private CurvePoint curvePoint;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(1);
        curvePoint.setTerm(2.0);
        curvePoint.setValue(3.0);

        service = new CurveService(repository);
    }
    // Create _____________________________
    @DisplayName("Create a curve point")
    @Test
    public void createCurvePointTest_success() {
        // Arrange
        when(repository.save(any(CurvePoint.class))).thenReturn(curvePoint);
        // Act
        service.createCurvePoint(curvePoint);
        // Assert
        verify(repository, times(1)).save(any(CurvePoint.class));
    }
    // Read   _____________________________
    @DisplayName("Get a curve point")
    @Test
    void getCurvePointTest_success() {
        // Arrange
        when(repository.findById(anyInt())).thenReturn(Optional.of(curvePoint));
        // Act
        CurvePoint retrieved = service.getCurvePointById(curvePoint.getId());
        // Assert
        assertNotNull(retrieved);
        assertEquals(curvePoint, retrieved);
    }
    @DisplayName("Get all curve points")
    @Test
    void getAllCurvePointsTest_success() {
        // Arrange
        when(repository.findAll()).thenReturn(Arrays.asList(curvePoint));
        // Act
        List<CurvePoint> curveList = service.getAllCurvePoints();
        // Assert
        assertEquals(1, curveList.size());
    }
    // Update _____________________________
    @DisplayName("Update a curve point")
    @Test
    void updateCurvePointTest_success() {
        // Arrange
        when(repository.findById(anyInt())).thenReturn(Optional.of(curvePoint));
        curvePoint.setValue(4.0);
        // Act
        service.updateCurvePoint(curvePoint);
        // Assert
        verify(repository, times(1)).save(any(CurvePoint.class));
        assert(4.0 == curvePoint.getValue());
    }
    // Delete _____________________________
    @DisplayName("Delete a curve point")
    @Test
    void deleteCurvePointTest_success() {
        // Arrange
        when(repository.existsById(1)).thenReturn(true);
        // Act
        service.deleteCurvePoint(curvePoint.getId());
        // Assert
        verify(repository, times(1)).existsById(anyInt());
        verify(repository, times(1)).deleteById(curvePoint.getId());
    }
}
