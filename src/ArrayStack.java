public class ArrayStack<T> implements ArrayStackADT<T> {

    private T[] stack;  
    private int top;    // position of last element in the stack

    public static String sequence;

    private final int DEFAULT_CAPACITY = 14;

    public ArrayStack() {
        this.top = -1;
        this.stack = (T[]) (new Object[DEFAULT_CAPACITY]);
        ArrayStack.sequence = "";
    }

    public ArrayStack(int initialCapacity) {
        this.top = -1;
        this.stack = (T[]) (new Object[initialCapacity]);
        ArrayStack.sequence = "";
    }

    @Override
    public void push(T dataItem) {

        if (this.top + 1 == this.stack.length)
            expandCapacity();

        this.top++;
        this.stack[this.top] = dataItem;

        if (dataItem instanceof MapCell) {
            sequence += "push" + ((MapCell) dataItem).getIdentifier();
        } else {
            sequence += "push" + dataItem.toString();
        }
    }

    @Override
    public T pop() throws EmptyStackException {

        if (top == -1)
            throw new EmptyStackException("Empty stack!");

        T topItem = this.stack[this.top];
        this.stack[this.top] = null;

        this.top--;

        if (this.top < this.stack.length / 4) {
            shrinkCapacity();
        }

        if (topItem instanceof MapCell) {
            sequence += "pop" + ((MapCell) topItem).getIdentifier();
        } else {
            sequence += "pop" + topItem.toString();
        }

        return topItem;
    }

    @Override
    public T peek() throws java.util.EmptyStackException {
        if (this.top == -1)
            throw new EmptyStackException("Empty stack");
        return stack[this.top];
    }

    @Override
    public boolean isEmpty() {
        return (this.top == -1);
    }

    @Override
    public int size() {
        return this.top + 1;
    }

    public int length() {
        return this.stack.length;
    }

    public String toString() {
        String results = "Stack: ";

        for (int i = 0; i < this.top + 1; i++) {
            
            if (i < this.stack.length - 1) {
                results += this.stack[i].toString() + ", ";
            } else {
                results += this.stack[i].toString();    // last element
            }
        }
        
        return results;
    }

    private void expandCapacity() {

        int newSize;
        if (this.stack.length < 50) {
            newSize = this.stack.length + 10;
        } else {
            newSize = stack.length * 2;
        }

        T[] larger = (T[]) (new Object[newSize]);

        for (int i = 0; i < this.stack.length; i++)
            larger[i] = this.stack[i];
        this.stack = larger;
    }

    private void shrinkCapacity() {

        int newSize = Math.floorDiv(this.stack.length, 2);

        if (newSize < 14) {
            newSize = 14;
        }

        T[] shrickArray = (T[]) (new Object[newSize]);

        for (int i = 0; i < this.top + 1; i++)
            shrickArray[i] = this.stack[i];

        this.stack = shrickArray;
    }

}
