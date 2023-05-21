package src;
import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private Map<String, String> symbols;
    private String name;
    private String tipoRetorno;
    private int cantParametros;

    public SymbolTable(String name, String tipoRetorno) {
        this.name = name;
        this.tipoRetorno = tipoRetorno;
        symbols = new HashMap<String, String>();
    }

    public void addSymbol(String name, String type) {
        symbols.put(name, type);
    }

    public boolean containsSymbol(String name) {
        return symbols.containsKey(name);
    }

    public String getType(String name) {
        return symbols.get(name);
    }

    public String getName(){
        return this.name;
    }

    public String getTipoRetorno(){
        return this.tipoRetorno;
    }

    public void setCantParametros(int cant){
        this.cantParametros = cant;
    }

    public int getCantParametros(){
        return this.cantParametros;
    }

    public Map<String, String> getSymbols(){
        return this.symbols;
    }
}
