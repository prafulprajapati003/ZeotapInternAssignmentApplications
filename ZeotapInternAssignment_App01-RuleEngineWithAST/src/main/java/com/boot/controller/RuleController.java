package com.boot.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.boot.model.Node;
import com.boot.model.Rule;
import com.boot.service.IRuleService;
import com.boot.service.RuleEvaluatorService;

@Controller
@RequestMapping("/rules")
public class RuleController {
	@Autowired
	private IRuleService ruleService;

	@Autowired
	private RuleEvaluatorService ruleEvaluator;

	@GetMapping("/")
	public String showCreateRuleForm(Model model) {
		return "index";
	}

	@PostMapping
	public String createRule(@RequestParam String ruleString) {
		ruleService.createRule(ruleString);
		return "create_rule";
	}

	@GetMapping("/evaluate")
	public String showEvaluateRuleForm(Model model) {
		return "evaluate_rule";
	}

	@PostMapping("/evaluate")
	public String evaluateRule(@RequestParam Map<String, Object> attributes, Model model) {
		String ruleString = attributes.get("ruleString").toString();
		Node ast = ruleService.createAST(ruleString);
		boolean result = ruleEvaluator.evaluateRule(ast, attributes);
		model.addAttribute("result", result);
		return "evaluate_rule"; // Return the same page with result
	}

	@GetMapping("/view")
	public String viewRules(Model model) {
		List<Rule> rules = ruleService.getAllRules();
		model.addAttribute("rules", rules);
		return "view_rules"; // Create this view if needed
	}
}
