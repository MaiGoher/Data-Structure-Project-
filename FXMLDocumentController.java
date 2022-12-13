/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
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
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import project.CustomInput;
import project.CustomOutput;
import project.Minifying;

/**
 * FXML Controller class
 *
 * @author habib
 */
public class FXMLDocumentController implements Initializable {
 String  xml, xmlOut;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void savefile(ActionEvent event) {
        
        xml = TextArea2.getText();
         FileChooser fileChooser = new FileChooser();
        // FileChooser.ExtensionFilter extFilter2 = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        FileChooser.ExtensionFilter Filter2 = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
         fileChooser.getExtensionFilters().add(Filter2);
         File output = fileChooser.showSaveDialog(new Stage());

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
         FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter Filter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(Filter);
        File input = fileChooser.showOpenDialog(new Stage());
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
    
}
