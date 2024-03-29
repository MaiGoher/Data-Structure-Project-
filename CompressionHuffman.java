/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.PriorityQueue;

/**
 *
 * @author Mai Esmail
 */
public class CompressionHuffman {

 private static final int DATA_RANGE = 256;


    private static void privateCompress(String s) {

        char[] inputChars = s.toCharArray();

        int[] freq = new int[DATA_RANGE];
        for (int i = 0; i < inputChars.length; i++)
            freq[inputChars[i]]++;

        // build Huffman trie
        Node root = buildTrie(freq);

        // build code table
        String[] symbolTable = new String[DATA_RANGE];
        buildCodeTable(symbolTable, root, "");


        // write trie in output file for decoding
        writeTrie(root);

        // write the length of the input stream for decoding
         CustomOutput.write(inputChars.length);

        // use huffman compression to encode the output
        for (int i = 0; i < inputChars.length; i++) {
            String code = symbolTable[inputChars[i]];
            for (int j = 0; j < code.length(); j++) {
                if (code.charAt(j) == '1')
                     CustomOutput.write(true);
                else if (code.charAt(j) == '0')
                     CustomOutput.write(false);
                else
                    throw new IllegalStateException("either 0 or 1, illegal state");
            }
        }
         CustomOutput.close();
    }

    public static void compress(){
        // build the char[] input
        String s = CustomInput.readString();
        CustomInput.close();

        privateCompress(s);
    }

    public static void compress(String s, File output){

        PrintStream orgOutStream = System.out;

        try {
            System.setOut(new PrintStream(output));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        privateCompress(s);
        System.setOut(orgOutStream);
    }

    public static void compress(File input, File output) {
        InputStream orgInStream = System.in;
        PrintStream orgOutStream = System.out;

        try {
            System.setIn(new FileInputStream(input));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            System.setOut(new PrintStream(output));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        compress();

        System.setIn(orgInStream);
        System.setOut(orgOutStream);

    }


    private static Node buildTrie(int[] freq) {
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

    private static void writeTrie(Node node) {
        if (node.isLeaf()) {
             CustomOutput.write(true);
             CustomOutput.write(node.ch);
            return;
        }
         CustomOutput.write(false);
        writeTrie(node.left);
        writeTrie(node.right);
    }

    private static void buildCodeTable(String[] symbolTable, Node node, String code) {
        if (!node.isLeaf()) {
            buildCodeTable(symbolTable, node.left, code + '0');
            buildCodeTable(symbolTable, node.right, code + '1');
        } else {
            symbolTable[node.ch] = code;
        }
    }

    public static void expand() {

        // read the Huffman trie first from the input
        Node root = readTrie();

        // read the length of the stream
        int length = CustomInput.readInt();

        for (int i = 0; i < length; i++) {
            Node x = root;
            while (!x.isLeaf()) {
                // read bit
                boolean b = CustomInput.readBoolean();
                if (b)
                    x = x.right;
                else
                    x = x.left;
            }
             CustomOutput.write(x.ch);
        }
        CustomInput.close();
        CustomOutput.close();
    }

    public static void decompress(File input, File output) {
        InputStream orgInStream = System.in;
        PrintStream orgOutStream = System.out;

        try {
            System.setIn(new FileInputStream(input));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            System.setOut(new PrintStream(output));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        expand();

        System.setIn(orgInStream);
        System.setOut(orgOutStream);

    }

    private static Node readTrie() {
        boolean isLeafNode = CustomInput.readBoolean();
        if (isLeafNode) {
            return new Node(CustomInput.readChar(), -1, null, null);
        } else {
            return new Node('\0', -1, readTrie(), readTrie());
        }
    }

    private static class Node implements Comparable<Node> {

        private final char ch;
        private final int freq;
        private final Node left, right;

        Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        @Override
        public int compareTo(Node that) {
            return Integer.compare(this.freq, that.freq);
        }

        private boolean isLeaf() {
            return ((this.left == null) && (this.right == null));
        }
    }

}
