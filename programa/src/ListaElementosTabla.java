package src;

import java.util.ArrayList;
import java.util.List;

/* Define a class for the formal parameter list */
public class ListaElementosTabla {
    private List<ElementoTabla> params;

    public ListaElementosTabla() {
        params = new ArrayList<>();
        //params.add(param);
    }

    public void addParameter(ElementoTabla param) {
        params.add(param);
    }

    public String existe(String nombre){
        for (ElementoTabla elementoTabla : params) {
            System.out.println("-------------------------");
            System.out.println(nombre);
            System.out.println(elementoTabla.getName());
            if(nombre.equals(elementoTabla.getName())){
                System.out.println(elementoTabla.getType());
                return elementoTabla.getType();
            }
        }
        return null;
    }
    public List<ElementoTabla> getParams() {
        return params;
    }
}