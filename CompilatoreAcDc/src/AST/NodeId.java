package AST;

public class NodeId extends NodeAST{
    private final String name;
    public NodeId(String id){
        this.name = id;
    }

    @Override
    public String toString(){
        return "<ID: " + name+">";
    }

    @Override
    public String toStringConcise() {
        return name;
    }
}
