import java.util.*;

public class MyLinkedList<T>{

//     add(T, int index), remove(index), get(int index) +++
//    size, equals, hashcode, toString  ++++


    public static void main(String[] args) {
        MyLinkedList<String> numbers = new MyLinkedList<>();
        numbers.add(0,"1");
        numbers.add(1,"2");
        numbers.add(2,"3");

        MyLinkedList<String> numbers2 = new MyLinkedList<>();
        numbers2.add(0,"1");
        numbers2.add(1,"2");



        System.out.println(numbers.equals(numbers2));
        System.out.println(numbers.toString());

        numbers.remove(2);
        numbers2.remove(1);

        System.out.println(numbers.get(1));
        System.out.println(numbers2.get(0));
    }


    transient int size = 0;
    transient MyNode<T> first;
    transient MyNode<T> last;
    protected transient int modCount = 0;




    public int size(){
        return size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("This collection: [");
        for (int i = 0; i < this.size(); i++){
            sb.append(this.get(i));
            if (i != this.size - 1){
                sb.append(";");
            }
        }
        sb.append(']');

        return sb.toString();
    }

    public boolean equals(MyLinkedList myLinkedList) {
        if (this == myLinkedList) return true;
        if (myLinkedList == null || getClass() != myLinkedList.getClass()) return false;

        return size() == myLinkedList.size() &&
                modCount == myLinkedList.modCount && this.checkMyLinkedListsForEquals(myLinkedList);
    }

    public boolean checkMyLinkedListsForEquals(MyLinkedList myLinkedList){
      for (int i = 0; i < this.size; i++){
          if (!this.get(i).equals(myLinkedList.get(i))){
              return false;
          }
      }
      return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, first, last, modCount);
    }

//    private void linkFirst(T t) {
//        final MyNode<T> f = first;
//        final MyNode<T> newMyNode = new MyNode<>(null, t, f);
//        first = newMyNode;
//        if (f == null)
//            last = newMyNode;
//        else
//            f.prev = newMyNode;
//        size++;
//        modCount++;
//    }

    private void linkLast(T t) {
        final MyNode<T> l = last;
        final MyNode<T> newMyNode = new MyNode<>(l, t, null);
        last = newMyNode;
        if (l == null)
            first = newMyNode;
        else
            l.next = newMyNode;
        size++;
        modCount++;
    }

    void linkBefore(T t, MyNode<T> succ) {
        // assert succ != null;
        final MyNode<T> pred = succ.prev;
        final MyNode<T> newMyNode = new MyNode<>(pred, t, succ);
        succ.prev = newMyNode;
        if (pred == null)
            first = newMyNode;
        else
            pred.next = newMyNode;
        size++;
        modCount++;
    }

    T unlink(MyNode<T> x) {
        // assert x != null;
        final T element = x.item;
        final MyNode<T> next = x.next;
        final MyNode<T> prev = x.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.item = null;
        size--;
        modCount++;
        return element;
    }


    //  add method
    public void add(int index, T element) {
        checkPositionIndex(index);

        if (index == size)
            linkLast(element);
        else
            linkBefore(element, node(index));
    }

    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index))
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index))
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    MyNode<T> node(int index) {
        // assert isElementIndex(index);

        if (index < (size >> 1)) {
            MyNode<T> x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else {
            MyNode<T> x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }


    //    remove method
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(node(index));
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    // get method
    public T get(int index) {
        checkElementIndex(index);
        return node(index).item;
    }



    private static class MyNode<T> {
        T item;
        MyNode<T> next;
        MyNode<T> prev;

        MyNode(MyNode<T> prev, T element, MyNode<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MyNode<T> myNode = (MyNode<T>) o;
            return item == myNode.item && next == myNode.next && prev == myNode.prev;
        }

        @Override
        public int hashCode() {
            return Objects.hash(item, next, prev);
        }
    }
}
