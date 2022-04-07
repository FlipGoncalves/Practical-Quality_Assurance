package TQS_HW1.HW1.Stack;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Stack<T> {

    private final ArrayList<T> array =  new ArrayList<>();
    private Integer limit = null;
  
    public Stack() {}
  
    public Stack(int limit) {
      this.limit = limit;
    }

    public void push(T x) {
        if (limit == null) array.add(x);
        else if (array.size() < limit) array.add(x);
        else throw new IllegalStateException();
    }

    public T peek() {
        if (array.size() > 0) {
            return array.get(array.size()-1);
        }
        throw new NoSuchElementException();
    }

    public T pop() {
        if (array.size() > 0) {
            return array.remove(array.size()-1);
        }
        throw new NoSuchElementException();
    }

    public int size() {
        return array.size();
    }

    public boolean isEmpty() {
        if (array.size() == 0) return true;
        return false;
    }

    public boolean exists(T element) {
        for(T el: array)
            if (el.equals(element))
                return true;
        return false;
    }
}
