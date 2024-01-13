package AST;

import visitor.IVisitor;

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

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

    public String getName(){
        return name;
    }
}
