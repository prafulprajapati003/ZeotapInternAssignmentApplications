package com.boot.service;

import java.util.List;
import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.model.Node;
import com.boot.model.Rule;
import com.boot.repository.RuleRepository;

@Service
public class RulesServiceImpl implements IRuleService {

	@Autowired
	private RuleRepository ruleRepository;

	@Override
	public Rule createRule(String rules) {
		return ruleRepository.save(new Rule(rules));

	}

	@Override
	public List<Rule> getAllRules() {
		return ruleRepository.findAll();
	}

	@Override

	public Node createAST(String ruleString) {
		Stack<Node> stack = new Stack<>();
		String[] tokens = ruleString.split(" ");

		for (String token : tokens) {
			if (token.equals("AND") || token.equals("OR")) {
				Node right = stack.pop();
				Node left = stack.pop();
				Node operatorNode = new Node("operator", left, right, token);
				stack.push(operatorNode);
			} else {
				String[] parts = token.split("=");
				if (parts.length == 2) {
					String field = parts[0].trim();
					String value = parts[1].trim().replace("'", ""); // Remove quotes for string values
					Node operandNode = new Node("operand", null, null, field + "=" + value);
					stack.push(operandNode);
				} else {
					String[] comparisonParts = token.split("([<>]=?|!=)");
					String comparisonOperator = token.replace(comparisonParts[0], "").replace(comparisonParts[1], "")
							.trim();
					if (comparisonParts.length == 2) {
						String field = comparisonParts[0].trim();
						String value = comparisonParts[1].trim();
						Node operandNode = new Node("operand", null, null,
								field + " " + comparisonOperator + " " + value);
						stack.push(operandNode);
					}
				}
			}
		}

		return stack.pop(); // The root of the AST
	}

	@Override
	/*public Node combineRules(List<String> rules) {
		if (rules.isEmpty()) {
			return null;
		}

		Node root = new Node("operator", null, null, "OR"); // Root node for combined AST
		Node lastNode = null;

		for (String rule : rules) {
			Node currentAST = createAST(rule); // Create AST for the current rule
			if (lastNode == null) {
				root.left = currentAST; // First AST becomes the left child
			} else {
				root.right = currentAST; // Subsequent ASTs connect to the right
				Node newRoot = new Node("operator", lastNode, currentAST, "OR");
				lastNode = newRoot;
				root.right = lastNode; // Update the root to the new combined root
			}
			lastNode = currentAST; // Keep track of the last node
		}

		return root; // Return the combined AST
	}*/

	public Node combineRules(List<String> rules) {
	    if (rules.isEmpty()) {
	        return null;
	    }

	    Node root = null; // Initialize root

	    for (String rule : rules) {
	        Node currentAST = createAST(rule); // Create AST for the current rule

	        if (root == null) {
	            root = currentAST; // First rule's AST becomes the root
	        } else {
	            // Combine existing root with the current AST using an OR operator
	            root = new Node("operator", root, currentAST, "OR");
	        }
	    }

	    return root; // Return the combined AST
	}

}
