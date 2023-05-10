package src;

import java.util.List;

public class Funcion {
    private String name;
    private String tipoRetorno;
    private List<ElementoTabla> parameters;

    public Funcion(String name, List<ElementoTabla> parameters, String tipoRetorno) {
        this.name = name;
        this.tipoRetorno = tipoRetorno;
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public String getTipoRetorno() {
        return tipoRetorno;
    }
    public List<ElementoTabla> getParameters() {
        return parameters;
    }
}
