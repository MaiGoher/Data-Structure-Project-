/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dsproject;

import java.io.*;

/**
 *
 * @author Maiosha
 */
public class Decompression extends HuffmanTree {
    
    /**
     * Decompress xml file in steps 
     * 1. read the Huffman tree first from the input
     * 2.read the length of the stream
     * 3.read bit by bit
     **/
    public static void decompress() {

       
        Node root = readTree();
 
        int length = CustomInput.readInt();

        for (int i = 0; i < length; i++) {
            Node x = root;
            while (!x.isLeaf()) {
                
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
        }
        try {
            System.setOut(new PrintStream(output));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        decompress();

        System.setIn(orgInStream);
        System.setOut(orgOutStream);

    }
    
}
