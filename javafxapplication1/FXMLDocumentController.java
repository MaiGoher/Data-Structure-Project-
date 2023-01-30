
package javafxapplication1;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import project.CustomInput;
import project.CustomOutput;
import project.Minifying;
import project.Prettifying;
import project.XmlToJson;
import project.graph_representation;
import project.Checkconsistence;
import project.CompressionHuffman;

/**
 * FXML Controller class
 *
 * @author habib
 */
public class FXMLDocumentController implements Initializable {
 String  xml, xmlOut;
 File input;
  File output;
  boolean graphReady;
   FileChooser fileChooser;

    @FXML
    private Button btnsave;
    @FXML
    private TextArea TextArea1;
    @FXML
    private Button SelectFilebtn;
    @FXML
    private Button btnMinify;
    @FXML
    private TextArea TextArea2;
    @FXML
    private Button btncompress;
    @FXML
    private Button btndecompress;
    @FXML
    private AnchorPane anchorpane;
    @FXML
    private Button btncheckconsis;
    @FXML
    private Button btnprettify;
    @FXML
    private Button btnxml2json;
    @FXML
    private Button btnonMakeGraph;
    @FXML
    private Button btncorrecterror;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         fileChooser = new FileChooser();
          FileChooser.ExtensionFilter Filter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
       
          FileChooser.ExtensionFilter Filter2 = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
         fileChooser.getExtensionFilters().add(Filter);
          fileChooser.getExtensionFilters().add(Filter2);
        // TextArea2.setWrapText(true);
        
       
    }    

    @FXML
    private void savefile(ActionEvent event) {
        
        xmlOut = TextArea2.getText();
      //   FileChooser fileChooser = new FileChooser();
        // FileChooser.ExtensionFilter extFilter2 = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
      //  FileChooser.ExtensionFilter Filter2 = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        // fileChooser.getExtensionFilters().add(Filter2);
        output = fileChooser.showSaveDialog(new Stage());

        if (output != null) {
            PrintStream orgOutStream = System.out;
            try {
                System.setOut(new PrintStream(output));
            } catch (FileNotFoundException ee) {
                ee.printStackTrace();
            }
           
           CustomOutput.write(xmlOut);

            CustomOutput.close();
            System.setOut(orgOutStream);
        } else {
            JOptionPane.showMessageDialog(null, "You didn't select a path to save the file","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
      private static boolean checkIfEmpty(String xml) {
        if (xml.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please Select a non empty XML file","No XML",JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        return false;
    }

    @FXML
    private void FileSelector(ActionEvent event) {
        // FileChooser fileChooser = new FileChooser();
      //  FileChooser.ExtensionFilter Filter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
      //  fileChooser.getExtensionFilters().add(Filter);
        input = fileChooser.showOpenDialog(new Stage());
        if (input != null ) {
            InputStream orgInStream = System.in;
            try {
                System.setIn(new FileInputStream(input));
            } catch (FileNotFoundException ee) {
                ee.printStackTrace();
            }
            String xml = CustomInput.readString();
            TextArea1.setText(xml);
            CustomInput.close();
            System.setIn(orgInStream);
           
        } else {  
         JOptionPane.showMessageDialog(null, "You didn't select a file","Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    private void Minify(ActionEvent event) {
            xml = TextArea1.getText();
        if (checkIfEmpty(xml))
            return;

        xmlOut = Minifying.minify(xml);
        TextArea2.setText(xmlOut);
    }

    @FXML
      private void Compress(ActionEvent event) {
     xml = TextArea1.getText();
        if (checkIfEmpty(xml))
            return;

      // JOptionPane.showMessageDialog(null, "compression status","compression",JOptionPane.INFORMATION_MESSAGE);

        output = fileChooser.showSaveDialog(new Stage());

        if (output != null) {
           CompressionHuffman.compress(xml, output);
            JOptionPane.showMessageDialog(null, "compression completed","compression",JOptionPane.INFORMATION_MESSAGE);

            InputStream orgInStream = System.in;

            try {
                System.setIn(new FileInputStream(output));
            } catch (FileNotFoundException ee) {
                ee.printStackTrace();
            }
            xmlOut = CustomInput.readString();

            CustomInput.close();
            System.setIn(orgInStream);

            TextArea2.setText(xmlOut);
        } else {
           JOptionPane.showMessageDialog(null, "You didn't select a path to save the file","Error",JOptionPane.ERROR_MESSAGE);
        }
    
    
    
    
    
        }
    
    
 
    
    @FXML
    private void Decompress(ActionEvent event) {
     File originalFile, newFile;

        originalFile = fileChooser.showOpenDialog(new Stage());
        newFile = fileChooser.showSaveDialog(new Stage());
            if (originalFile != null && newFile != null) {
            CompressionHuffman.decompress(originalFile,newFile);
            InputStream orgInStream = System.in;

            try {
                System.setIn(new FileInputStream(originalFile));
            } catch (FileNotFoundException ee) {
                ee.printStackTrace();
            }

            TextArea1.setText(CustomInput.readString());
            CustomInput.close();
            try {
                System.setIn(new FileInputStream(newFile));
            } catch (FileNotFoundException ee) {
                ee.printStackTrace();
            }

            xml = CustomInput.readString().replaceAll("\r", "");
            CustomInput.close();
            System.setIn(orgInStream);
            TextArea2.setText(xml);
        } else {
     JOptionPane.showMessageDialog(null, "You didn't provide a path/s to save/load the files","error",JOptionPane.ERROR_MESSAGE);
      
        }
    }
    
        @FXML
    private void correcterror(ActionEvent event) {        
       xml = TextArea1.getText();
        if (checkIfEmpty(xml))
            return;
       
       Checkconsistence corrector=new Checkconsistence(xml);
         
         
         if(!corrector.isFileConsistent()){
         JOptionPane.showMessageDialog(null, "Errors have been corrected ","correct error",JOptionPane.INFORMATION_MESSAGE);
         String msg;
         msg=corrector.correctTag_str();
         msg=corrector.correctTagBalance_str(msg);
         TextArea2.setText( Prettifying.Prettify(msg));
         }else{
         JOptionPane.showMessageDialog(null, "Xml file has no errors to be corrected! ","correct error",JOptionPane.INFORMATION_MESSAGE);
         TextArea2.setText( Prettifying.Prettify(xml));
         }
 
    }
     @FXML
    private void checkconsis(ActionEvent event) {
         xml = TextArea1.getText();
        if (checkIfEmpty(xml))
            return;
     Checkconsistence checker=new Checkconsistence(xml);   
    
      if (!checker.CheckCorrectnessTags_bool()){
         StringBuilder msg = new StringBuilder();
           JOptionPane.showMessageDialog(null,"XML file is NOT consistent","Consistency check",JOptionPane.INFORMATION_MESSAGE);
            msg.append("Errors count = ").append(checker.Counter_error).append("\n").append("Correct the following tag/s, then check again:\n");

            for (String s : checker.IncorrectTags) {
                msg.append(s).append("\n");
            }
            TextArea2.setText(msg.toString());
            return;
      }
            if (checker.CheckTagsBalance_bool()) {
            
             JOptionPane.showMessageDialog(null, "XML file is consistent","Consistency check",JOptionPane.INFORMATION_MESSAGE);
            TextArea2.clear();
        } else {
            StringBuilder msg = new StringBuilder();
            JOptionPane.showMessageDialog(null,"XML file is NOT consistent","Consistency check",JOptionPane.INFORMATION_MESSAGE);
            msg.append("Errors count = ").append(checker.Counter_error).append("\n").append("Error/s in the following tag/s:\n");

            for (String s : checker.IncorrectTags) {
                msg.append(s).append("\n");
            }
           
            TextArea2.setText(msg.toString());
        }
    
    
    
    }

    @FXML
    private void prettify(ActionEvent event) {
    xml = TextArea1.getText();
        if (checkIfEmpty(xml))
            return;
    xmlOut =Prettifying.Prettify(xml);
    TextArea2.setText(xmlOut);
    }

    @FXML
    private void Xml2Json(ActionEvent event) {
     xml = TextArea1.getText();
        if (checkIfEmpty(xml))
            return;
    xmlOut = XmlToJson.XML_To_JSON(xml);
    TextArea2.setText(xmlOut);
    
    }
    @FXML
    private void onMakeGraph(ActionEvent event) {

    }



    }

    

        
    

