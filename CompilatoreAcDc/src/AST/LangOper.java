package AST;

public enum LangOper {
    PLUS,
    MINUS,
    MULTIP,
    DIVISION;

    public String toStringConcise(){
        return switch (this) {
            case PLUS -> "+";
            case MINUS -> "-";
            case MULTIP -> "*";
            case DIVISION -> "/";
        };
    }
}
