package AST;

public class NodeDecl extends NodeDecSt{
    private final NodeId id;
    private final LangType type;
    private final NodeExpr init;

    public NodeDecl(LangType ty, NodeId id, NodeExpr init){
        this.id = id;
        this.type = ty;
        this.init = init;
    }

    public String toString(){
        return "<DECL: " + type + " " + id + " " + init+">";
    }

    @Override
    public String toStringConcise() {
        return type + " " + id.toStringConcise() + " = " + init.toStringConcise();
    }
}
