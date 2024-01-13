package typeDescriptor;

public class TypeDescriptor {
    private final TypeTD type;
    private String messaggio = null;

    public TypeDescriptor(TypeTD t){
        this.type = t;
    }

    public TypeDescriptor(TypeTD t, String messaggio){
        this.type = t;
        this.messaggio = messaggio;
    }

    public TypeTD getTypeTD(){
        return type;
    }
    public String getMessaggio(){
        return messaggio;
    }
}
