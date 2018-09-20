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
    Boolean answerInResultBox = false;
    //help prevent multiple operators being pressed in a row
    Boolean lastPressOperator = false;
    
    //Stacks to hold my operators and operands
    public static Stack<Character> operatorStack = new Stack<>();
    public static Stack<Double> operandStack = new Stack<>();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
        
    }    
    
//===========================onButtonClick()====================================
    @FXML
    public void onButtonClick(ActionEvent event)
    {
        if(event.getSource() instanceof Button)
        {
            Button button = (Button)event.getSource();
            String text = button.getText();
            if(!Character.isDigit(text.charAt(0)) && equation.length() == 0)
            {
                //do nothing
                //there's probably a better way to do this, but whatever
            }
            else
            {
                //if another operator is pressed in a row, change the operator
                if(lastPressOperator && !Character.isDigit(text.charAt(0)))
                {
                    //grab current equation
                    String updatedString = resultBox.getText();
                    //create a substring cutting out previous operator
                    updatedString = updatedString.substring(0, updatedString.length() - 1);
                    //add new operator
                    updatedString+=text.charAt(0);
                    //update resultBox and equation
                    resultBox.setText(updatedString);
                    equation = updatedString;
                }
                else
                {
                    if(answerInResultBox && Character.isDigit(text.charAt(0)))
                    {
                        //clear text and equation first
                        resultBox.setText("");
                        equation = "";
                    }
                    //add button text to screen text
                    resultBox.setText(resultBox.getText() + text.trim());
                    equation = resultBox.getText(); //add to equation

                    if(!Character.isDigit(text.charAt(0)))
                    {
                        lastPressOperator = true; //last press was operator
                    }else
                        lastPressOperator = false; //last press was not operator
                    
                    answerInResultBox = false; //update this flag
                }
                
                
            }
            
        }
    }
    
//==============================onClearButtonClicked()==========================
    //Clears resultBox and equation
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
        //flag to say that the resultBox is holding a previous answer
        answerInResultBox = true;
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


//Improvements I could make:
//3. M0odify so that an error shows in the window when invalid input is recieved
//  (May not need if operators cannot be added multiple times in a row or at
//  the beginning of an equation)
//4. Add more operations and improve layout
//5. Add icons for taskbar and title area
//6. Can't handle negative numbers