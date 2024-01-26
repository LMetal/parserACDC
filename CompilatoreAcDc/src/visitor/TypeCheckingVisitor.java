package visitor;

import AST.*;
import symbolTable.SymbolTable;
import typeDescriptor.TypeDescriptor;
import typeDescriptor.TypeTD;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * Implementa un visitatore per l'analisi semantica e il controllo dei tipi nell'AST.
 */
public class TypeCheckingVisitor implements IVisitor{
    /**
     * Il tipo risultante dall'analisi semantica.
     */
    private TypeDescriptor resType;

    /**
     * Lista di tipi risultanti per ogni riga di codice ac
     */
    private final ArrayList<TypeDescriptor> linesRes;

    /**
     * Costruisce un nuovo oggetto TypeCheckingVisitor e inizializza la tabella
     */
    public TypeCheckingVisitor() {
        SymbolTable.init();
        linesRes = new ArrayList<>();
    }

    /**
     * Ritorna un iteratore per la lista dei tipi risultanti per ogni riga
     *
     * @return Un iteratore per la lista dei tipi risultanti
     */
    public Iterator<TypeDescriptor> getLinesRes(){
        return linesRes.iterator();
    }


    /**
     * Scorre tutte le righe e salva i risultati
     *
     * @param node NodeProgram da visitare
     */
    @Override
    public void visit(NodeProgram node) {
        int row = 0;
        for(NodeDecSt line: node.getDecStm()){
            line.accept(this);
            linesRes.add(resType);
            if(resType.getTypeTD() == TypeTD.ERROR) resType.setRow(row);
            row++;
        }
    }

    /**
     * @param node NodeId da visitare
     */
    @Override
    public void visit(NodeId node) {
        if(SymbolTable.lookup(node.getName()) == null) resType = new TypeDescriptor(TypeTD.ERROR, node.getName() + " non dichiarato");
        else if(SymbolTable.lookup(node.getName()).getTipo() == LangType.INT)
            resType = new TypeDescriptor(TypeTD.INT);
        else
            resType = new TypeDescriptor(TypeTD.FLOAT);
    }

    /**
     * Aggiunge il nuovo id nella SymbolTable, se c'e' inizializzazione genera il codice.
     * Ritorna ERROR se id e' gia' stato dichiarato.
     *
     * @param node NodeDecl da visitare
     */
    @Override
    public void visit(NodeDecl node) {
        TypeDescriptor idRes, initRes;

        // se id gia' dichiarato, errore e return
        if(SymbolTable.enter(node.getNodeId().getName(), new SymbolTable.Attributes(node.getType(), node.getNodeId().getName()))){
            if(node.getType() == LangType.INT) idRes = new TypeDescriptor(TypeTD.INT);
            else idRes = new TypeDescriptor(TypeTD.FLOAT);
        }
        else{
            resType = new TypeDescriptor(TypeTD.ERROR, node.getNodeId().getName() + " gia' dichiarato");
            return;
        }

        if(node.getInit() == null){
            resType = new TypeDescriptor(TypeTD.OK);
            return;
        }

        node.getInit().accept(this);
        SymbolTable.lookup(node.getNodeId().getName());
        initRes = resType;
        if(idRes.getTypeTD() == TypeTD.INT) {
            if (initRes.getTypeTD() == TypeTD.INT) resType = new TypeDescriptor(TypeTD.OK);
            else resType = new TypeDescriptor(TypeTD.ERROR, "impossibile inizializzare INT a FLOAT");
        } else {
            resType = new TypeDescriptor(TypeTD.OK);
        }

    }

    /**
     * Controlla tipo della parte desta e sinistra, se niente e' ERROR ritorna il tipo.
     *
     * @param node NodeBinOp da visitare
     */
    @Override
    public void visit(NodeBinOp node) {
        node.getLeft().accept(this);
        TypeDescriptor leftTD = resType;
        node.getRight().accept(this);
        TypeDescriptor rightTD = resType;

        if(leftTD.getTypeTD() == TypeTD.ERROR) {
            resType = leftTD;
            return;
        }
        if(rightTD.getTypeTD() == TypeTD.ERROR) {
            resType = rightTD;
            return;
        }

        if(leftTD.getTypeTD() != rightTD.getTypeTD()){
            if(leftTD.getTypeTD() == TypeTD.INT){
                node.setLeft(new NodeConvert(node.getLeft()));
            }
            else{
                node.setRight(new NodeConvert(node.getRight()));
            }
            resType = new TypeDescriptor(TypeTD.FLOAT);
        } else {
            if(leftTD.getTypeTD() == TypeTD.INT) resType = new TypeDescriptor(TypeTD.INT);
            else resType = new TypeDescriptor(TypeTD.FLOAT);
        }
    }

    /**
     * Ritorna FLOAT. Se e' presente un nodeConvert l'espressione e' sicuramente valida e FLOAT.
     *
     * @param nodeConvert NodeConvert da visitare
     */
    @Override
    public void visit(NodeConvert nodeConvert) {
        resType = new TypeDescriptor(TypeTD.FLOAT);
    }

    /**
     * Ritorna il tipo della costante
     *
     * @param nodeConst NodeConst da visitare
     */
    @Override
    public void visit(NodeConst nodeConst) {
        if(nodeConst.getType() == LangType.INT) resType = new TypeDescriptor(TypeTD.INT);
        else resType = new TypeDescriptor(TypeTD.FLOAT);
    }

    /**
     * Passa la visita all'id contenuto
     *
     * @param nodeDeref NodeDeref da visitare
     */
    @Override
    public void visit(NodeDeref nodeDeref) {
        nodeDeref.getId().accept(this);
    }

    /**
     * Controlla che l'id non sia ERROR, ritorna OK.
     *
     * @param nodePrint NodePrint da visitare
     */
    @Override
    public void visit(NodePrint nodePrint) {
        nodePrint.getId().accept(this);
        if(resType.getTypeTD() != TypeTD.ERROR) {
            resType = new TypeDescriptor(TypeTD.OK);
        }
    }

    /**
     * Controlla che ne id ne expr siano ERROR.
     * Controlla che i tipi siano compatibili.
     * Ritorna OK.
     *
     * @param nodeAssign NodeAssign da visitare
     */
    @Override
    public void visit(NodeAssign nodeAssign) {
        nodeAssign.getId().accept(this);
        TypeDescriptor idRes = resType;
        nodeAssign.getExpr().accept(this);
        TypeDescriptor exprRes = resType;

        if(idRes.getTypeTD() == TypeTD.ERROR){
            resType = idRes;
            return;
        }

        if(exprRes.getTypeTD() == TypeTD.ERROR){
            resType = exprRes;
            return;
        }


        //se sto assegnando un float a un id INT ERRORE
        if(idRes.getTypeTD() == TypeTD.INT && exprRes.getTypeTD() == TypeTD.FLOAT){
            resType = new TypeDescriptor(TypeTD.ERROR, "Impossibile assegnare FLOAT a id INT");

            return;
        }

        resType = new TypeDescriptor(TypeTD.OK);

    }
}
