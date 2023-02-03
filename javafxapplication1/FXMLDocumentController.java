/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxapplication1;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.scene.image.*;
/**
 * FXML Controller class
 *
 * @author habiba
 */
public class FXMLDocumentController implements Initializable {
   
 public static String  xml, xmlOut;
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
    @FXML
    private Button btnviz;
    @FXML
    private Button networkbtn;
    
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
  
    }    

    @FXML
    private void savefile(ActionEvent event) {
        
        xmlOut = TextArea2.getText();
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
         Checkconsistence c1 = new Checkconsistence(Prettifying.Prettify(xml));
         
         if(!c1.isFileConsistent()){
         JOptionPane.showMessageDialog(null, "Errors have been corrected ","correct error",JOptionPane.INFORMATION_MESSAGE);
         String msg;
         String result;
         msg=c1.SlashCorrect_str(Prettifying.Prettify(xml));
         result=c1.correctTag_str(Prettifying.Prettify(msg));
         result=c1.correctTagBalance_str(Prettifying.Prettify(result));
        TextArea2.setText( result);
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
            if (checker.isFileConsistent()) {
            
             JOptionPane.showMessageDialog(null, "XML file is consistent","Consistency check",JOptionPane.INFORMATION_MESSAGE);
            TextArea2.clear();
        } else {
            StringBuilder msg = new StringBuilder();
            JOptionPane.showMessageDialog(null,"XML file is NOT consistent","Consistency check",JOptionPane.INFORMATION_MESSAGE);
            msg.append("Errors count = ").append(checker.getErrorCounter()).append("\n").append("Error/s in the following tag/s:\n");

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
    private void onMakeGraph(ActionEvent event) throws IOException {
        xml = TextArea1.getText();
        if (checkIfEmpty(xml))
            return;

       Checkconsistence checker = new Checkconsistence(xml);
        if (checker.CheckTagsBalance_bool()&& !xml.isEmpty()) {
            synchronized (graph_representation.class) {
                graph_representation.draw(xml);
                graphReady = true;
            }
        } else {
   
             JOptionPane.showMessageDialog(null,"The provided XML has to be consistent to be converted to visualized","Consistency error",JOptionPane.INFORMATION_MESSAGE);
        }
    
    }

    @FXML
    private void visualize(ActionEvent event) {
        
         if (!graphReady){

           JOptionPane.showMessageDialog(null,"Please make the graph first","Graph Missing",JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Image image;
        try {
            image = new Image(new FileInputStream("graph.dot.png"));
            ImageView iv = new ImageView(image);
            iv.setPreserveRatio(true);
            Group root = new Group(iv);
            Stage s = new Stage();
            s.setScene(new Scene(root, 600, 500));
            s.setTitle("Graphical Representation");
            s.showAndWait();
        } catch (FileNotFoundException ee) {
            ee.printStackTrace();
        }
    }

    @FXML
    private void networkanalysis(ActionEvent event) throws IOException {
        
      if (graphReady){
        Parent root = FXMLLoader.load(getClass().getResource("graphFXML.fxml"));
        Stage stage =new Stage();
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        
        stage.setTitle("Network analysis");
        stage.show(); 
      }else{
      
           JOptionPane.showMessageDialog(null,"Please make the graph first","Graph Missing",JOptionPane.INFORMATION_MESSAGE);
      
      }
        
    }



    }

    

        
    

