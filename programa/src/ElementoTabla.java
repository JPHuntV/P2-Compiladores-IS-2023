package src;


/* Define a class for formal parameters */
public class ElementoTabla {
    private String name;
    private String type;

    public ElementoTabla(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
