package parser;

import AST.*;
import scanner.LexicalException;
import scanner.Scanner;
import token.Token;
import token.TokenType;

import java.io.IOException;
import java.util.ArrayList;

public class Parser {
    private final Scanner scanner;

    public Parser(Scanner s){
        this.scanner = s;
    }

    public NodeProgram parse() throws SyntacticException {
        try {
            return parsePrg();
        } catch (LexicalException | IOException e) {
            throw new SyntacticException(e.getMessage(), e);
        }
    }

    private NodeProgram parsePrg() throws LexicalException, SyntacticException, IOException {
        Token t = scanner.peekToken();

        switch (t.getTipo()){
            case TYPE_INT, TYPE_FLOAT, ID, PRINT, EOF -> { // Prg -> DSs $
                ArrayList<NodeDecSt> declStmList = parseDSs();
                match(TokenType.EOF);
                return new NodeProgram(declStmList);
            }
            default -> throw new SyntacticException("TYPE_INT, TYPE_FLOAT, ID, PRINT, EOF", t.getRiga(), t.getTipo());
        }
    }

    private ArrayList<NodeDecSt> parseDSs() throws LexicalException, IOException, SyntacticException {
        Token t = scanner.peekToken();

        switch (t.getTipo()){
            case TYPE_INT, TYPE_FLOAT -> {  // DSs -> DCl DSs
                NodeDecSt dcl = parseDcl();
                ArrayList<NodeDecSt> declStmList = parseDSs();
                declStmList.add(0, dcl);
                return declStmList;
            }

            case ID, PRINT -> {     // DSs -> Stm DSs
                NodeDecSt stm = parseStm();
                ArrayList<NodeDecSt> declStmList = parseDSs();
                declStmList.add(0, stm);
                return declStmList;
            }

            case EOF -> {    // DSs -> ϵ
                return new ArrayList<>();
            }

            default -> throw new SyntacticException("TYPE_INT, TYPE_FLOAT, ID, PRINT, EOF", t.getRiga(), t.getTipo());
        }

    }

    private NodeDecl parseDcl() throws LexicalException, SyntacticException, IOException {
        Token t = scanner.peekToken();

        // Dcl -> Ty id DclP
        switch (t.getTipo()){
            case TYPE_INT, TYPE_FLOAT   -> {
                LangType type = parseTy();
                NodeId id = new NodeId(match(TokenType.ID).getVal());
                NodeExpr init = parseDclP();
                return new NodeDecl(type, id, init);
            }

            default -> throw new SyntacticException("TYPE_INT, TYPE_FLOAT", t.getRiga(), t.getTipo());
        }
    }

    private NodeExpr parseDclP() throws LexicalException, SyntacticException, IOException {
        Token t = scanner.peekToken();

        switch (t.getTipo()){
            case SEMI ->{
                match(TokenType.SEMI); //DclP -> ;
                return null;
            }
            case OP_ASS -> {                    //DclP -> opAss Exp
                Token op = match(TokenType.OP_ASS);
                if(!op.getVal().equals("=")) throw new SyntacticException("=", t.getRiga(), op.getVal());
                NodeExpr expr = parseExp();
                match(TokenType.SEMI);
                return expr;
            }

            default -> throw new SyntacticException("SEMI, OP_ASS", t.getRiga(), t.getTipo());
        }
    }

    private NodeStm parseStm() throws LexicalException, SyntacticException, IOException {
        Token t = scanner.peekToken();
        NodeId id;
        NodeStm stm;

        switch (t.getTipo()){
            case ID -> {        // Stm -> id opAss Exp
                id = new NodeId(match(TokenType.ID).getVal());
                Token op = match(TokenType.OP_ASS);
                NodeExpr expr = parseExp();


                switch (op.getVal()){
                    case "+=" -> expr = new NodeBinOp(new NodeDeref(id), LangOper.PLUS, expr);
                    case "-=" -> expr = new NodeBinOp(new NodeDeref(id), LangOper.MINUS, expr);
                    case "*=" -> expr = new NodeBinOp(new NodeDeref(id), LangOper.MULTIP, expr);
                    case "/=" -> expr = new NodeBinOp(new NodeDeref(id), LangOper.DIVISION, expr);
                }

                stm = new NodeAssign(id, expr);
                match(TokenType.SEMI);
                return stm;
            }

            case PRINT -> {     // Stm -> print id ;
                match(TokenType.PRINT);
                id = new NodeId(match(TokenType.ID).getVal());
                stm = new NodePrint(id);
                match(TokenType.SEMI);
                return stm;
            }

            default -> throw new SyntacticException("PRINT, ID", t.getRiga(), t.getTipo());
        }
    }

    private NodeExpr parseExp() throws LexicalException, SyntacticException, IOException {
        Token t = scanner.peekToken();

        switch (t.getTipo()){
            case ID, FLOAT, INT -> { //Exp -> Tr ExpP
                NodeExpr expr = parseTr();

                // salvo eventuale op prima della chiamata
                LangOper op = null;
                if(scanner.peekToken() != null){
                    if(scanner.peekToken().getTipo() == TokenType.PLUS) op = LangOper.PLUS;
                    if(scanner.peekToken().getTipo() == TokenType.MINUS) op = LangOper.MINUS;
                }

                NodeExpr expr2 = parseExpP();

                if(expr2 != null) expr = new NodeBinOp(expr, op, expr2);

                return expr;
            }
            default -> throw new SyntacticException("ID, FLOAT, INT", t.getRiga(), t.getTipo());
        }
    }

    private NodeExpr parseExpP() throws LexicalException, SyntacticException, IOException {
        Token t = scanner.peekToken();
        NodeExpr expr, expr2;

        switch (t.getTipo()){
            case PLUS -> {  // ExpP -> + Tr ExpP
                match(TokenType.PLUS);
                expr = parseTr();

                // salvo eventuale op prima della chiamata
                LangOper op = null;
                if(scanner.peekToken() != null){
                    if(scanner.peekToken().getTipo() == TokenType.PLUS) op = LangOper.PLUS;
                    if(scanner.peekToken().getTipo() == TokenType.MINUS) op = LangOper.MINUS;
                }

                expr2 = parseExpP();

                if(expr2 != null) expr = new NodeBinOp(expr, op, expr2);

                return expr;
            }

            case MINUS -> { // ExpP -> - Tr ExpP
                match(TokenType.MINUS);
                expr = parseTr();

                // salvo eventuale op prima della chiamata
                LangOper op = null;
                if(scanner.peekToken() != null){
                    if(scanner.peekToken().getTipo() == TokenType.PLUS) op = LangOper.PLUS;
                    if(scanner.peekToken().getTipo() == TokenType.MINUS) op = LangOper.MINUS;
                }

                expr2 = parseExpP();

                if(expr2 != null) expr = new NodeBinOp(expr, op, expr2);

                return expr;
            }

            case SEMI -> {  // ExpP ->
                return null;
            }

            default -> throw new SyntacticException("PLUS, MINUS", t.getRiga(), t.getTipo());
        }
    }

    private NodeExpr parseTr() throws LexicalException, SyntacticException, IOException {
        Token t = scanner.peekToken();

        switch (t.getTipo()){
            case ID, FLOAT, INT -> {    // Tr -> Val TrP
                NodeExpr expr = parseVal();

                // salvo eventuale op prima della chiamata
                LangOper op = null;
                if(scanner.peekToken() != null){
                    if(scanner.peekToken().getTipo() == TokenType.MULTIP) op = LangOper.MULTIP;
                    if(scanner.peekToken().getTipo() == TokenType.DIVISION) op = LangOper.DIVISION;
                }

                NodeExpr expr2 = parseTrP();

                if(expr2 != null) return new NodeBinOp(expr, op, expr2);
                else return expr;
            }

            default -> throw new SyntacticException("ID, FLOAT, INT", t.getRiga(), t.getTipo());
        }
    }

    private NodeExpr parseTrP() throws LexicalException, SyntacticException, IOException {
        Token t = scanner.peekToken();

        switch (t.getTipo()){
            case MULTIP -> {    //TrP -> * Val Trp
                match(TokenType.MULTIP);
                NodeExpr val1 = parseVal();

                // salvo eventuale op prima della chiamata
                LangOper op = null;
                if(scanner.peekToken() != null){
                    if(scanner.peekToken().getTipo() == TokenType.MULTIP) op = LangOper.MULTIP;
                    if(scanner.peekToken().getTipo() == TokenType.DIVISION) op = LangOper.DIVISION;
                }
                NodeExpr val2 = parseTrP();

                if(val2 == null)return val1;
                else return new NodeBinOp(val1, op, val2);
            }

            case DIVISION -> {    //TrP -> / Val Trp
                match(TokenType.DIVISION);
                NodeExpr val1 = parseVal();

                // salvo eventuale op prima della chiamata
                LangOper op = null;
                if(scanner.peekToken() != null){
                    if(scanner.peekToken().getTipo() == TokenType.MULTIP) op = LangOper.MULTIP;
                    if(scanner.peekToken().getTipo() == TokenType.DIVISION) op = LangOper.DIVISION;
                }

                NodeExpr val2 = parseTrP();
                if(val2 == null)return val1;
                else{
                    System.out.println("HEEH");
                    return new NodeBinOp(val1, op, val2);
                }
            }

            case MINUS, PLUS, SEMI ->{  //TrP ->
                return null;
            }

            default -> throw new SyntacticException("MULTIP, DIVISION, PLUS, MINUS, SEMI", t.getRiga(), t.getTipo());
        }
    }


    private LangType parseTy() throws LexicalException, SyntacticException, IOException {
        Token t = scanner.peekToken();
        switch (t.getTipo()){
            case TYPE_FLOAT -> {
                match(TokenType.TYPE_FLOAT);
                return LangType.FLOAT;
            }
            case TYPE_INT ->{
                match(TokenType.TYPE_INT);
                return LangType.INT;
            }

            default -> throw new SyntacticException("TYPE_INT, TYPE_FLOAT", t.getRiga(), t.getTipo());
        }
    }

    private NodeExpr parseVal() throws LexicalException, SyntacticException, IOException {
        Token t = scanner.peekToken();
        switch (t.getTipo()){
            case INT -> {
                return new NodeConst(match(TokenType.INT).getVal(), LangType.INT);
            }
            case FLOAT -> {
                return new NodeConst(match(TokenType.FLOAT).getVal(), LangType.FLOAT);
            }
            case ID -> {
                return new NodeDeref(new NodeId(match(TokenType.ID).getVal()));
            }

            default -> throw new SyntacticException("ID, FLOAT, INT", t.getRiga(), t.getTipo());
        }

    }

    private Token match(TokenType type) throws LexicalException, IOException, SyntacticException {
        Token t = scanner.peekToken();

        if(type.equals(t.getTipo())) return scanner.nextToken();
        else throw new SyntacticException(type, t.getRiga(), t.getTipo());
    }
}
