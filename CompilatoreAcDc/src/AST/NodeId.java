package AST;

import token.Token;

public class NodeId extends NodeAST{
    private final String name;
    public NodeId(Token t){
        this.name = t.getVal();
    }

    public String toString(){
        return "<ID: " + name+">";
    }
}
