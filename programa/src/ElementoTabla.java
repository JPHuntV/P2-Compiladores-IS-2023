package src;


/* Define a class for formal parameters */
public class ElementoTabla {
    private String name;
    private String type;
    private int size;

    public ElementoTabla(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public ElementoTabla(String name, String type, int size) {
        this.name = name;
        this.type = type;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getSize(){
        return size;
    }
}
