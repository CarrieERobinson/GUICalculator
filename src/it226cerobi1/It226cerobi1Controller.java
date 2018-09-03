/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it226cerobi1;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author Carrie
 */
public class It226cerobi1Controller implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        String equation = ""; //Will store equation for later parsing
        
        
    }    
    
    
    public static double solveEq(String equation)
    {
        double solution;
        equation = equation.replace("//s", "");
        String operands[] = new String[equation.length()];
        
        for(int i = 0; i < equation.length(); i++)
        {
            //
            if(i == 0 && Character.isAlphabetic(equation.charAt(i)))
            {
                
            }
        }
        
        
        return solution;
    }
}
