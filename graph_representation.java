/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package project;

import java.io.*;
import java.util.ArrayList;

public class graph_representation {
    
 
    
    private static ArrayList<ArrayList<Integer>> extract_IDs(String xml_input) {

        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        ArrayList<Integer> sub_list = new ArrayList<>();
        StringBuffer id_number = new StringBuffer("0");
        xml_input = Prettifying .Prettify(xml_input);
        xml_input = xml_input.replaceAll("\\s", "");
        for (int i = 0; i < xml_input.length(); i++) {
            if (xml_input.charAt(i) == '<' && xml_input.charAt(i + 1) == 'i' && xml_input.charAt(i + 2) == 'd' && xml_input.charAt(i + 3) == '>') {
                for(int x = (i + 4) ; xml_input.charAt(x) != '<' ; x++){
                    id_number.append(xml_input.charAt(x));
                }
                int c_integer = Integer.parseInt(String.valueOf(id_number));
                sub_list.add(c_integer);
                id_number.delete(0 , id_number.length());
            }
            if (xml_input.charAt(i) == '<' && xml_input.charAt(i + 1) == '/' && xml_input.charAt(i + 2) == 'u' && xml_input.charAt(i + 3) == 's' && xml_input.charAt(i + 4) == 'e' && xml_input.charAt(i + 5) == 'r' && xml_input.charAt(i + 6) == '>') {
                list.add(new ArrayList<>(sub_list));
                sub_list.clear();
            }
        }
        return list;
    }
   

    private static void writeDot(ArrayList<ArrayList<Integer>> list){
        File file = new File("graph.dot");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        PrintWriter pw = null;
        try {
            pw = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        pw.println("digraph {");
        for(int i = 0 ; i < list.size(); i++){
            ArrayList<Integer> small_list = list.get(i);
            for(int j = 0 ; j < small_list.size(); j++){
                if((j + 1) < small_list.size()){
                    pw.println(small_list.get(j + 1) + " -> " + small_list.get(0));
                }
            }
        }
        pw.println("}");
        pw.close();
    }

    public static void draw (String s){
        ArrayList<ArrayList<Integer>> IDs_list = extract_IDs(s);
        writeDot(IDs_list);
        try {
            Runtime.getRuntime().exec("dot -Tpng -O graph.dot");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }


