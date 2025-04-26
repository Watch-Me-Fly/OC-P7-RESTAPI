package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class RuleNameServiceTest {

    @Mock
    private RuleNameRepository repository;
    @InjectMocks
    private RuleNameService service;

    private RuleName ruleName;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("New Rule");
        ruleName.setDescription("description of the rule.");
        ruleName.setJson("{\"field\":\"value\"}");
        ruleName.setTemplate("template example");
        ruleName.setSqlStr("SELECT * FROM table WHERE field = ?");
        ruleName.setSqlPart("WHERE field = ?");

        service = new RuleNameService(repository);
    }
    // Create _____________________________
    @DisplayName("Create a rule name")
    @Test
    void createRuleName_success() {
        // Arrange
        when(repository.save(any(RuleName.class))).thenReturn(ruleName);
        // Act
        service.createRuleName(ruleName);
        // Assert
        verify(repository, times(1)).save(ruleName);
    }
    // Read   _____________________________
    @DisplayName("Get a rule successfully")
    @Test
    void getRuleName_success() {
        // Arrange
        when(repository.findById(anyInt())).thenReturn(Optional.of(ruleName));
        // Act
        RuleName retrieved = service.getRuleNameById(ruleName.getId());
        // Assert
        assertNotNull(retrieved);
        assertEquals(ruleName, retrieved);
    }
    @DisplayName("Get a list of rules")
    @Test
    void getRuleNameList_success() {
        // Arrange
        when(repository.findAll()).thenReturn(List.of(ruleName));
        // Act
        List<RuleName> allRules = service.getAllRuleNames();
        // Assert
        assertEquals(ruleName, allRules.get(0));
        assertEquals(1, allRules.size());
    }
    // Update _____________________________
    @DisplayName("Update rule name")
    @Test
    void updateRuleName_success() {
        // Arrange
        when(repository.findById(anyInt())).thenReturn(Optional.of(ruleName));
        ruleName.setDescription("new description");
        // Act
        service.updateRuleName(ruleName);
        // Assert
        verify(repository, times(1)).findById(anyInt());
        verify(repository, times(1)).save(any(RuleName.class));
        assert("new description".equals(ruleName.getDescription()));
    }
    // Delete _____________________________
    @DisplayName("Delete rule name")
    @Test
    void deleteRuleName_success() {
        // Arrange
        when(repository.existsById(anyInt())).thenReturn(true);
        // Act
        service.deleteRuleName(ruleName.getId());
        // Assert
        verify(repository, times(1)).existsById(anyInt());
        verify(repository, times(1)).deleteById(anyInt());
    }
}