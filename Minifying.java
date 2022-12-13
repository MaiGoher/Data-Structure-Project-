/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package com.mycompany.dsproject;
/**
 *
 * @author habib
 */
public class Minifying {

    /**
     * @param args the command line arguments
     */
 	public static String minify(String xml) {
		if (xml == null || xml.trim().length() == 0) return "";
		StringBuilder x = new StringBuilder();
		String[] rows = xml.trim().replaceAll(">", ">\n").replaceAll("<", "\n<").split("\n"); // Separate the XML into Row
		for (int i = 0; i < rows.length; i++)
		{	
			if (rows[i] == null || rows[i].trim().length() == 0) 
				continue;
			rows[i] = rows[i].trim();
			x.append(rows[i]);
		}
		return x.toString();
        }
}
