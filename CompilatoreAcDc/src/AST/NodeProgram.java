package AST;

import java.util.ArrayList;

public class NodeProgram extends NodeAST {
    private final ArrayList<NodeDecSt> decSts;

    public NodeProgram(ArrayList<NodeDecSt> nodes){
        this.decSts = nodes;
    }

    public ArrayList<NodeDecSt> getdecStm(){
        return decSts;
    }

    public String toString(){
        return "PROGRAM : " + decSts.toString();
    }

}
