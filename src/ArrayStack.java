public class ArrayStack<T> implements ArrayStackADT<T> {

    private T[] stack;
    private int top;

    public static String sequence;

    private final int DEFAULT_CAPACITY = 14;

    public ArrayStack() {
        this.top = -1;
        this.stack = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    public ArrayStack(int initialCapacity) {
        this.top = 0;
        this.stack = (T[]) (new Object[initialCapacity]);
    }

    @Override
    public void push(T dataItem) {

        if (this.top == this.stack.length)
            expandCapacity();

        stack[this.top] = dataItem;
        this.top++;

        if (dataItem instanceof MapCell) {
            sequence += "push" + ((MapCell) dataItem).getIdentifier();
        } else {
            sequence += "push" + dataItem.toString();
        }
    }

    @Override
    public T pop() throws EmptyStackException {
        return null;

    }

    @Override
    public T peek() throws java.util.EmptyStackException {
        if (this.top <= 0)
            throw new EmptyStackException("Empty stack");
        return stack[this.top - 1];
    }

    @Override
    public boolean isEmpty() {
        return (this.top == 0);
    }

    @Override
    public int size() {
        return this.top;
    }

    private void expandCapacity() {

        int increaseNum;

        if (this.stack.length < 50) {
            increaseNum = this.stack.length + 10;
        } else {
            increaseNum = stack.length * 2;
        }

        T[] larger = (T[]) (new Object[increaseNum]);

        for (int i = 0; i < this.stack.length; i++)
            larger[i] = this.stack[i];
        this.stack = larger;
    }

}
