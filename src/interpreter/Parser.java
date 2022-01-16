/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;
import java.util.ArrayList;
import java.util.Iterator;
/**
 *
 * @author mehak
 */
class Node{
    Float value;
    Node left;
    Node right;   
    public Float getValue(){
        return value;
    }
    public Node getLeft() {
        return left;
    }
    public Node getRight() {
        return right;
    }
    public Node getNode(){
        return null;
    }
}
class NumberNode extends Node{
    Float value;
    NumberNode(Float value) {
        this.value = value;
    }   
    @Override
    public Float getValue(){
        return value;
    }
    public String toString(){
        return value.toString();
    }
}
class AddNode extends Node{
    Node left;
    Node right;
    AddNode(Node left, Node right) {
        this.left = left;
        this.right = right;
    }   
    @Override
    public String toString(){
       return "(" + left + "+" + right + ")";
    }
    @Override
    public Node getRight() {
        return right; 
    }
    @Override
    public Node getLeft() {
        return left; 
    } 
}
class SubNode extends Node{
    Node left;
    Node right;
    SubNode(Node left, Node right) {
        this.left  = left;
        this.right  = right;
    }
    @Override
    public String toString(){
       return "(" + left + "-" + right + ")";
    }
    @Override
    public Node getRight() {
        return right; 
    }
    @Override
    public Node getLeft() {
        return left; 
    } 
}
class MulNode extends Node{
    Node left;
    Node right;
    public MulNode(Node left, Node right) {
        this.left = left;
        this.right = right;
    }  
    @Override
    public String toString(){
       return "(" + left + "*" + right + ")";
    }
    @Override
    public Node getRight() {
        return right; 
    }
    @Override
    public Node getLeft() {
        return left; 
    }    
}
class DivNode extends Node{
    Node left;
    Node right;
    public DivNode(Node left, Node right) {
        this.left = left;
        this.right = right;
    }  
    @Override
    public String toString(){
       return "(" + left + "/" + right + ")";
    }
    @Override
    public Node getRight() {
        return right; //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public Node getLeft() {
        return left; //To change body of generated methods, choose Tools | Templates.
    }  
}
class UnaryAddNode extends Node{
    Node node;
    public UnaryAddNode(Node node) {
        this.node = node;
    }
    @Override
    public Node getNode() {
        return node;
    }
    @Override
    public String toString() {
        return "(+" + node + ")";
    }
}
class UnaryMinusNode extends Node{
    Node node;
    public UnaryMinusNode(Node node) {
        this.node = node;
    }
    @Override
    public Node getNode() {
        return node;
    }
    @Override
    public String toString() {
        return "(-" + node + ")";
    }
}
public class Parser {
    ArrayList<Token> tokens;
    Token cur_token;
    Iterator<Token> it;
    
    public Parser(ArrayList<Token> tokens){
        this.tokens = tokens;
        it = tokens.iterator();
        this.advance();
    }
    
    private void advance(){
        if(it.hasNext()){
            cur_token = it.next();
        }else 
            cur_token = null;
    }
    
    public Node parse(){
        if(cur_token == null) return null;
        Node result = null;
        try{
            result = expr();  
        }catch(Exception e){
            e.printStackTrace();
        }
        if(cur_token != null){
            try{
                throw new Exception("invalid syntax");   
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return result;    
    }
    
    private Node expr() throws Exception {
        Node result = term();
        while(cur_token != null && (cur_token.getType() == Token.TokenType.ADD || cur_token.getType() == Token.TokenType.SUB)){
            if(cur_token.getType() == Token.TokenType.ADD){
                advance();
                result = new AddNode(result, term() );
            }else if(cur_token.getType() == Token.TokenType.SUB){
                advance();
                result = new SubNode(result, term());
            }  
        }
        return result;
    }
    
    private Node term() throws Exception{
        Node result = factor();
        while(cur_token != null && (cur_token.getType() == Token.TokenType.MUL || cur_token.getType() == Token.TokenType.DIV)){
            if(cur_token.getType() == Token.TokenType.MUL){
                advance();
                result = new MulNode(result, factor() );
            }else if(cur_token.getType() == Token.TokenType.DIV){
                advance();
                result = new DivNode(result, factor());
            }  
        }
        return result;
    }
    private Node factor() throws Exception{
        Token token  = cur_token;
        if(token.getType() == Token.TokenType.NUMBER){
            advance();
            return new NumberNode((Float)(token.getValue()));
        }else if(token.getType() == Token.TokenType.ADD){
            advance();
            return new UnaryAddNode(factor());
        }else if(token.getType() == Token.TokenType.SUB){
            advance();
            return new UnaryMinusNode(factor());
        }else if(token.getType() == Token.TokenType.lParen){
            advance();
            Node result = expr();
            if(cur_token.getType() != Token.TokenType.rPAREN){
                throw new Exception("invalid syntax");
            }
            advance();
            return result;
        }
        else{
            throw new Exception("invalid syntax");
        }    
    }
}
