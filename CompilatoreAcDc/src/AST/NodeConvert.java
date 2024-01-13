package AST;

import visitor.IVisitor;

public class NodeConvert extends NodeExpr{
    NodeExpr node;
    public NodeConvert(NodeExpr node){
        this.node = node;
    }

    @Override
    public String toString() {
        return "<CONVERT: " + node.toString() +">";
    }

    @Override
    public String toStringConcise() {
        return "(float)"+node.toStringConcise();
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
