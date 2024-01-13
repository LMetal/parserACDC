package AST;

import visitor.IVisitor;

public class NodeDeref extends NodeExpr{
    private final NodeId id;

    public NodeDeref(NodeId id){
        this.id = id;
    }

    public String toString(){
        return id.toString();
    }

    @Override
    public String toStringConcise() {
        return id.toStringConcise();
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
