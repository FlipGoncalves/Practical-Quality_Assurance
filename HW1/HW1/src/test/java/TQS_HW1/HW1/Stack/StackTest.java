package TQS_HW1.HW1.Stack;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class StackTest 
{
    private Stack<String> stack;

    /**
     * Rigorous Test :-)
     */

    @BeforeEach
    public void setup() {
        this.stack = new Stack<String>();
    }

    @Test
    @DisplayName("A stack is empty on construction")
    public void isEmpty() {
        assertTrue(this.stack.isEmpty());
    }

    @Test
    @DisplayName("A stack has size 0 on construction")
    public void size() {
        assertTrue(this.stack.size() == 0);
    }

    @Test
    @DisplayName("After n pushes to an empty stack, n > 0, the stack is not empty and its size is n")
    public void push_size() {
        int limit = 15;
        for(int i = 0; i < limit; i++) {
            this.stack.push("sometext");
        }
        assertTrue(this.stack.size() == limit);
    }

    @Test
    @DisplayName("If one pushes x then pops, the value popped is x")
    public void pop() {
        this.stack.push("poptext");
        String elem = this.stack.pop();
        assertTrue(elem == "poptext");
    }

    @Test
    @DisplayName("If one pushes x then peeks, the value returned is x, but the size stays the same")
    public void peek() {
        this.stack.push("peektext");
        String elem = this.stack.peek();
        assertTrue((elem == "peektext") && !(this.stack.isEmpty()));
    }

    @Test
    @DisplayName("If the size is n, then after n pops, the stack is empty and has a size 0")
    public void n_pop() {
        int size = this.stack.size();
        if (size == 0) {
            size = 15;
            for (int i = 0; i < size; i++)
                this.stack.push("poptext");
        }
        for (int i = 0; i < size; i++)
            this.stack.pop();
        assertTrue((this.stack.size() == 0) && (this.stack.isEmpty()));
    }

    @Test
    @DisplayName("Popping from an empty stack does throw a NoSuchElementException")
    public void pop_0() {
        if (this.stack.size() == 0) {
            assertThrows(NoSuchElementException.class, () -> this.stack.pop());
        }
    }

    @Test
    @DisplayName("Peeking into an empty stack does throw a NoSuchElementException")
    public void peek_0() {
        if (this.stack.size() == 0) {
            assertThrows(NoSuchElementException.class, () -> this.stack.peek());
        }
    }

    @Test
    @DisplayName("For bounded stacks only:pushing onto a full stack does throw an IllegalStateException")
    public void bounded(){
        int limit = 5;
        this.stack = new Stack<String>(limit);
        for (int i = 0; i < limit; i++)
            this.stack.push("test");

        assertThrows(IllegalStateException.class, () -> this.stack.push("testbounded"));
    }

    @Test
    @DisplayName("Test for element cached")
    public void el_exists(){
        int limit = 5;
        this.stack = new Stack<String>(limit);
        for (int i = 0; i < limit; i++) {
            String test = "test" + i;
            this.stack.push(test);
        }

        assertTrue(this.stack.exists("test2"));
    }
}
