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

    /**
     * Implementa la regola della grammatica:
     *      Prg -> DSs $
     *
     * @return Una arraylists di nodeDecStm delle linee gia' nell'AST
     * @throws LexicalException Se lo scanner ha trovato errori
     * @throws SyntacticException Se il parsing ha trovato errori
     * @throws IOException Se avviene un errore nella lettura da file
     */
    private NodeProgram parsePrg() throws LexicalException, SyntacticException, IOException {
        Token t = scanner.peekToken();

        switch (t.getTipo()){
            case TYPE_INT, TYPE_FLOAT, ID, PRINT, EOF -> {
                ArrayList<NodeDecSt> declStmList = parseDSs();
                match(TokenType.EOF);
                return new NodeProgram(declStmList);
            }
            default -> throw new SyntacticException("TYPE_INT, TYPE_FLOAT, ID, PRINT, EOF", t.getRiga(), t.getTipo());
        }
    }

    /**
     * Implementa le regole della grammatica:
     *      DSs -> DCl DSs
     *      DSs -> Stm DSs
     *      DSs -> ϵ
     *
     * @return Una arraylists di nodeDecStm delle linee gia' nell'AST
     * @throws LexicalException Se lo scanner ha trovato errori
     * @throws SyntacticException Se il parsing ha trovato errori
     * @throws IOException Se avviene un errore nella lettura da file
     */
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

    /**
     * Implementa la regola della grammatica:
     *      Dcl -> Ty id DclP
     *
     * @return Nodo dichiarazione creato
     * @throws LexicalException Se lo scanner ha trovato errori
     * @throws SyntacticException Se il parsing ha trovato errori
     * @throws IOException Se avviene un errore nella lettura da file
     */
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

    /**
     * Implementa le regole della grammatica:
     *      DclP -> ;
     *      DclP -> opAss Exp
     *
     * @return Un nodo espressione se presente
     * @throws LexicalException Se lo scanner ha trovato errori
     * @throws SyntacticException Se il parsing ha trovato errori
     * @throws IOException Se avviene un errore nella lettura da file
     */
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

    /**
     * Implementa le regole della grammatica:
     *      Stm -> id opAss Exp
     *      Stm -> print id ;
     *
     * @return Un nuovo nodo statement
     * @throws LexicalException Se lo scanner ha trovato errori
     * @throws SyntacticException Se il parsing ha trovato errori
     * @throws IOException Se avviene un errore nella lettura da file
     */
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

    /**
     * @return
     * @throws LexicalException Se lo scanner ha trovato errori
     * @throws SyntacticException Se il parsing ha trovato errori
     * @throws IOException Se avviene un errore nella lettura da file
     */
    private NodeExpr parseExp() throws LexicalException, SyntacticException, IOException {
        Token t = scanner.peekToken();

        switch (t.getTipo()){
            case ID, FLOAT, INT -> { //Exp -> Tr ExpP
                NodeExpr tr = parseTr();
                return parseExpP(tr);
            }
            default -> throw new SyntacticException("ID, FLOAT, INT", t.getRiga(), t.getTipo());
        }
    }

    /**
     * @param tr
     * @return
     * @throws LexicalException Se lo scanner ha trovato errori
     * @throws SyntacticException Se il parsing ha trovato errori
     * @throws IOException Se avviene un errore nella lettura da file
     */
    private NodeExpr parseExpP(NodeExpr tr) throws LexicalException, SyntacticException, IOException {
        Token t = scanner.peekToken();

        switch (t.getTipo()){
            case PLUS -> {  // ExpP -> + Tr ExpP
                match(TokenType.PLUS);
                NodeExpr tr2 = parseTr();
                NodeExpr expP = parseExpP(tr2);

                return new NodeBinOp(tr, LangOper.PLUS, expP);
            }

            case MINUS -> { // ExpP -> - Tr ExpP
                match(TokenType.MINUS);
                NodeExpr tr2 = parseTr();
                NodeExpr expP = parseExpP(tr2);

                return new NodeBinOp(tr, LangOper.MINUS, expP);
            }

            case SEMI -> {  // ExpP ->
                return tr;
            }

            default -> throw new SyntacticException("PLUS, MINUS", t.getRiga(), t.getTipo());
        }
    }

    /**
     * @return
     * @throws LexicalException Se lo scanner ha trovato errori
     * @throws SyntacticException Se il parsing ha trovato errori
     * @throws IOException Se avviene un errore nella lettura da file
     */
    private NodeExpr parseTr() throws LexicalException, SyntacticException, IOException {
        Token t = scanner.peekToken();

        switch (t.getTipo()){
            case ID, FLOAT, INT -> {    // Tr -> Val TrP
                NodeExpr val = parseVal();

                return parseTrP(val);
            }

            default -> throw new SyntacticException("ID, FLOAT, INT", t.getRiga(), t.getTipo());
        }
    }

    /**
     * @param val
     * @return
     * @throws LexicalException Se lo scanner ha trovato errori
     * @throws SyntacticException Se il parsing ha trovato errori
     * @throws IOException Se avviene un errore nella lettura da file
     */
    private NodeExpr parseTrP(NodeExpr val) throws LexicalException, SyntacticException, IOException {
        Token t = scanner.peekToken();

        switch (t.getTipo()){
            case MULTIP -> {    //TrP -> * Val Trp
                match(TokenType.MULTIP);
                NodeExpr val2 = parseVal();
                NodeExpr trp = parseTrP(val2);

                return new NodeBinOp(val, LangOper.MULTIP, trp);
            }

            case DIVISION -> {    //TrP -> / Val Trp
                match(TokenType.DIVISION);
                NodeExpr val2 = parseVal();
                NodeExpr trp = parseTrP(val2);

                return new NodeBinOp(val, LangOper.DIVISION, trp);
            }

            case MINUS, PLUS, SEMI ->{  //TrP ->
                return val;
            }

            default -> throw new SyntacticException("MULTIP, DIVISION, PLUS, MINUS, SEMI", t.getRiga(), t.getTipo());
        }
    }


    /**
     * @return
     * @throws LexicalException Se lo scanner ha trovato errori
     * @throws SyntacticException Se il parsing ha trovato errori
     * @throws IOException Se avviene un errore nella lettura da file
     */
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

    /**
     * @return
     * @throws LexicalException Se lo scanner ha trovato errori
     * @throws SyntacticException Se il parsing ha trovato errori
     * @throws IOException Se avviene un errore nella lettura da file
     */
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

    /**
     * Controlla che il tipo del prossimo token sia uguale a type, se e' cosi' lo consuma
     *
     * @param type Tipo expected
     * @return Il next token
     * @throws LexicalException Se lo scanner ha trovato errori
     * @throws SyntacticException Se type non e' uguale al tipo del prossimo token
     * @throws IOException Se avviene un errore nella lettura da file
     */
    private Token match(TokenType type) throws LexicalException, IOException, SyntacticException {
        Token t = scanner.peekToken();

        if(type.equals(t.getTipo())) return scanner.nextToken();
        else throw new SyntacticException(type, t.getRiga(), t.getTipo());
    }
}
