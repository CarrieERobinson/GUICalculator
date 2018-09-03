/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it226cerobi1;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Carrie
 */
public class It226cerobi1Controller implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    TextField resultBox;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
    }    
    
    @FXML
    public void onButtonClick(ActionEvent event)
    {
        if(event.getSource() instanceof Button)
        {
            Button button = (Button)event.getSource();
            //add button text to screen text
            resultBox.setText(resultBox.getText().trim() + button.getText().trim());
        }
    }
    
}
