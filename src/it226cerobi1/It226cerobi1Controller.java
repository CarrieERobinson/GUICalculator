/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it226cerobi1;

import java.net.URL;
import java.util.ResourceBundle;
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
    public static Stack<Character> operatorStack = new Stack<>();
    public static Stack<Double> operandStack = new Stack<>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        String equation = ""; //Will store equation for later parsing
        
        
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
                while(j < equation.length() && Character.isDigit(equation.charAt(j)))
                {
                    num += equation.charAt(j);//add number to string
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
                    System.out.println("Invalid character detected!");
                    return 0;
                }
                
            }
        }
        
        while(!operatorStack.isEmpty())
        {
            process(); //process remaining operations until the stack is empty
        }
        
        System.out.println(operandStack.peek());
        return operandStack.pop();
    }
    
    public static void process()
    {
        
    }
}