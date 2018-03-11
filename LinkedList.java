package oblig2;

import sun.awt.image.ImageWatched;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class LinkedList<E> implements IList<E> {
    private Node<E> firstNode; // head reference to first node
    private Node<E> lastNode;  // last reference to last node
    private IList<E> mainList = null;
    private int numberOfEntries;

    public LinkedList() {
        firstNode = null;
        lastNode = null;
        numberOfEntries = 0;
    }

    public LinkedList(E newElement) {
        firstNode = new Node<E>(newElement);
        lastNode = new Node<E>(newElement);
        numberOfEntries = 0;
    }

    public LinkedList(E newElement, IList<E> list) {
        firstNode = new Node<E>(newElement);
        lastNode = new Node<E>(newElement);
        numberOfEntries = 0;
        mainList = list;

    }

//    public void resetDataFields () {
//        numberOfEntries = 0;
//        lastNode = null;
//        firstNode = null;
//
//    }


    // override slik at equals compare om 2 noder er lik, inner equals kaller Node klassen sin equal metode
    @Override
    public boolean equals(Object o)
    {
    if  ( !(o instanceof LinkedList))
    {
        return false;
    }
    Node o1 = firstNode;
    LinkedList list = (LinkedList)o;
    Node o2  = list.firstNode;
    while(o1 != null && o2 != null)
    {
        if (! o1.equals(o2))
        {
            return false;
        }
        o1 = o1.getNextNode();
        o2 = o2.getNextNode();
    }
    return true;
}

    @Override
    public E first() throws NoSuchElementException {
        if (firstNode == null) {
            throw new NoSuchElementException();
        }

        return (E) firstNode.getData();
    }

    @Override
    public IList<E> rest() {
        if (firstNode == null || firstNode.getNextNode() == null )  {
            throw new NoSuchElementException();
        }

        Node nextNode = firstNode.getNextNode();
        LinkedList<E> tempList = new LinkedList<>();

        while (nextNode != null) {
            tempList.add((E) nextNode.getData());
            nextNode = nextNode.getNextNode();
        }

        return tempList;
    }

    @Override
    public boolean add(E elem) {
        Node newNode = new Node(elem);
        if(isEmpty()) {
            firstNode = newNode;
        } else {
            lastNode.setNextNode(newNode);
        }
        lastNode = newNode;
        numberOfEntries++;
        return true;
    }

    @Override
    public boolean put(E elem) {
        Node newNode = new Node(elem);
        if(isEmpty()) {
            firstNode = newNode;
        } else {
            newNode.setNextNode(firstNode);
            firstNode = newNode;
        }
        numberOfEntries++;
        return true;
    }

    @Override
    public E remove() throws NoSuchElementException {
        if (firstNode == null) {
            throw new NoSuchElementException();
        }
        Node temp = firstNode;
        firstNode = firstNode.getNextNode();
        numberOfEntries--;

        return (E) temp.getData();
    }

    @Override
    public boolean remove(Object o) {
        Node currentNode = firstNode;
        Node previousNode = firstNode;
//        Node last = lastNode;

        while (currentNode != null) {
            if (o.equals(firstNode.getData())) {
                firstNode = firstNode.getNextNode();
                numberOfEntries--;
                return true;
            } else if (o.equals(lastNode.getData()) ) {
//                lastNode = null;
//                previousNode.setNextNode(null);
                lastNode = previousNode;
                numberOfEntries--;
                return true;
            } else if (o.equals(currentNode.getData())) {
                previousNode.setNextNode(currentNode.getNextNode());
                numberOfEntries--;
                return true;
            }

            previousNode = currentNode;
            currentNode = currentNode.getNextNode();
        }

        return false;
    }

    @Override
    public boolean contains(Object o) {
        boolean found = false;
        Node currentNode = firstNode;

        while (!found && (currentNode != null)) {
            if ( o.equals(currentNode.getData()) ) {
                found = true;
            } else {
                currentNode = currentNode.getNextNode();
            }
        }
        return found;
    }

    @Override
    public boolean isEmpty() {
        boolean check;
        if (numberOfEntries == 0) {
            // when list is empty, firstNode reference is null
            assert firstNode == null;
            check = true;
        } else {
            assert firstNode != null;
            check = false;
        }
        return check;
    }

    /**
     * - append() goes through every element in list and perform simple operation O(1) for every element, which gives an O(n) runtime
     * - prepend() goes through every element in list and perform simple operation O(1) for every element, which gives an O(n) runtime
     * - concat()
     */

    @Override
    public void append(IList<? extends E> list) {
//        while (list.first() != null) {
//            lastNode.setNextNode(new Node(list.remove()) );
//        }
        Node<E> newNode;

        for (E element : list) {
//            lastNode.setNextNode(new Node<E>(element));
//            newNode = new Node<>(element);
//            lastNode.setNextNode(newNode);
//            newNode = lastNode;
            add(element);
        }

    }

    @Override
    public void prepend(IList<? extends E> list) {
        Node<E> newNode;

        for (E element : list) {
//            newNode = new Node<>(element);
//            newNode.setNextNode(firstNode);
//            newNode = firstNode;
            put(element);
        }

//        while (list.first() != null) {
//            firstNode.setNextNode(new Node(list.remove()) );
//        }
    }

    @Override
    public IList<E> concat(IList<? extends E>... lists) {
        IList<E> temp = new LinkedList<E>();
//        int count = 0;

        // combine all elements in all lists into single list
        for (IList<? extends E> l : lists) {
//            count = count + l.size();

            for (E element : l) {
                temp.add(element);
            }
        }
//        this.numberOfEntries = count;

        return temp;
    }

    @Override
    public void sort(Comparator<? super E> c) {

//        c.compare(firstNode.getData(),firstNode.getNextNode());
    }

    @Override
    public void filter(Predicate<? super E> filter) {
        // take each element and use filter on each againt them
//         filter.test(firstNode.getData());

         while (firstNode != null) {
             boolean res = filter.test(firstNode.getData());
             if (res) {
                 remove(firstNode);
             }
             firstNode = firstNode.getNextNode();
         }


    }

    @Override
    public <U> IList<U> map(Function<? super E, ? extends U> f) {
//        Function<f ,IList<U>> m = LinkedList:: ;

        // for each element in list and make and return a IList<U>
        U result = f.apply(firstNode.getData());

        LinkedList<U> tempList = new LinkedList<>();


        return null;
    }

    @Override
    public <T> T reduce(T t, BiFunction<T, ? super E, T> f) {
//        Function<T>
        return null;
    }

    @Override
    public int size() {
//        int count = 0;
//
//        while (firstNode != null ) {
//            firstNode = firstNode.getNextNode();
//            count++;
//        }

//        for (E e : this) {
//            count++;
//        }
//        return count;
        return numberOfEntries;
    }

    @Override
    public void clear() {
        firstNode = null;
        lastNode = null;
        numberOfEntries = 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new IteratorLinkedList();
    }

    private class IteratorLinkedList implements Iterator<E> {
        private Node nextNode;

        private IteratorLinkedList () {
            nextNode = firstNode;
        }

        public E next() {
            if (hasNext()) {
                Node<E> tempNode = nextNode;
                nextNode = nextNode.getNextNode();
                return tempNode.getData();
            } else {
                throw new NoSuchElementException("Illegal call of next(): " + "iterator is at end of list");
            }
        }

        public boolean hasNext() {
            if (nextNode != null) {
                return true;
            }
            return false;
        }

        public void remove() {
            Node prev = nextNode;
            Node current = nextNode.getNextNode();

            if (nextNode == null) {
                throw new NoSuchElementException();
            } else {
                // set the prev node's next node to skip the coming one and to next node
                prev.setNextNode(current.getNextNode());

            }

        }

    } // end private iterator class


}
