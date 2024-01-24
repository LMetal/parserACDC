package visitor;

import AST.*;
import symbolTable.Registri;
import symbolTable.SymbolTable;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Oggetto per la generazione di codice dc
 * Lavora con il pattern visitor
 */
public class CodeGeneratorVisitor implements IVisitor{
    /**
     * Stringa codice dc
     */
    private String codiceDc;
    /**
     * Eventuale log di errore
     */
    private String log;
    /**
     * Lista delle linee di codice dc generate
     */
    private final ArrayList<String> linesCode;

    /**
     * Costruttore dell'oggetto: crea linesCode, inizializza codiceDc, inizializza Registri
     */
    public CodeGeneratorVisitor(){
        linesCode = new ArrayList<>();
        codiceDc = "";
        Registri.init();
    }

    /**
     * @return un iterator dell'ArrayList di linesCode
     */
    public Iterator<String> getDcCode(){
        return linesCode.iterator();
    }

    /**
     * Cicla su tutti i nodi decStm contenuti in node
     * Salva i risultati dell'accept di ogni nodo ina linesCode
     *
     * @param node nodo root dell'AST
     */
    @Override
    public void visit(NodeProgram node) {
        for(NodeDecSt line: node.getDecStm()){
            if(log != null) return;
            line.accept(this);
            linesCode.add(codiceDc);
            codiceDc = "";
        }
    }

    /**
     * Ricava il registro associato all' id in node, lo assegna a codiceDc
     *
     * @param node nodo dell' AST
     *
     */
    @Override
    public void visit(NodeId node) {
        char reg = SymbolTable.lookup(node.getName()).getRegister();
        codiceDc = String.valueOf(reg);
    }


    /**
     * Definisce il binding tra nome variabile in ac e registro in dc
     * Se presente inizializzazione, definisce in codice in dc in codiceDc
     *
     * @param node node declaration nell' AST
     */
    @Override
    public void visit(NodeDecl node) {
        var att = SymbolTable.lookup(node.getNodeId().getName());
        Character newReg = Registri.newRegister();

        if(newReg == null){
            log = "Registri non sufficienti";
            return;
        }
        att.setRegister(newReg);

        if(node.getInit() != null){
            node.getInit().accept(this);
            String init = codiceDc;

            node.getNodeId().accept(this);
            String reg = codiceDc;

            codiceDc = init + " s" + reg;
            if(codiceDc.contains(" k ")) codiceDc = codiceDc.concat(" 0 k");
        }
    }

    /**
     * Ottiene il codiceDc per la sinistra e destra dell'operazione
     * Assegna il codice a codiceDc
     *
     * @param node nodo presente nell'AST
     */
    @Override
    public void visit(NodeBinOp node) {
        node.getLeft().accept(this);
        String leftCodice = codiceDc;
        node.getRight().accept(this);
        String rightCodice = codiceDc;


        //if()

        codiceDc = leftCodice + " " + rightCodice + " " + node.getOp();

    }

    /**
     * Aumenta l'accuratezza di dc
     * Assegna a codiceDc l'aumento dell'accuratezza
     *
     * @param nodeConvert nodo presente nell'AST
     */
    @Override
    public void visit(NodeConvert nodeConvert) {
        nodeConvert.getExpr().accept(this);
        codiceDc = "5 k " + codiceDc;
    }

    /**
     * Ottiene il valore del numero
     *
     * @param nodeConst Nodo nell'AST
     */
    @Override
    public void visit(NodeConst nodeConst) {
        codiceDc = nodeConst.getValue();
    }

    /**
     * Genera il codice Dc per caricare in cima allo stack il valore del registro associato all'id contenuto in nodeDeref
     *
     * @param nodeDeref Nodo contenente un id
     */
    @Override
    public void visit(NodeDeref nodeDeref) {
        nodeDeref.getId().accept(this);
        codiceDc = "l"+codiceDc;
    }

    /**
     * Genera il codice Dc per la stampa del valore del registro associato all'id contenuto in nodePrint
     *
     * @param nodePrint Nodo dell'AST, contiene un NodeId
     */
    @Override
    public void visit(NodePrint nodePrint) {
        nodePrint.getId().accept(this);
        codiceDc = "l" + codiceDc + " p P";
    }

    /**
     * Genera il codice Dc per assegnare a un registro il risultato di un'espressione
     *
     * @param nodeAssign Nodo dell'AST
     */
    @Override
    public void visit(NodeAssign nodeAssign) {
        nodeAssign.getExpr().accept(this);
        String exprCode = codiceDc;

        nodeAssign.getId().accept(this);
        String reg = codiceDc;

        codiceDc = exprCode + " s" + reg;
        if(codiceDc.contains(" k ")) codiceDc = codiceDc.concat(" 0 k");
    }

    /**
     * Ritorna il log di errore
     *
     * @return Il log di errore se presente, altrimenti null
     */
    public String getLog() {
        return log;
    }
}
