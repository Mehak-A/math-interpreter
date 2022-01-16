/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;

/**
 *
 * @author mehak
 */

public class Token {
    private final TokenType type;
    private final Object value;

    public TokenType getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }
    
    enum TokenType{
        ADD, SUB, MUL, DIV, lParen, rPAREN, NUMBER
    }
    
    public Token(TokenType type, Object value){
        this.type = type;
        this.value = value;
    }
    
    public Token(TokenType type){
        this.type = type;
        this.value = null;
    }
    
    @Override
    public String toString(){
        if(value == null)
            return String.valueOf(type);
        else
            return type + ":" + value;
    }
}
