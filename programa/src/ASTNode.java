package src;

import java.util.ArrayList;

public class ASTNode {
    private String type;
    private Object value;
    private ArrayList<ASTNode> children;

    public ASTNode(String type) {
        this.type = type;
        this.value = null;
        this.children = new ArrayList<>();
    }

    public ASTNode(String type, Object value) {
        this.type = type;
        this.value = value;
        this.children = new ArrayList<>();
    }

    public void setType(String type){
        this.type = type;
    }
    public void addChild(ASTNode child) {
        this.children.add(child);
    }
    public void addChildren(ArrayList<ASTNode> newChildren) {
        children.addAll(newChildren);
    }
    public String getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public ArrayList<ASTNode> getChildren() {
        return children;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(type);
        if (value != null) {
            sb.append(": ");
            sb.append(value);
        }
        if (children != null && children.size() > 0) {
            sb.append(" {\n");
            for (ASTNode child : children) {
                sb.append(child.toString().replaceAll("(?m)^", "  "));
                sb.append("\n");
            }
            sb.append("}");
        }
        return sb.toString();
    }
}
