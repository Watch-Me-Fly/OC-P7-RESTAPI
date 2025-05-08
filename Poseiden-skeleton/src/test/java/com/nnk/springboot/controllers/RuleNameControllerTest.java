package com.nnk.springboot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.RuleNameService;
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
public class RuleNameControllerTest {

    private static MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private RuleNameService service;
    @InjectMocks
    private RuleNameController controller;

    private static RuleName ruleName;

    @BeforeAll
    static void setUp() {
        ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("New Rule");
        ruleName.setDescription("description of the rule.");
        ruleName.setJson("{\"field\":\"value\"}");
        ruleName.setTemplate("template example");
        ruleName.setSqlStr("SELECT * FROM table WHERE field = ?");
        ruleName.setSqlPart("WHERE field = ?");
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
        List<RuleName> ruleList = Arrays.asList(ruleName);
        when(service.getAllRuleNames()).thenReturn(ruleList);

        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attributeExists("ruleNamesList"));
    }
    @Test
    void testAddRule() throws Exception {
        mockMvc.perform(
                get("/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }
    @Test
    void testValidate_success() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                        .param("name", ruleName.getName())
                        .param("description", ruleName.getDescription())
                        .param("json", ruleName.getJson())
                        .param("template", ruleName.getTemplate())
                        .param("sqlStr", ruleName.getSqlStr())
                        .param("sqlPart", ruleName.getSqlPart()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(service, times(1)).createRuleName(any(RuleName.class));
    }
    @Test
    void testValidate_fail() throws Exception {
        mockMvc.perform(post("/ruleName/validate"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));

        verify(service, never()).createRuleName(any(RuleName.class));
    }
    @Test
    void testShowUpdateForm() throws Exception {
        when(service.getRuleNameById(1)).thenReturn(ruleName);

        mockMvc.perform(get("/ruleName/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attributeExists("ruleName"));

        verify(service, times(1)).getRuleNameById(1);
    }
    @Test
    void testUpdateRule() throws Exception {
        mockMvc.perform(post("/ruleName/update/1")
                        .param("name", "Updated Rule")
                .param("description", ruleName.getDescription())
                .param("json", ruleName.getJson())
                .param("template", ruleName.getTemplate())
                .param("sqlStr", ruleName.getSqlStr())
                .param("sqlPart", ruleName.getSqlPart()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(service, times(1)).updateRuleName(any(RuleName.class));
    }
    @Test
    void testDeleteRule() throws Exception {
        mockMvc.perform(get("/ruleName/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        verify(service, times(1)).deleteRuleName(1);
    }
}
