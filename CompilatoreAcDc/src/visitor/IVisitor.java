package visitor;

import AST.*;

public interface IVisitor {
    public abstract void visit(NodeProgram node);
    public abstract void visit(NodeId node);
    public abstract void visit(NodeDecl node);
    public abstract void visit(NodeBinOp node);
    public abstract void visit(NodeConvert nodeConvert);
    public abstract void visit(NodeConst nodeConst);
    public abstract void visit(NodeDeref nodeDeref);
    public abstract void visit(NodePrint nodePrint);
    public abstract void visit(NodeAssign nodeAssign);
}
