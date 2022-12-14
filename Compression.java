/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dsproject;

import java.io.*;

/**
 *
 * @author Mai Esmail
 */
public class Compression extends HuffmanTree {
    
    private static final int DATA_RANGE = 256;
/**
 *compress of data in xml file in steps 
 * 1.build Huffman tree
 * 2.build code table
 * 3.write tree in output file for decoding
 * 4.write the length of the input stream for decoding
 * 5.use Huffman compression to encode the output
     * @param s
 **/
     public static void privateCompress(String s) {

        char[] inputChars = s.toCharArray();

        int[] freq = new int[DATA_RANGE];
        for (int i = 0; i < inputChars.length; i++)
            freq[inputChars[i]]++;
        
        Node root = buildTree(freq);
        
        String[] symbolTable = new String[DATA_RANGE];
        buildCodeTable(symbolTable, root, "");
        
        writeTree(root);

        CustomOutput.write(inputChars.length);

        
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
    
/**
 * by using privateCompresss func. we make compress func. with no param 
 * that takes file of xml and compress it without passing any parameters 
 **/    
     public static void compress(){
        
        String s = CustomInput.readString();
        CustomInput.close();

        privateCompress(s);
    }
/**
 * another compress function using PrivateCompress taking string s as param
 * and file to save compression output at it 
     * @param s
     * @param output
 **/
    public static void compress(String s, File output){

        PrintStream orgOutStream = System.out;

        try {
            System.setOut(new PrintStream(output));
        } catch (FileNotFoundException e) {
        }

        privateCompress(s);
        System.setOut(orgOutStream);
    }
/**
 *another compress function using Compress func.that takes no parameters
 * takes xml file as an input and save compression output in another file
     * @param input
     * @param output
 **/
    public static void compress(File input, File output) {
        InputStream orgInStream = System.in;
        PrintStream orgOutStream = System.out;

        try {
            System.setIn(new FileInputStream(input));
        } catch (FileNotFoundException e) {
        }
        try {
            System.setOut(new PrintStream(output));
        } catch (FileNotFoundException e) {
        }
        compress();

        System.setIn(orgInStream);
        System.setOut(orgOutStream);

    }

    
}