/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Maram Ahmed , Esraa Amr
 */
public class Checkconsistence {

 private String[] Lines = null;
    public static ArrayList<String> IncorrectTags;

    public static ArrayList<Integer> IncorrectTagsLine;

    public int Counter_error = 0;

 
    public Checkconsistence(String file) {

        Lines = file.replaceAll(" ", "")
                .replaceAll("<", "\n<")
                .replaceAll(">", ">\n")
                .split("\n");

        IncorrectTags = new ArrayList<>();
        IncorrectTagsLine = new ArrayList<>();
    }

    public boolean isOpeningTag(String s) {
        int len = s.length();
        if (len > 1)
            return (s.charAt(0) == '<') && (s.charAt(1) != '/') && (s.charAt(len - 1) == '>');

        return false;
    }

    public boolean isClosingTag(String s) {
        int len = s.length();
        if (len > 1)
            return (s.charAt(0) == '<') && (s.charAt(1) == '/') && (s.charAt(len - 1) == '>');

        return false;
    }

    public boolean NoTag(String s) {
        return !isOpeningTag(s) && !isClosingTag(s);
    }

    public boolean compareTop(String Tag, String topStack) {
        StringBuilder s = new StringBuilder();

        for (int i = 0; i < Tag.length(); i++) {
            if (Tag.charAt(i) != '/') {
                s.append(Tag.charAt(i));
            }
        }

        return topStack.equals(s.toString());
    }
    
    public int getErrorCounter() {
        return Counter_error;
    }
    
    public ArrayList<String> getWrongTags(){

        return IncorrectTags;
    }
    public ArrayList<Integer> getWrongTagsLines(){

        return IncorrectTagsLine;
    }
    
    
    public String correctTag_str(String s) {
        StringBuilder File_correct = new StringBuilder();
        Checkconsistence c1 = new Checkconsistence(s);
        

        for (String line : Lines) {
            if (NoTag(line)) {
                if ((line.startsWith("<")) && !(line.endsWith(">"))) {
                    int indexOfClosingBracket = line.indexOf(">");
                    if (indexOfClosingBracket == -1) {
                        File_correct.append(line).append(">").append("\n");
                    } else {
                        File_correct.append(line).append("\n");
                    }
                } else if (!(line.startsWith("<")) && (line.endsWith(">"))) {
                    File_correct.append("<").append(line).append("\n");
                } else {
                    if (!line.equals("")) {
                        File_correct.append(line).append("\n");
                    }
                }
            } else {
                File_correct.append(line).append("\n");
            }
        }

        return File_correct.toString();
    }

    
    
    public String correctTagBalance_str(String file)
    {
        Stack<String> stack = new Stack<>();
        
    String[] lines = Prettifying.Prettify(file).split("\n");
    
    StringBuilder fileCorrect = new StringBuilder();

    for (String line : lines) {
        int startIndex = line.indexOf('<');
        int endIndex = line.indexOf('>');
        if (startIndex != -1 && endIndex != -1) {
            String tag = line.substring(startIndex + 1, endIndex);
            if (!tag.startsWith("/")) {
                
                if(stack.empty()){
                     stack.push(tag);
                }
                else if(!(stack.peek()).equals(tag) ){
                
                stack.push(tag);
                }
                else if((stack.peek()).equals(tag))
                {
                // String popTag = stack.pop();
                //String expectedTag = tag.substring(1);
                     line = line.replace( tag, "/"+tag );
                }
            } else {
               
                String popTag = stack.pop();
                String expectedTag = tag.substring(1);
                if (!popTag.equals(expectedTag)) {
                    line = line.replace("/" + expectedTag, "/" + popTag);
                }
            }
        }
        fileCorrect.append(line).append("\n");
    }
    
        
    while (!stack.empty()) {
        String missingTag = stack.pop();
        //System.out.println(missingTag);
        fileCorrect.append("</").append(missingTag).append(">\n");
    }
        
        return fileCorrect.toString();
    }


    private static String[] split(String file)
    {
        String[] lines;
        file = file.trim().replaceAll("<", "\n<").replaceAll(">", ">\n");
        lines = file.split("\n");
        return lines;
    }

    public boolean CheckTagsBalance_bool()
    {
        boolean hasAtLeastOnePush = false;
        Stack<String> TagsHolder = new Stack<>();
        IncorrectTags.clear();
        IncorrectTagsLine.clear();
        Counter_error = 0;

        for (int i = 0 ; i < Lines.length; i++)
        {
            if (!NoTag(Lines[i]))
            {
                if (isOpeningTag(Lines[i]))
                {
                    TagsHolder.push(Lines[i]);
                    hasAtLeastOnePush = true;
                }
                else if ((isClosingTag(Lines[i])) && !(TagsHolder.isEmpty()))
                {
                    if (compareTop(Lines[i], TagsHolder.peek()))
                    {
                        TagsHolder.pop();
                    }
                    else
                    {
                       Counter_error++;
                        /* Opening tag without closing */
                        if (TagsHolder.contains(SlashCorrect_str(Lines[i])))
                        {
                            i--;
                            IncorrectTags.add(TagsHolder.peek());
                            IncorrectTagsLine.add(i);
                            TagsHolder.pop();
                        }
                       
                        else
                            IncorrectTags.add(Lines[i]);
                            IncorrectTagsLine.add(i);
                        

                    }
                }
                else if (isClosingTag(Lines[i]))
                {
                   Counter_error++;
                    IncorrectTags.add(Lines[i]);
                    IncorrectTagsLine.add(i);
                }
            }
        }

        while(!TagsHolder.isEmpty()){
            Counter_error++;
            IncorrectTags.add(TagsHolder.peek());
            TagsHolder.pop();
        }

        return (Counter_error == 0) && hasAtLeastOnePush;
    }
/*
    public static String SlashCorrect_str(String Tag)
    {
        StringBuilder s = new StringBuilder();

        for (int i = 0; i < Tag.length(); i++)
        {
            if (i == 1)
            {
                if(Tag.charAt(i) != '/')
                {
                    s.append('/').append(Tag.charAt(i));
                }
            }
            else
                s.append(Tag.charAt(i));
        }
        return s.toString();
    }
*/
    
    
    
    public static String SlashCorrect_str(String file)
        {
        Stack<String> stack = new Stack<>();
        
    String[] lines = Prettifying.Prettify(file).split("\n");
    
    StringBuilder fileCorrect = new StringBuilder();

    for (String line : lines) {
        int startIndex = line.indexOf('<');
        int endIndex = line.indexOf('>');
        if (startIndex != -1 && endIndex != -1) {
            String tag = line.substring(startIndex + 1, endIndex);
            if (!tag.startsWith("/")) {
                
                if(stack.empty()){
                     stack.push(tag);
                }
                else if(!(stack.peek()).equals(tag) ){
                
                stack.push(tag);
                }
                else if((stack.peek()).equals(tag))
                {
                // String popTag = stack.pop();
                //String expectedTag = tag.substring(1);
                     line = line.replace( tag, "/"+tag );
                }
            
            }
        }
        fileCorrect.append(line).append("\n");
    }
    
       
        return fileCorrect.toString();
    }

    
    
    
    
     
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public boolean CheckCorrectnessTags_bool()
    {
       Counter_error = 0;
        IncorrectTags.clear();
        IncorrectTagsLine.clear();
        boolean isTagCorrect = true;

        for (int i=0 ; i < Lines.length ; i++) 
        {
            if (NoTag(Lines[i])) {
                if ((Lines[i].startsWith("<")) && !(Lines[i].endsWith(">")))
                {
                   Counter_error++;
                    IncorrectTags.add(Lines[i]);
                    IncorrectTagsLine.add(i);
                    
                    isTagCorrect = false;
                }
                else if (!(Lines[i].startsWith("<")) && (Lines[i].endsWith(">")))
                {
                    Counter_error++;
                    IncorrectTags.add(Lines[i]);
                    IncorrectTagsLine.add(i);
                    isTagCorrect = false;
                }
            }
        }
        return isTagCorrect;
    }

    public boolean isFileConsistent()
    {
        boolean result2 = CheckCorrectnessTags_bool();
        boolean result1 = CheckTagsBalance_bool();
        return result1 && result2;
    }

    public static void main(String args[]) throws IOException {

        String s = "<users>\n" +
                "    <user>\n" +
                "        <id>1</id> \n" +
                "        <name> Ahmed Ali\n </name> "+
                "        <posts>\n" +
                "            <post>\n" +
                "                <body>\n" +
                "                  shamosaa"+
                //"                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\n" +
                "                </body>\n" +
                "                <topics>\n" +
                "                    <topic>\n" +
                "                        economy\n" +
                "                    </topic>\n" +
                "                    <topic>\n" +
                "                        finance\n" +
                "                    </topic>\n" +
                "                </topics>\n" +
                "            </post>\n" +
                "            <post>\n" +
                "                <body>\n" +
                "                  mai"+
               // "                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\n" +
                "                </body>\n" +
                "                <topics>\n" +
                "                    <topic>\n" +
                "                        solar_energy\n" +
                "                    </topic>\n" +
                "                </topics>\n" +
                "            </post>\n" +
                "        </posts>\n" +
                "        <followers>\n" +
                "            <follower>\n" +
                "                <id>2</id>\n" +
                "            </follower>\n" +
                "            <follower>\n" +
                "               <id> 3 </id> \n" +
                "            </follower>\n" +
                "        </followers>\n" +
                "    </user>\n" +               
                "</users>";

        Checkconsistence c1 = new Checkconsistence(Prettifying.Prettify(s));
       // System.out.println(c1.isFileConsistent());
      //  System.out.println(c1.CheckCorrectnessTags_bool());
        //System.out.println("Errors --> " + c1.getErrorCounter());
        //System.out.println(c1.getWrongTags());
         //System.out.println(c1.getWrongTagsLines());
           
}
}

