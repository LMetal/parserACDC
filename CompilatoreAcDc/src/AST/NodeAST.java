package AST;

import visitor.IVisitor;

public abstract class NodeAST {
    public abstract String toString();
    public abstract String toStringConcise();

    public abstract void accept(IVisitor visitor);
}
