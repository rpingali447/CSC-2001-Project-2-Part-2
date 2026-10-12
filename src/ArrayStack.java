import java.util.NoSuchElementException;

public class ArrayStack<E> {
    // instance variables
    private E[] data;
    private int size;

    /*
    Purpose: Create an empty stack with a starting capacity of 10.
    Input/output: no input; creates an empty ArrayStack.
    Example: new ArrayStack<Integer>() -> empty stack, size() is 0
    */
    @SuppressWarnings("unchecked")
    public ArrayStack() {
        this.data = (E[]) new Object[10];
        this.size = 0;
    }

    /*
    Purpose: Add a value to the top of the stack.
    Input/output: adds value to the top; returns nothing.
    @param value: E
    Example: stack = [1,2]; push(3) -> stack = [1,2,3]
    */
    public void push(E value) {
        if (this.size == this.data.length) {
            this.resize(this.data.length * 2);
        }
        this.data[this.size] = value;
        this.size++;
    }

    /*
    Purpose: Remove and return the item on top of the stack.
    Input/output: no input; returns the removed top item.
    Throws NoSuchElementException if the stack is empty.
    Example: stack = [1,2,3]; pop() returns 3, stack = [1,2]
    */
    public E pop() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Cannot pop from an empty stack");
        }
        this.size--;
        E value = this.data[this.size];
        this.data[this.size] = null;
        return value;
    }

    /*
    Purpose: Return the item on top of the stack without removing it.
    Input/output: no input; returns the top item.
    Throws NoSuchElementException if the stack is empty.
    Example: stack = [1,2,3]; peek() returns 3, stack is still [1,2,3]
    */
    public E peek() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Cannot peek from an empty stack");
        }
        return this.data[this.size - 1];
    }

    /*
    Purpose: Return whether the stack has no items.
    Input/output: no input; returns true if empty, false otherwise.
    Example: new stack -> true; after push(4) -> false
    */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /*
    Purpose: Return the number of items in the stack.
    Input/output: no input; returns the item count as an int.
    Example: stack = [1, 2, 3, 4, 5] -> size returns 5.
    */
    public int size() {
        return this.size;
    }

    /*
    Purpose: Replace the array with a bigger array, copying items in order.
    Input/output: replaces the backing array; returns nothing.
    @param newSize: int
    Example: full array of capacity 10; resize(20) -> capacity 20, same items
    */
    @SuppressWarnings("unchecked")
    private void resize(int newSize) {
        E[] newData = (E[]) new Object[newSize];
        for (int i = 0; i < this.size; i++) {
            newData[i] = this.data[i];
        }
        this.data = newData;
    }
}