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

import java.util.Stack;
import javafx.fxml.Initializable;

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
    String equation = "";

    public static Stack<Character> operatorStack = new Stack<>();
    public static Stack<Double> operandStack = new Stack<>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        String equation = ""; //Will store equation for later parsing
        
    }    
    
//===========================onButtonClick()====================================
    @FXML
    public void onButtonClick(ActionEvent event)
    {
        if(event.getSource() instanceof Button)
        {
            Button button = (Button)event.getSource();
            //add button text to screen text
            resultBox.setText(resultBox.getText().trim() + button.getText().trim());
            equation = resultBox.getText(); //add to equation
        }
    }
    
//==============================onClearButtonClicked()==========================
    public void onClearButtonClicked(ActionEvent event)
    {
        resultBox.setText("");
        equation = "";
    }

//====================onCalculateButtonClicked()================================
    public void onCalculateButtonClicked(ActionEvent event)
    {
        //solve and print result
        resultBox.setText(Double.toString(solve(equation)));
    }
    

//========================solve()===============================================
    //will interpret and solve an equation in string form; prints and returns the answer
    public static double solve(String equation)
    {
        equation.replaceAll("//s", ""); //remove any spaces
        //iterate through the equation string
        for(int i = 0; i < equation.length(); i++)
        {
            //check if char is a digit, if so, check for how many digits the
            //number has and push them to the stack.
            if(Character.isDigit(equation.charAt(i)))
            {
                int j = i; //new index to search ahead in the string
                String num = ""; //this string will hold the digits in the number to be pushed later
                
                //continue through equatiion until operand or end of string is reached
                while(j < equation.length() && (Character.isDigit(equation.charAt(j))
                        || equation.charAt(j) == '.'))
                {
                    num += equation.charAt(j);//add number to string
                    System.out.println(num);
                    j++; //increment j
                }
                
                i = j - 1; //update i so that it is in the correct index after the loop
                double finalNum = Double.parseDouble(num); //parse string into a double
                operandStack.push(finalNum); //push that number onto the stack
            }else
            {
                //this checks that the first char is not non-numerical
                if(i == 0 || i == equation.length() - 1)
                {
                    System.out.println("Invalid format detected!");
                    return 0;
                }else if(!Character.isDigit(equation.charAt(i-1)))
                {
                    //checks that 2 operands are not put in a row
                    System.out.println("Invalid format detected!");
                    return 0;
                }
                
                //in case of a non-numerical character, check for an operator
                if(equation.charAt(i) == '+' || equation.charAt(i) == '-' || 
                equation.charAt(i) == '*' || equation.charAt(i) == '/')
                {
                    //check if the previous operator needs to be evalutated
                    //(* or / when current is + or -)
                    while(!operatorStack.isEmpty() && 
                            (operatorStack.peek() == '*' || operatorStack.peek() == '/')
                            && (equation.charAt(i) == '-' || equation.charAt(i) == '+'))
                    {
                        process(); //evaluate previous operator so that order of
                        //operations is upheld, will continue until + or - or empty stack
                    }
                    operatorStack.push(equation.charAt(i));
                }else
                {
                    //the character is invalid!
                    System.out.println("#Invalid character detected!  " + equation.charAt(i));
                    
                    return 0;
                }
                
            }
        }
        
        while(!operatorStack.isEmpty())
        {
            process(); //process remaining operations until the stack is empty
        }
        
        System.out.printf("%.2f\n\n", operandStack.peek());
        return operandStack.pop();
    }
    
    //===============================process()======================================
    //will process instructions on top of each stack then push the answer to the
    //operandStack
    public static void process()
    {
        char operator = operatorStack.pop();
        //System.out.println(operator);
        double operand1 = operandStack.pop();
        //System.out.println("Op 1: " + operand1);
        double operand2 = operandStack.pop();
        //System.out.println("Op 2: " + operand2);
        
        //checks operation to be performed on 2 operands then performs it and
        //pushes the answer to the top of the operand stack. This keeps a running
        //total to use as more calculations are made
        switch(operator)
        {
            case '+':
                operandStack.push(operand2 + operand1);
                break;
            case '-':
                operandStack.push(operand2 - operand1);
                break;
            case '*':
                operandStack.push(operand2 * operand1);
                break;
            case '/':
                operandStack.push(operand2 / operand1);
                break;
            default: System.out.println("Good job, you broke it!");
        }
    }
}
