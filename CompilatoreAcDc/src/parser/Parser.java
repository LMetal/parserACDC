package parser;

import scanner.LexicalException;
import scanner.Scanner;
import token.Token;
import token.TokenType;

import java.io.IOException;

public class Parser {
    private final Scanner scanner;

    public Parser(Scanner s){
        this.scanner = s;
    }

    public void parse() throws SyntacticException {
        try {
            this.parsePrg();
        } catch (LexicalException | IOException e) {
            throw new SyntacticException(e.getMessage(), e);
        }
    }

    private void parsePrg() throws LexicalException, SyntacticException, IOException {
        Token t = scanner.peekToken();

        switch (t.getTipo()){
            case TYPE_INT, TYPE_FLOAT, ID, PRINT, EOF -> { // Prg -> DSs $
                parseDSs();
                match(TokenType.EOF);
            }
            default -> throw new SyntacticException("TYPE_INT, TYPE_FLOAT, ID, PRINT, EOF", t.getRiga(), t.getTipo());
        }
    }

    private void parseDSs() throws LexicalException, IOException, SyntacticException {
        Token t = scanner.peekToken();

        switch (t.getTipo()){
            case TYPE_INT, TYPE_FLOAT -> {  // DSs -> DCl DSs
                parseDcl();
                parseDSs();
            }

            case ID, PRINT -> {     // DSs -> Stm DSs
                parseStm();
                parseDSs();
            }

            case EOF -> {    // DSs -> ϵ
            }

            default -> throw new SyntacticException("TYPE_INT, TYPE_FLOAT, ID, PRINT, EOF", t.getRiga(), t.getTipo());
        }

    }

    private void parseDcl() throws LexicalException, SyntacticException, IOException {
        Token t = scanner.peekToken();

        // Dcl -> Ty id DclP
        switch (t.getTipo()){
            case TYPE_INT, TYPE_FLOAT   -> {
                parseTy();
                match(TokenType.ID);
                parseDclP();
            }

            default -> throw new SyntacticException("TYPE_INT, TYPE_FLOAT", t.getRiga(), t.getTipo());
        }
    }

    private void parseDclP() throws LexicalException, SyntacticException, IOException {
        Token t = scanner.peekToken();

        switch (t.getTipo()){
            case SEMI -> match(TokenType.SEMI); //DclP -> ;
            case OP_ASS -> {                    //DclP -> opAss Exp
                match(TokenType.OP_ASS);
                parseExp();
                match(TokenType.SEMI);
            }

            default -> throw new SyntacticException("SEMI, OP_ASS", t.getRiga(), t.getTipo());
        }
    }

    private void parseStm() throws LexicalException, SyntacticException, IOException {
        Token t = scanner.peekToken();

        switch (t.getTipo()){
            case ID -> {        // Stm -> id opAss Exp
                match(TokenType.ID);
                match(TokenType.OP_ASS);
                parseExp();
                match(TokenType.SEMI);
            }

            case PRINT -> {     // Stm -> print id ;
                match(TokenType.PRINT);
                match(TokenType.ID);
                match(TokenType.SEMI);
            }

            default -> throw new SyntacticException("PRINT, ID", t.getRiga(), t.getTipo());
        }
    }

    private void parseExp() throws LexicalException, SyntacticException, IOException {
        Token t = scanner.peekToken();

        switch (t.getTipo()){
            case ID, FLOAT, INT -> { //Exp -> Tr ExpP
                parseTr();
                parseExpP();
            }
            default -> throw new SyntacticException("ID, FLOAT, INT", t.getRiga(), t.getTipo());
        }
    }

    private void parseExpP() throws LexicalException, SyntacticException, IOException {
        Token t = scanner.peekToken();

        switch (t.getTipo()){
            case PLUS -> {  // ExpP -> + Tr ExpP
                match(TokenType.PLUS);
                parseTr();
                parseExpP();
            }

            case MINUS -> { // ExpP -> - Tr ExpP
                match(TokenType.MINUS);
                parseTr();
                parseExpP();
            }

            case SEMI -> {  // ExpP ->
            }

            default -> throw new SyntacticException("PLUS, MINUS", t.getRiga(), t.getTipo());
        }
    }

    private void parseTr() throws LexicalException, SyntacticException, IOException {
        Token t = scanner.peekToken();

        switch (t.getTipo()){
            case ID, FLOAT, INT -> {    // Tr -> Val TrP
                parseVal();
                parseTrP();
            }

            default -> throw new SyntacticException("ID, FLOAT, INT", t.getRiga(), t.getTipo());
        }
    }

    private void parseTrP() throws LexicalException, SyntacticException, IOException {
        Token t = scanner.peekToken();

        switch (t.getTipo()){
            case MULTIP -> {    //TrP -> * Val Trp
                match(TokenType.MULTIP);
                parseVal();
                parseTrP();
            }

            case DIVISION -> {    //TrP -> / Val Trp
                match(TokenType.DIVISION);
                parseVal();
                parseTrP();
            }

            case MINUS, PLUS, SEMI ->{  //TrP ->
            }

            default -> throw new SyntacticException("MULTIP, DIVISION, PLUS, MINUS, SEMI", t.getRiga(), t.getTipo());
        }
    }


    private void parseTy() throws LexicalException, SyntacticException, IOException {
        Token t = scanner.peekToken();
        switch (t.getTipo()){
            case TYPE_FLOAT -> match(TokenType.TYPE_FLOAT);
            case TYPE_INT   -> match(TokenType.TYPE_INT);

            default -> throw new SyntacticException("TYPE_INT, TYPE_FLOAT", t.getRiga(), t.getTipo());
        }
    }

    private void parseVal() throws LexicalException, SyntacticException, IOException {
        Token t = scanner.peekToken();
        switch (t.getTipo()){
            case INT -> match(TokenType.INT);
            case FLOAT -> match(TokenType.FLOAT);
            case ID -> match(TokenType.ID);

            default -> throw new SyntacticException("ID, FLOAT, INT", t.getRiga(), t.getTipo());
        }

    }

    private Token match(TokenType type) throws LexicalException, IOException, SyntacticException {
        Token t = scanner.peekToken();

        if(type.equals(t.getTipo())) return scanner.nextToken();
        else throw new SyntacticException(type, t.getRiga(), t.getTipo());
    }
}
