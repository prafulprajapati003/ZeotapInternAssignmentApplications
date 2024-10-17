package com.boot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Node {
    private String type; // "operator" or "operand"
    private Node left;   // Reference to the left child
    private Node right;  // Reference to the right child
    private String value; // Value for operand nodes


}
