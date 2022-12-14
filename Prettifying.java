/***************************************/
/******Coded By Maram Ahmed Hussein*****/
/**************************************/

public class Prettifying {
    
  private static int spacing=0;
 //  private static boolean flag1=false;
  
    
public static String Prettify(String xml_file)
{
    

    /*check if the file is empty*/
    if((xml_file ==null) || (xml_file.length()==0))
    { return "";  }//return null
    
    StringBuilder str =new StringBuilder();
    
    /*insert a new line after each tag or data*/
    String[] lines = xml_file.trim().replaceAll(">", ">\n").replaceAll("<", "\n<").split("\n");
    
   
    for(int i=0;i<lines.length;i++)
    {
        
       
        /*check if the line doesn't contain any data*/
        if(lines[i].trim().length()==0 || lines[i]==null)
                        {continue;}
        
        lines[i]=lines[i].trim();
        
       StringBuilder indentation =new StringBuilder();
        /*ignores the initial tag*/
        if (lines[i].startsWith("<?"))
			{
				str.append(lines[i] + "\n");
			} 
        
        /*adjusts the indentation for opening tag*/
   else if(lines[i].startsWith("</"))
   {     spacing--;
            for(int j=0;j<spacing;j++)
        {indentation.append("    ");}
            str.append( indentation + lines[i] + "\n");
        
        }
            /*adjusts the indentation for closing tag*/
   else if(lines[i].startsWith("<"))
        {
            
 
           for(int j=0;j<spacing;j++)
            {indentation .append("    ");}
            str.append( indentation + lines[i] + "\n");
           spacing++;
       
        }    
        
        /*adjusts indentaion for data*/
        
   else {// StringBuilder indentation =new StringBuilder();
       
            for(int j=0;j<spacing;j++)
            {indentation .append("    ");}
            str.append( indentation + lines[i] + "\n"); 
            
        }  
        
    }
    return str.toString();
    
}
  
}

