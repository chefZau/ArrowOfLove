public class ArrayStack<T> implements ArrayStackADT<T> {

    private T[] stack; // stores the data items of the stack.
    private int top; // position of last element in the stack
    public static String sequence; // use for tracking the arrow path.
    private final int DEFAULT_CAPACITY = 14; // default capacity

    /**
     * The default constructor. It will creates an empty stack. The default initial
     * capacity of the array used to store the items of the stack is 14
     */
    public ArrayStack() {
        this.top = -1;
        this.stack = (T[]) (new Object[DEFAULT_CAPACITY]);
        ArrayStack.sequence = "";
    }

    /**
     * Creates an empty stack using an array of length equal to the value of the
     * parameter.
     */
    public ArrayStack(int initialCapacity) {
        this.top = -1;
        this.stack = (T[]) (new Object[initialCapacity]);
        ArrayStack.sequence = "";
    }

    /**
     * Adds dataItem to the top of the stack. If the array storing the data items is
     * full, hepler method expandCapacity() will called.
     */
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

    /**
     * Removes and returns the data item at the top of the stack. An
     * EmptyStackException is thrown if the stack is empty.
     * 
     * @return the data item at the top of the stack
     */
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

    /**
     * Returns the data item at the top of the stack without removing it. An
     * EmptyStackException is thrown if the stack is empty.
     * 
     * @return the data item at the top of the stack
     */
    @Override
    public T peek() throws java.util.EmptyStackException {
        if (this.top == -1)
            throw new EmptyStackException("Empty stack");
        return stack[this.top];
    }

    /**
     * Check if the stack is empty.
     * 
     * @return true if the stack is empty and returns false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return (this.top == -1);
    }

    /**
     * Retrieve the number of data items in the stack
     * 
     * @return the number of data items in the stack
     */
    @Override
    public int size() {
        return this.top + 1;
    }

    /**
     * Return the capacity of the stack.
     * 
     * @return the capacity of the array stack.
     */
    public int length() {
        return this.stack.length;
    }

    /**
     * Returns a String representation of the stack of the form: “Stack: elem1,
     * elem2, ..." where element i is a String representation of the i-th element of
     * the stack.
     * 
     * @return the String representation of the stack
     */
    public String toString() {
        String results = "Stack: ";

        for (int i = 0; i < this.top + 1; i++) {

            if (i < this.stack.length - 1) {
                results += this.stack[i].toString() + ", ";
            } else {
                results += this.stack[i].toString(); // last element
            }
        }

        return results;
    }

    /**
     * Heplper method for explanding array. It will be called from the push method
     * when the array is full.
     */
    private void expandCapacity() {

        // calculate the new array size
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

    /**
     * Helper method for removing extra spaces. This function will be called from
     * the pop method when the number of data items remaining is smaller than one
     * fourth of the length of the array
     */
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
