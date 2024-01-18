package visitor;

import AST.*;
import symbolTable.Registri;
import symbolTable.SymbolTable;
import typeDescriptor.TypeTD;

import java.util.ArrayList;
import java.util.Iterator;

public class CodeGeneratorVisitor implements IVisitor{
    private String codiceDc;
    private ArrayList<String> linesCode;

    public CodeGeneratorVisitor(){
        linesCode = new ArrayList<>();
        codiceDc = "";
        Registri.init();
    }

    public Iterator<String> getDcCode(){
        return linesCode.iterator();
    }

    @Override
    public void visit(NodeProgram node) {
        for(NodeDecSt line: node.getDecStm()){
            line.accept(this);
            if(codiceDc.contains(" k ")) codiceDc = codiceDc.concat(" 0 k");
            linesCode.add(codiceDc);
            codiceDc = "";
        }
    }

    @Override
    public void visit(NodeId node) {
        char reg = SymbolTable.lookup(node.getName()).getRegister();
        codiceDc = "l"+reg;
    }

    @Override
    public void visit(NodeDecl node) {
        var att = SymbolTable.lookup(node.getNodeId().getName());
        att.setRegister(Registri.newRegister());

        if(node.getInit() != null){
            node.getInit().accept(this);
            String init = codiceDc;
            char reg = SymbolTable.lookup(node.getNodeId().getName()).getRegister();
            codiceDc = init + " s" + reg;
        }
    }

    @Override
    public void visit(NodeBinOp node) {
        node.getLeft().accept(this);
        String leftCodice = codiceDc;
        node.getRight().accept(this);
        String rightCodice = codiceDc;


        //if()

        codiceDc = leftCodice + " " + rightCodice + " " + node.getOp();

    }

    @Override
    public void visit(NodeConvert nodeConvert) {
        nodeConvert.getExpr().accept(this);
        String expr = codiceDc;
        codiceDc = "5 k " + codiceDc;
    }

    @Override
    public void visit(NodeConst nodeConst) {
        codiceDc = nodeConst.getValue();
    }

    @Override
    public void visit(NodeDeref nodeDeref) {
        nodeDeref.getId().accept(this);
    }

    @Override
    public void visit(NodePrint nodePrint) {
        codiceDc = "l" + nodePrint.getId().getName() + " p P";
    }

    @Override
    public void visit(NodeAssign nodeAssign) {
        nodeAssign.getExpr().accept(this);
        String exprCode = codiceDc;
        char reg = SymbolTable.lookup(nodeAssign.getId().getName()).getRegister();
        codiceDc = exprCode + " s" + reg;
    }
}
