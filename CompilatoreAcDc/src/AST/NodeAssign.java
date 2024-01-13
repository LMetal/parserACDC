package AST;

import visitor.IVisitor;

public class NodeAssign extends NodeStm{
    private final NodeId id;
    private final NodeExpr expr;

    public NodeAssign(NodeId id, NodeExpr expr){
        this.id = id;
        this.expr = expr;
    }

    @Override
    public String toString(){
        return "<ASSIGN: " + id + " " + expr +">";
    }

    @Override
    public String toStringConcise() {
        return id.toStringConcise() + " = " + expr.toStringConcise();
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

    public NodeId getId(){
        return id;
    }

    public NodeExpr getExpr(){
        return expr;
    }
}
