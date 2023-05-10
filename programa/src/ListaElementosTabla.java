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

    public List<ElementoTabla> getParams() {
        return params;
    }
}