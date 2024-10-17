package com.boot.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.boot.model.Node;

@Service
public class RuleEvaluatorService {
	public boolean evaluateRule(Node ast, Map<String, Object> data) {
		if (ast == null) {
			return false; // Handle null AST
		}

		switch (ast.getType()) {
		case "operand":
			return evaluateOperand(ast.getValue(), data);
		case "operator":
			return evaluateOperator(ast, data);
		default:
			throw new IllegalArgumentException("Unknown AST node type: " + ast.getType());
		}
	}

	private boolean evaluateOperand(String value, Map<String, Object> data) {
		String[] parts = value.split(" ");
		String field = parts[0];
		String operator = parts[1];
		String operandValue = parts[2];

		Object dataValue = data.get(field);

		// Handle the comparison based on the operator
		return switch (operator) {
		case "=" -> dataValue != null && dataValue.toString().equals(operandValue);
		case "!=" -> dataValue == null || !dataValue.toString().equals(operandValue);
		case ">" -> ((Comparable) dataValue).compareTo(convertValue(operandValue)) > 0;
		case "<" -> ((Comparable) dataValue).compareTo(convertValue(operandValue)) < 0;
		case ">=" -> ((Comparable) dataValue).compareTo(convertValue(operandValue)) >= 0;
		case "<=" -> ((Comparable) dataValue).compareTo(convertValue(operandValue)) <= 0;
		default -> throw new IllegalArgumentException("Unknown operator: " + operator);
		};
	}

	private Object convertValue(String operandValue) {
	    if (operandValue == null) return null; // Handle null input

	    // Attempt to parse as Integer or Double
	    try {
	        return Integer.parseInt(operandValue); // Try Integer
	    } catch (NumberFormatException ignored) { }

	    try {
	        return Double.parseDouble(operandValue); // Try Double
	    } catch (NumberFormatException ignored) { }

	    return operandValue; // Return as String if both conversions fail
	}

	private boolean evaluateOperator(Node ast, Map<String, Object> data) {
		boolean leftValue = evaluateRule(ast.getLeft(), data);
		boolean rightValue = evaluateRule(ast.getRight(), data);

		return switch (ast.getValue()) {
		case "AND" -> leftValue && rightValue;
		case "OR" -> leftValue || rightValue;
		default -> throw new IllegalArgumentException("Unknown operator: " + ast.getValue());
		};
	}

}
