package AST;

public class NodeDeref extends NodeExpr{
    private final NodeId id;

    public NodeDeref(NodeId id){
        this.id = id;
    }

    public String toString(){
        return id.toString();
    }
}
