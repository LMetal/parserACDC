package AST;

public enum LangOper {
    PLUS,
    MINUS,
    MULTIP,
    DIVISION;

    public String toString(){
        return switch (this) {
            case PLUS -> "+";
            case MINUS -> "-";
            case MULTIP -> "*";
            case DIVISION -> "/";
        };
    }
}
