package AST;

import visitor.IVisitor;

public class NodePrint extends NodeStm{
    private final NodeId id;

    public NodePrint(NodeId node){
        this.id = node;
    }

    public String toString() {
        return "<PRINT: " + id.toString()+">";
    }

    @Override
    public String toStringConcise() {
        return "PRINT" + id.toStringConcise();
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

    public NodeId getId() {
        return this.id;
    }
}
