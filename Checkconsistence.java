
package project;

import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author habiba
 */
public class Checkconsistence {

 private String[] Lines = null;
    public static ArrayList<String> IncorrectTags;

    public int Counter_error = 0;

 
    public Checkconsistence(String file) {

        Lines = file.replaceAll(" ", "")
                .replaceAll("<", "\n<")
                .replaceAll(">", ">\n")
                .split("\n");

        IncorrectTags = new ArrayList<>();
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

    public String correctTag_str()
    {
        StringBuilder File_correct = new StringBuilder();

        for (String line : Lines)
        {
            if (NoTag(line))
            {
                if ((line.startsWith("<")) && !(line.endsWith(">")))
                {
                    File_correct.append(line).append(">").append("\n");
                }
                else if (!(line.startsWith("<")) && (line.endsWith(">")))
                {
                    File_correct.append("<").append(line).append("\n");
                }
                else
                {
                    if (!line.equals(""))
                        File_correct.append(line).append("\n");
                }
            }
            else
                File_correct.append(line).append("\n");
        }

        return File_correct.toString();
    }

    public String correctTagBalance_str(String file)
    {
        String[] Lines = file.split("\n");
        StringBuilder File_correct = new StringBuilder();
        Stack<String> TagsHolder = new Stack<>();

        for (int i = 0; i < Lines.length; i++)
        {
            if (!NoTag(Lines[i]))
            {
                if (isOpeningTag(Lines[i]))
                {
                    TagsHolder.push(Lines[i]);
                    File_correct.append(Lines[i]).append("\n");
                }
                else if ((isClosingTag(Lines[i])) && !(TagsHolder.isEmpty()))
                {
                    if (compareTop(Lines[i], TagsHolder.peek()))
                    {
                        TagsHolder.pop();
                        File_correct.append(Lines[i]).append("\n");
                    }
                    else
                    {
                        if (TagsHolder.contains(SlashCorrect_str(Lines[i])))
                        {
                            i--;
                            File_correct.append(SlashCorrect_str(TagsHolder.peek())).append("\n");
                            TagsHolder.pop();
                        }
                      
                        else
                        {
                            File_correct.append(SlashCorrect_str(Lines[i])).append("\n").append(Lines[i]).append("\n");
                        }
                    }
                }
                else if (isClosingTag(Lines[i]))
                {
                    String lastFile = SlashCorrect_str(Lines[i]) + "\n" + File_correct.toString() + Lines[i];
                    File_correct = new StringBuilder(lastFile);
                }
            }
            else
                File_correct.append(Lines[i]).append("\n");
        }

        return File_correct.toString();
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
                            TagsHolder.pop();
                        }
                       
                        else
                            IncorrectTags.add(Lines[i]);

                    }
                }
                else if (isClosingTag(Lines[i]))
                {
                   Counter_error++;
                    IncorrectTags.add(Lines[i]);
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

    public boolean CheckCorrectnessTags_bool()
    {
       Counter_error = 0;
        IncorrectTags.clear();
        boolean isTagCorrect = true;

        for (String line : Lines)
        {
            if (NoTag(line)) {
                if ((line.startsWith("<")) && !(line.endsWith(">")))
                {
                   Counter_error++;
                    IncorrectTags.add(line);
                    isTagCorrect = false;
                }
                else if (!(line.startsWith("<")) && (line.endsWith(">")))
                {
                    Counter_error++;
                    IncorrectTags.add(line);
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
}
