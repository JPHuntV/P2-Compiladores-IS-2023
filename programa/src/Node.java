package src;
class Node {
    private int lineNumber;   // line number in the source code
    private String symbol;    // symbol represented by this node
    private Node[] children;  // children of this node
    
    /* Constructor for leaf nodes */
    public Node(String symbol) {
      this.lineNumber = lineNumber;
      this.symbol = symbol;
      this.children = null;
    }
    
    /* Constructor for non-leaf nodes */
    public Node(String symbol, Node[] children) {
      this.symbol = symbol;
      this.children = children;
    }
    
    /* Getters */
    public int getLineNumber() {
      return lineNumber;
    }
    
    public String getSymbol() {
      return symbol;
    }
    
    public Node[] getChildren() {
      return children;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(symbol);
        if (children != null) {
            sb.append(" [\n");
            for (int i = 0; i < children.length; i++) {
                sb.append("  ");
                sb.append(children[i].toString().replaceAll("\n", "\n  "));
                if (i != children.length - 1) {
                    sb.append(",");
                }
                sb.append("\n");
            }
            sb.append("]");
        }
        return sb.toString();
    }
  }
  