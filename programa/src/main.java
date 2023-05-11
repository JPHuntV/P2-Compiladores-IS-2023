package src;
public class main {
    public static void main(String[] args) {
        App app = new App();
        app.analizar("programa/src/ejemplo código 1.txt");//lexer
        app.parsear("programa/src/ejemplo código 1.txt");//parser
    }
}
