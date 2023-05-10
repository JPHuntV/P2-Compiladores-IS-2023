package src;

import java.util.Stack;

public class SymbolTableStack {
    private Stack<SymbolTable> stack;

    public SymbolTableStack() {
        stack = new Stack<SymbolTable>();
    }

    public void push(SymbolTable symbolTable) {
        stack.push(symbolTable);
    }

    public SymbolTable pop() {
        return stack.pop();
    }

    public SymbolTable peek() {
        return stack.peek();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public Stack<SymbolTable> getStack(){
        return this.stack;
    }
}
