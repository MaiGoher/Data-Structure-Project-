/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dsproject;

import java.util.PriorityQueue;

/**
 *
 * @author Maiosha
 */
  class Node implements Comparable<Node> {

        public final char ch;
        public final int freq;
        public final Node left;
        public final Node right;

        Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }
        
/**
 *comparator  helps to compare the node on the basis of one of its attribute.Here we will
 * be compared on the basis of data values of the nodes.
         * @param that
 **/
        @Override
        public int compareTo(Node that) {
            return (this.freq - that.freq);
        }

        public boolean isLeaf() {
            assert ((left == null) && (right == null)) || ((left != null) && (right != null));
            return ((this.left == null) && (this.right == null));
        }
    }

public class HuffmanTree {
    
    private static final int DATA_RANGE = 256;
    
/**
     * @return 
 **/
public static Node readTree() {

        boolean isLeafNode = CustomInput.readBoolean();
        if (isLeafNode) {
            return new Node(CustomInput.readChar(), -1, null, null);
        } 
        
        else {
            return new Node('\0', -1, readTree(), readTree());
        }
    
}


public static void writeTree(Node node) {
        if (node.isLeaf()) {
            CustomOutput.write(true);
            CustomOutput.write(node.ch);
            return;
        }
        CustomOutput.write(false);
        writeTree(node.left);
        writeTree(node.right);
    }

/**
 build tree from bottom using Priority Queue by getting min and next min and add them together as parent node 
 and make that recursively 
     * @param freq
     * @return 
 **/
public static Node buildTree(int[] freq) {
        // using priority queue
        PriorityQueue<Node> pqNodes = new PriorityQueue<>();

        for (char c = 0; c < DATA_RANGE; c++)
            if (freq[c] > 0)
                pqNodes.add(new Node(c, freq[c], null, null));

        while (pqNodes.size() > 1) {
            Node left = pqNodes.poll();
            Node right = pqNodes.poll();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            pqNodes.add(parent);
        }
        return pqNodes.poll();
    }

/** build code table for each char in the xml file by go in depth in tree if i go in left put in code 0 
    if i go right put and increase zeroes and ones to make code for each char 
     * @param symbolTable 
     * @param node 
     * @param code 
 **/
 public static void buildCodeTable(String[] symbolTable, Node node, String code) {
        if (!node.isLeaf()) {
            buildCodeTable(symbolTable, node.left, code + '0');
            buildCodeTable(symbolTable, node.right, code + '1');
        } else {
            symbolTable[node.ch] = code;
        }
    }

    
}
