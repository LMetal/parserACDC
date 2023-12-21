package AST;

public class NodeDecl extends NodeDecSt{
    private final NodeId id;
    private final LangType type;
    private final NodeExpr init;

    public NodeDecl(LangType ty, NodeId id, NodeExpr exp){
        this.id = id;
        this.type = ty;
        this.init = exp;
    }

    public String toString(){
        return "<DECL: " + type + " " + id + " " + init+">";
    }
}
