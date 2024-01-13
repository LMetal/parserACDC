package AST;

import visitor.IVisitor;

import java.util.ArrayList;

public class NodeProgram extends NodeAST {
    private final ArrayList<NodeDecSt> decSts;

    public NodeProgram(ArrayList<NodeDecSt> nodes){
        this.decSts = nodes;
    }

    public ArrayList<NodeDecSt> getdecStm(){
        return decSts;
    }

    @Override
    public String toString(){
        return "PROGRAM : " + decSts.toString();
    }

    @Override
    public String toStringConcise() {
        return decSts.stream()
                .map(NodeDecSt::toStringConcise)
                .reduce("", (partialResult, element) -> partialResult + element);
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

}
