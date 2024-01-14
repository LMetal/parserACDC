package visitor;

import AST.*;
import symbolTable.SymbolTable;
import typeDescriptor.TypeDescriptor;
import typeDescriptor.TypeTD;

import java.util.ArrayList;
import java.util.Iterator;

public class TypeCheckingVisitor implements IVisitor{
    private TypeDescriptor resType;
    private final ArrayList<TypeDescriptor> linesRes;

    public TypeCheckingVisitor(){
        SymbolTable.init();
        linesRes = new ArrayList<>();
    }


    public Iterator<TypeDescriptor> getLinesRes(){
        return linesRes.iterator();
    }

    @Override
    public void visit(NodeProgram node) {
        for(NodeDecSt line: node.getDecStm()){
            line.accept(this);
            linesRes.add(resType);
        }
    }

    @Override
    public void visit(NodeId node) {
        if(SymbolTable.lookup(node.getName()) == null) resType = new TypeDescriptor(TypeTD.ERROR, node.getName() + " non dichiarato");
        else if(SymbolTable.lookup(node.getName()) == LangType.INT)
            resType = new TypeDescriptor(TypeTD.INT);
        else
            resType = new TypeDescriptor(TypeTD.FLOAT);
    }

    @Override
    public void visit(NodeDecl node) {
        TypeDescriptor idRes, initRes;

        // se id gia' dichiarato, errore e return
        if(SymbolTable.enter(node.getNodeId().getName(), node.getType())){
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
        initRes = resType;
        if(idRes.getTypeTD() == TypeTD.INT) {
            if (initRes.getTypeTD() == TypeTD.INT) resType = new TypeDescriptor(TypeTD.OK);
            else resType = new TypeDescriptor(TypeTD.ERROR, "impossibile inizializzare INT a FLOAT");
        } else {
            resType = new TypeDescriptor(TypeTD.OK);
        }

    }

    @Override
    public void visit(NodeBinOp node) {
        node.getLeft().accept(this);
        TypeDescriptor leftTD = resType;
        node.getRight().accept(this);
        TypeDescriptor rightTD = resType;

        //System.out.println("left "+node.getLeft().toString()+" "+ leftTD.getTypeTD() + " right "+node.getRight().toString()+" "+ rightTD.getTypeTD());

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

    @Override
    public void visit(NodeConvert nodeConvert) {
        resType = new TypeDescriptor(TypeTD.FLOAT);
    }

    @Override
    public void visit(NodeConst nodeConst) {
        if(nodeConst.getType() == LangType.INT) resType = new TypeDescriptor(TypeTD.INT);
        else resType = new TypeDescriptor(TypeTD.FLOAT);
    }

    @Override
    public void visit(NodeDeref nodeDeref) {
        nodeDeref.getId().accept(this);
    }

    @Override
    public void visit(NodePrint nodePrint) {
        nodePrint.getId().accept(this);
        if(resType.getTypeTD() != TypeTD.ERROR) {
            resType = new TypeDescriptor(TypeTD.OK);
        }
    }

    @Override
    public void visit(NodeAssign nodeAssign) {
        nodeAssign.getId().accept(this);
        TypeDescriptor idRes = resType;
        nodeAssign.getExpr().accept(this);
        TypeDescriptor exprRes = resType;

        //System.out.println("Assign: "+nodeAssign.getId().toString() + idRes.getTypeTD() + " "+nodeAssign.getExpr().toString()+ exprRes.getTypeTD());

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
