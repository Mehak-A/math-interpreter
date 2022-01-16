/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;

class Number{
    Float value;
    public Number(Float value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return value+"";
    }   
}
/**
 *
 * @author mehak
 */
public class Interpreter {
    
    public Number visit(Node node) throws Exception{
        String node_name = node.getClass().getCanonicalName();
        node_name = "visit_" + node_name.substring(node_name.lastIndexOf('.') + 1);
       // System.out.println(node_name);
        Method method = this.getClass().getDeclaredMethod(node_name, Node.class);
        //System.out.println(method);
       // return null;
        return (Number) method.invoke(this, node);
    }
    public Number visit_NumberNode(Node node){
        return new Number(node.getValue());
    }
    public Number visit_AddNode(Node node) throws Exception{
        return new Number(visit(node.getLeft()).value + visit(node.getRight()).value);
    }
    public Number visit_SubNode(Node node) throws Exception{
        return new Number(visit(node.getLeft()).value - visit(node.getRight()).value);
    }
    public Number visit_MulNode(Node node) throws Exception{
        return new Number(visit(node.getLeft()).value * visit(node.getRight()).value);
    }
    public Number visit_DivNode(Node node) throws Exception{
        try{
          return new Number(visit(node.getLeft()).value / visit(node.getRight()).value);  
        }catch(Exception e){
            throw new Exception("division by zero");
        }
    }
    public Number visit_UnaryAddNode(Node node) throws Exception{
        return visit(node.getNode());
    }
    public Number visit_UnaryMinusNode(Node node) throws Exception{
        return new Number(-visit(node.getNode()).value);
    }
    
    
    public static void main(String[] args) throws Exception{
        // TODO code application logic here
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String s = "";
        Lexer lexer = new Lexer();
        while(true){
            System.out.print("enter > ");
            try{
                s = br.readLine();
                //s += '\n';
            }catch(IOException e){
                System.out.print(e.getMessage());
            }
            //System.out.print(s);
            if(s.equals("exit") == true){
                System.exit(0);
            }            
            ArrayList<Token> tokens = lexer.get_tokens(s);    
           // System.out.println(tokens);
            Parser parser = new Parser(tokens);
            Node node = parser.parse();
          //  System.out.println(node);
            Interpreter interpreter = new Interpreter();
            Number number = interpreter.visit(node);
            System.out.println(number);
            
            
        }
    }
    
}
