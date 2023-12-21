package AST;

public class NodePrint extends NodeStm{
    private final NodeId id;

    public NodePrint(NodeId node){
        this.id = node;
    }

    public String toString() {
        return "<PRINT: " + id.toString()+">";
    }
}
