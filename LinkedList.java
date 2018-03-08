package oblig2;

import sun.awt.image.ImageWatched;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class LinkedList<E> implements IList<E> {
    private Node firstNode; // head reference to first node
    private Node lastNode;  // last reference to last node
    //    private Node restNode;  // tail reference to rest of list, without the first element
    private int numberOfEntries;

    public LinkedList() {
        firstNode = null;
        lastNode = null;
        numberOfEntries = 0;
    }

    public LinkedList(E newElement) {
        firstNode = new Node(newElement);
        lastNode = new Node(newElement);
        numberOfEntries = 0;
    }

    public LinkedList(E newElement, IList<E> list) {
        firstNode = new Node(newElement);
        lastNode = new Node(newElement);
        numberOfEntries = 0;
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
        // SOME PROBLEMS REGARDING RETURN NODE AS ILIST
        return (IList<E>) nextNode;
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

        return (E) temp.getData();
    }

    @Override
    public boolean remove(Object o) {
        Node currentNode = firstNode;
        Node previousNode = firstNode;

        while (currentNode != null) {
            if (o.equals(firstNode.getData())) {
                firstNode = firstNode.getNextNode();
                return true;
            } else if (o.equals(currentNode)) {
                previousNode.setNextNode(currentNode.getNextNode());
                return true;
            }
            previousNode = currentNode;
            currentNode = currentNode.getNextNode();
        }

        return false;
    }



    private Node getNodeAt(int position) {
        assert (firstNode != null) && (1 <= position) && (position <= numberOfEntries);
        Node currentNode = firstNode;

        // traversing the chain to locate given node (fin if pos == 1)
        for (int i = 0; i < position; i ++) {
            currentNode = currentNode.getNextNode();
        }
        assert currentNode != null;

        return currentNode;
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

    @Override
    public void append(IList<? extends E> list) {
        while (list.first() != null) {
            lastNode.setNextNode(new Node(list.remove()) );
        }

    }

    @Override
    public void prepend(IList<? extends E> list) {
        while (list.first() != null) {
            firstNode.setNextNode(new Node(list.remove()) );
        }

    }

    @Override
    public IList<E> concat(IList<? extends E>... lists) {
        return null;
    }

//    @Override
//    public void append(IList<? super E> list) { // extends istedenfor super
////        put(IList.first());
////        list.put(first())
//    }



    @Override
    public void sort(Comparator<? super E> c) {

    }

    @Override
    public void filter(Predicate<? super E> filter) {

        // take each element and use filter on each againt them
//         filter.test()
    }

    @Override
    public <U> IList<U> map(Function<? super E, ? extends U> f) {
        return null;
    }

    @Override
    public <T> T reduce(T t, BiFunction<T, ? super E, T> f) {
        return null;
    }

    @Override
    public int size() {
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
                Node tempNode = nextNode;
                nextNode = nextNode.getNextNode();
                return (E) tempNode;
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
