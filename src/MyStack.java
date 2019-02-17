import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Objects;
//push, pop, size, equals, hashcode, toString +++

public class MyStack<T>{
    public static void main(String[] args) {
        MyStack<Integer> myStack = new MyStack<>();
        myStack.push(123);
        myStack.push(234);
        myStack.push(234);
        myStack.push(11111);
        System.out.println(myStack.toString());

        MyStack<Integer> myStack2 = new MyStack<>();
        myStack2.push(123);
        myStack2.push(234);
        myStack2.push(234);
        myStack2.push(11111);

        System.out.println(myStack.equals(myStack2));
        System.out.println(myStack.hashCode());
        System.out.println(myStack2.hashCode());

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyStack<?> myStack = (MyStack<?>) o;
        return elementCount == myStack.elementCount &&
                capacityIncrement == myStack.capacityIncrement &&
                modCount == myStack.modCount &&
                Arrays.equals(elementData, myStack.elementData);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(elementCount, capacityIncrement, modCount);
        result = 31 * result + Arrays.hashCode(elementData);
        return result;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("This Stack: ");
        for (int i = getElementCount() - 1; i >= 0 ; i--) {
            sb.append(elementData[i].toString());
            if (i != 0){
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    int elementCount;
    int capacityIncrement;
    transient int modCount = 0;
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    Object[] elementData = new Object[elementCount];

    public int getElementCount() {
        return elementCount;
    }

    // push
    public T push(T item) {
        addElement(item);
        return item;
    }

    public synchronized void addElement(T obj) {
        modCount++;
        ensureCapacityHelper(elementCount + 1);
        elementData[elementCount++] = obj;
    }

    private void ensureCapacityHelper(int minCapacity) {
        // overflow-conscious code
        if (minCapacity - elementData.length > 0)
            grow(minCapacity);
    }

    private void grow(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + ((capacityIncrement > 0) ?
                capacityIncrement : oldCapacity);
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    private int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
                Integer.MAX_VALUE :
                MAX_ARRAY_SIZE;
    }

    //pop + size
    public synchronized T pop() {
        T       obj;
        int     len = size();

        obj = peek();
        removeElementAt(len - 1);

        return obj;
    }

    public synchronized int size() {
        return elementCount;
    }

    public synchronized T peek() {
        int     len = size();

        if (len == 0)
            throw new EmptyStackException();
        return elementAt(len - 1);
    }

    public synchronized T elementAt(int index) {
        if (index >= elementCount) {
            throw new ArrayIndexOutOfBoundsException(index + " >= " + elementCount);
        }

        return elementData(index);
    }

    T elementData(int index) {
        return (T) elementData[index];
    }

    public synchronized void removeElementAt(int index) {
        modCount++;
        if (index >= elementCount) {
            throw new ArrayIndexOutOfBoundsException(index + " >= " +
                    elementCount);
        }
        else if (index < 0) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        int j = elementCount - index - 1;
        if (j > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, j);
        }
        elementCount--;
        elementData[elementCount] = null; /* to let gc do its work */
    }


}
