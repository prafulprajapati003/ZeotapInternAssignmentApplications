package com.boot.service;

import java.util.List;

import com.boot.model.Node;
import com.boot.model.Rule;

public interface IRuleService {
	
	public Rule createRule(String rules);
	
	public List<Rule> getAllRules();
	
	public Node createAST(String rules);

	public Node combineRules(List<String> rules);
}
