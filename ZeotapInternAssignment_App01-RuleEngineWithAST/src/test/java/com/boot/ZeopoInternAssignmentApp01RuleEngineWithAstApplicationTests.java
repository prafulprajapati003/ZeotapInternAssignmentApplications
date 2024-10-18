package com.boot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.boot.model.Rule;
import com.boot.repository.RuleRepository;
import com.boot.service.RulesServiceImpl;

public class ZeopoInternAssignmentApp01RuleEngineWithAstApplicationTests {
    @Mock
    private RuleRepository ruleRepository;

    @InjectMocks
    private RulesServiceImpl ruleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks before each test
    }

    @Test
    public void testCreateRule() {
        String ruleString = "age > 30";
        Rule rule = new Rule(ruleString);
        when(ruleRepository.save(rule)).thenReturn(rule); // Mock the repository save method

        Rule createdRule = ruleService.createRule(ruleString); // Call the method to test
        assertEquals(ruleString, createdRule.getRuleString()); // Assert that the rule was created correctly
    }
}
