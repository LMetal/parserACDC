package visitor;

import AST.*;
import symbolTable.SymbolTable;
import typeDescriptor.TypeDescriptor;
import typeDescriptor.TypeTD;

import java.util.ArrayList;

public class TypeCheckingVisitor implements IVisitor{
    private TypeDescriptor resType;
    private ArrayList<TypeDescriptor> linesRes;

    public TypeCheckingVisitor(){
        SymbolTable.init();
        linesRes = new ArrayList<>();
    }

    public TypeDescriptor getResType(){
        return resType;
    }

    public ArrayList<TypeDescriptor> getLinesRes(){
        return linesRes;
    }

    @Override
    public void visit(NodeProgram node) {
        for(NodeDecSt line: node.getdecStm()){
            line.accept(this);
            linesRes.add(resType);
        }
    }

    @Override
    public void visit(NodeId node) {
        if(SymbolTable.lookup(node.getName()) == null) resType = new TypeDescriptor(TypeTD.ERROR);
        else resType = new TypeDescriptor(TypeTD.OK);
    }

    @Override
    public void visit(NodeDecl node) {
        // TODO replace null ??? idk
        if(SymbolTable.enter(node.getNodeId().getName(), LangType.FLOAT)){
            resType = new TypeDescriptor(TypeTD.OK);
        }
        else{
            resType = new TypeDescriptor(TypeTD.ERROR);
        }
    }

    @Override
    public void visit(NodeBinOp node) {
        node.getLeft().accept(this);
        TypeDescriptor leftTD = resType;
        node.getRight().accept(this);
        TypeDescriptor rightTD = resType;

        if(leftTD.getTypeTD() == TypeTD.ERROR || rightTD.getTypeTD() == TypeTD.ERROR) resType = new TypeDescriptor(TypeTD.ERROR);

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

    }

    @Override
    public void visit(NodePrint nodePrint) {
        nodePrint.getId().accept(this);
        if(resType.getTypeTD() == TypeTD.OK) resType = new TypeDescriptor(TypeTD.OK);
        else resType = new TypeDescriptor(TypeTD.ERROR);
    }

    @Override
    public void visit(NodeAssign nodeAssign) {

    }
}
