public class ArrayStack<T> implements ArrayStackADT<T> {

    private T[] stack;
    private int top;

    private final int DEFAULT_CAPACITY = 14;

    public ArrayStack() {
        this.top = 0;
        this.stack = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    public ArrayStack(int initialCapacity) {
        this.top = 0;
        this.stack = (T[]) (new Object[initialCapacity]);
    }

    public void push(T dataItem) {

    }
}
