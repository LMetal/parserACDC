package AST;

import visitor.IVisitor;

public class NodeConst extends NodeExpr{
    private final String value;
    private final LangType type;

    public NodeConst(String value, LangType type){
        this.type = type;
        this.value = value;
    }

    public String toString(){
        return "<CONST: " + type + " " + value + ">";
    }

    @Override
    public String toStringConcise() {
        return value;
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

    public LangType getType() {
        return type;
    }
}
