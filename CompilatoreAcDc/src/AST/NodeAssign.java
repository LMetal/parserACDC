package AST;

public class NodeAssign extends NodeStm{
    private final NodeId id;
    private final NodeExpr expr;

    public NodeAssign(NodeId id, NodeExpr expr){
        this.id = id;
        this.expr = expr;
    }

    public String toString(){
        return "<ASSIGN: " + id + " " + expr +">";
    }
}
