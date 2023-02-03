/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxapplication1;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import project.networkanalysis;



/**
 * FXML Controller class
 *
 * @author habib
 */
public class GraphFXMLController implements Initializable {
     String text="";
     networkanalysis var=new networkanalysis(FXMLDocumentController.xml);
    
    @FXML
    private Button mostinfluncerbtn;
    @FXML
    private Button mostactivebtn;
    @FXML
    private Button mutualbtn;
    @FXML
    private TextField firstidmutualbtn;
    @FXML
    private TextField secondidmutualbtn;
    @FXML
    private TextArea textbox3;
    @FXML
    private Button suggestionbtn;
    @FXML
    private TextField suggesttext;
    @FXML
    private Button postbtn;
    @FXML
    private TextField posttext;
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       
         var.getFollowersList();
    }    

    @FXML
    private void mostinfluncer(ActionEvent event) {
     text="";
        text+="- the most influncer user it's ID is :";
       text+=var.getMostInfluencerUser();
        textbox3.setText(text);
    }

    @FXML
    private void mostactive(ActionEvent event) {
      text="";
      
      text+="- the most active user it's ID is :";
       text+=var.getMostActiveUser();
       textbox3.setText(text);}
      
    

    @FXML
    private void mutual(ActionEvent event) {
        
        text="";
       String first=firstidmutualbtn.getText();
       String second=secondidmutualbtn.getText();
       
        if(!(first.isEmpty() || second.isEmpty() )){
            if(first.equals(second)){
             JOptionPane.showMessageDialog(null, "Please enter two different Users ID","Error",JOptionPane.ERROR_MESSAGE);
            }else{
        
        text+="the mutual followers between ID "+first+" & "+ second+" is : " ;
       if(!(var.getMutualFollowers(first, second)).isEmpty()){
      text+= var.getMutualFollowers(first, second);
       }
       else{
           text+="no mutual friends";
       }
      

         textbox3.setText(text);}}
        else{
         JOptionPane.showMessageDialog(null, "Please enter Users ID","Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    private void firstidmutual(ActionEvent event) {
    }

    @FXML
    private void secondidmutual(ActionEvent event) {
    }

    @FXML
    private void suggestion(ActionEvent event) {
        text="";
        String t =suggesttext.getText();
        if(!t.isEmpty()){
            if(!var.getSuggestList(t).isEmpty()){
        text+="suggestion list of ID "+t+" is ";

        text+=var.getSuggestList(t);
        
      textbox3.setText(text);}
            else
            {
            text+="No suggestion list";
            textbox3.setText(text);
            }
        
        }
        
        else{
        JOptionPane.showMessageDialog(null, "Please enter User ID","Error",JOptionPane.ERROR_MESSAGE);
        }
            
        
        
    
    }
    @FXML
    private void suggesttextarea(ActionEvent event) {
        
    }

    @FXML
    private void postsearch(ActionEvent event) {
        text="";
        String p =posttext.getText();
        System.out.println(p);
        if(! p.isEmpty()){
           if(!"".equals(var.PostSearch(p))){
         text+=var.PostSearch(p);
         textbox3.setText(text);
    }else
           {
             text+="post not found";
         textbox3.setText(text);
            
            }
        }else{
         JOptionPane.showMessageDialog(null, "Please enter the word you want to search on it","Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    private void postTextarea(ActionEvent event) {
    }
    
    
   
}
