/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package com.mycompany.dsproject;

import java.io.BufferedOutputStream;
import java.io.IOException;

/**
 *
 * @author habib
 */
public class CustomOutput {

 private static BufferedOutputStream out;    
    private static int buffer;                  // buffer is 8-bit 
    private static int num;                       // the remaining bits number in buffer
    private static boolean isItInitialized;       

    private CustomOutput() {
    }

    private static void initialize() {
        out = new BufferedOutputStream(System.out);
        buffer = 0;
        num = 0;
        isItInitialized = true;
    }

    private static void writeBoolean(boolean x) {
        if (!isItInitialized) initialize();
        buffer <<= 1;
        if (x) buffer |= 1;
        num++;
        if (num == 8) clearBuffer();
    }

    private static void writeByte(int x) {
        if (!isItInitialized) initialize();
        assert x >= 0 && x < 256;
        if (num == 0) {
            try {
                out.write(x);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        for (int i = 0; i < 8; i++) {
            boolean b = (x >>> (8 - 1 - i) & 1) == 1;
            writeBoolean(b);
        }
    }

    private static void clearBuffer() {
        if (!isItInitialized) initialize();
        if (num == 0) return;
        if (num > 0)
            buffer <<= (8 - num);
        try {
            out.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        buffer = 0;
        num = 0;
    }

    private static void flush() {
        clearBuffer();
        try {
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void close() {
        flush();
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        isItInitialized = false;
    }

    public static void write(boolean x) {
        writeBoolean(x);
    }

    public static void write(byte x) {
        writeByte(x & 0xff);
    }

    public static void write(int x) {
        writeByte((x >>> 24) & 0xff);
        writeByte((x >>> 16) & 0xff);
        writeByte((x >>> 8) & 0xff);
        writeByte((x) & 0xff);
    }

    public static void write(char x) {
        writeByte((int) x);
    }

    public static void write(String s) {
        for (int i = 0; i < s.length(); i++)
            write(s.charAt(i));
    }
}