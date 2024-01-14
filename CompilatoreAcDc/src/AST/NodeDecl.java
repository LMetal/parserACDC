package AST;

import visitor.IVisitor;

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
        if(init != null) return type + " " + id.toStringConcise() + " = " + init.toStringConcise();
        else return type + " " + id.toStringConcise() + " = null";
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

    public NodeId getNodeId(){
        return id;
    }
    public NodeExpr getInit(){
        return init;
    }

    public LangType getType(){
        return type;
    }
}
