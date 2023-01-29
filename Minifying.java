package project;

/**
 *
 * @author habib
 */
public class Minifying {

    /**
     * @param args the command line arguments
     */
 	public static String minify(String xml) {
            //check that xml isn't null
		if (xml == null || xml.trim().length() == 0) return "";
		StringBuilder x = new StringBuilder();
                // Split the XML into array of rows
               String[] rows = xml.trim().replaceAll(">\\s+<", "><").split("\n");
		//String[] rows = xml.trim().replaceAll(">", ">\n").replaceAll("<", "\n<").split("\n"); 
		
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
