package AST;

public class NodeBinOp extends NodeExpr{
    private final NodeExpr left;
    private final NodeExpr right;
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
        return "(" + left.toStringConcise() + " " + op.toStringConcise() + " " + right.toStringConcise() + ")";
    }
}
