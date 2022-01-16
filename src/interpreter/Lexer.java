/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;
import java.util.ArrayList;
import java.util.List;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
/**
 *
 * @author mehak
 */
class IllegalCharError extends Exception{
    private String error;          
    public IllegalCharError(String s){
        this.error = s;
    }
    public String getError(){
        return error;
    }
}
public class Lexer {
    String line = null;
    Character cur_char = null;
    CharacterIterator it = null;  
    private void initiate(String s){
        line = s;
        it = new StringCharacterIterator(s);// cur_char = it.current();
        advance();
    } 
    private void advance(){
        if(it.current() != CharacterIterator.DONE){
            cur_char = it.current();
            it.next();
        }else
            cur_char = null;
    }    
    ArrayList<Token> get_tokens(String s) {
       // System.out.println(s + s.length());
        this.initiate(s);
        ArrayList<Token> tokens = new ArrayList<>();     
        while(cur_char != null){
                    if (cur_char == ' ' || cur_char == '\t' || cur_char == '\n'){
                        advance();
                    }else if(cur_char == '.' || Character.isDigit(cur_char) == true){
                        Token number = generate_number();
                        tokens.add(number);
                    }else if(cur_char == '+'){
                        advance();
                        tokens.add(new Token(Token.TokenType.ADD));
                    }else if(cur_char == '-'){
                        advance();
                        tokens.add(new Token(Token.TokenType.SUB));
                    }else if(cur_char == '*'){
                        advance();
                        tokens.add(new Token(Token.TokenType.MUL));
                    }else if(cur_char == '/'){
                        advance();
                        tokens.add(new Token(Token.TokenType.DIV));
                    }else if(cur_char == '('){
                        advance();
                        tokens.add(new Token(Token.TokenType.lParen));
                    }else if(cur_char == ')'){
                        advance();
                        tokens.add(new Token(Token.TokenType.rPAREN));
                    }else{
                        try{
                        throw new IllegalCharError( cur_char + " this character is not allowed");
                        }catch(IllegalCharError e){
                            System.out.println(e.getError());
                        }
                    }
        }
        return tokens;
    }
    private Token generate_number(){
            StringBuffer numstr = new StringBuffer(String.valueOf(cur_char));
            int deci_count = 0;
            advance();
            while(cur_char != null && (cur_char == '.' ||  Character.isDigit(cur_char) == true)){
                if(cur_char == '.'){
                    deci_count++;
                }
                if(deci_count > 1) break;
                numstr.append(cur_char);
                advance();
            }
            return new Token(Token.TokenType.NUMBER, new Float(numstr.toString()));
        }
}