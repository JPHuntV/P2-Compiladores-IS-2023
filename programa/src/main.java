package src;
public class main {
    public static void main(String[] args) {
        App app = new App();
        app.analizar("programa/src/ejemplo código 2.txt");//lexer
        app.parsear("programa/src/ejemplo código 2.txt");//parser
    }
}
