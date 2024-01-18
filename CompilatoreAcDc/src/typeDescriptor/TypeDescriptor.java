package typeDescriptor;

public class TypeDescriptor {
    private final TypeTD type;
    private String errore = null;
    private int row;

    public TypeDescriptor(TypeTD t){
        this.type = t;
    }

    public TypeDescriptor(TypeTD t, String errore){
        this.type = t;
        this.errore = errore;
    }

    public void setRow(int row){
        this.row = row;
    }

    public TypeTD getTypeTD(){
        return type;
    }
    public String getMessaggioErrore(){
        return "r:"+ row +" "+ errore;
    }
}
