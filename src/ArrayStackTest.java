import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class ArrayStackTest {

    @Test
    public void newStackIsEmpty() {
        ArrayStack<Integer> s = new ArrayStack<>();
        assertTrue(s.isEmpty());
        assertEquals(0, s.size());
    }

    @Test
    void pushThenPopIsLastInFirstOut() {
        ArrayStack<String> s = new ArrayStack<>();
        s.push("a");
        s.push("b");
        s.push("c");
        assertEquals(3, s.size());
        assertFalse(s.isEmpty());
        assertEquals("c", s.pop());
        assertEquals("b", s.pop());
        assertEquals("a", s.pop());
        assertTrue(s.isEmpty());
    }

    @Test
    void peekDoesNotRemove() {
        ArrayStack<Integer> s = new ArrayStack<>();
        s.push(7);
        assertEquals(7, (int) s.peek());
        assertEquals(1, s.size());
    }

    @Test
    void growsPastDefaultCapacity() {
        ArrayStack<Integer> s = new ArrayStack<>();
        for (int i = 0; i < 25; i++) {
            s.push(i);
        }
        assertEquals(25, s.size());
        for (int i = 24; i >= 0; i--) {
            assertEquals(i, (int) s.pop());
        }
    }

    @Test
    void popOnEmptyThrows() {
        ArrayStack<Integer> s = new ArrayStack<>();
        assertThrows(NoSuchElementException.class, s::pop);
    }

    @Test
    void peekOnEmptyThrows() {
        ArrayStack<Integer> s = new ArrayStack<>();
        assertThrows(NoSuchElementException.class, s::peek);
    }
}