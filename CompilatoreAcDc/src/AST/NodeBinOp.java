package AST;

import visitor.IVisitor;

public class NodeBinOp extends NodeExpr{
    private NodeExpr left;
    private NodeExpr right;
    private final LangOper op;

    public NodeBinOp(NodeExpr l, LangOper op, NodeExpr r){
        this.left = l;
        this.op = op;
        this.right = r;
    }

    public String toString(){
        return "<BINOP: "+ left + " " + op + " " + right + ">";
    }

    @Override
    public String toStringConcise() {
        return "(" + left.toStringConcise() + " " + op.toString() + " " + right.toStringConcise() + ")";
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

    public NodeExpr getLeft() {
        return left;
    }

    public NodeExpr getRight() {
        return right;
    }

    public LangOper getOp(){
        return op;
    }

    public void setLeft(NodeConvert nodeConvert) {
        this.left = nodeConvert;
    }

    public void setRight(NodeConvert nodeConvert) {
        this.right = nodeConvert;
    }
}
