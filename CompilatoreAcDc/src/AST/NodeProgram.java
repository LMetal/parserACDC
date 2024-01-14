package AST;

import visitor.IVisitor;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class NodeProgram extends NodeAST {
    private final ArrayList<NodeDecSt> decSts;

    public NodeProgram(ArrayList<NodeDecSt> nodes){
        this.decSts = nodes;
    }

    public ArrayList<NodeDecSt> getDecStm(){
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
                .collect(Collectors.joining("; "));
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

}
