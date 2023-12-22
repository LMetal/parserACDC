package AST;

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
}
