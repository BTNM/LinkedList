package oblig2;
//package no.uib.info233;
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
    private IList<E> mList = null;
    private int numberOfEntries;

    public LinkedList() {
        firstNode = null;
        lastNode = null;
        numberOfEntries = 0;
    }

    public LinkedList(E newElement) {
        firstNode = new Node<E>(newElement);
        lastNode = firstNode;
        numberOfEntries = 1;
    }

    public LinkedList(E newElement, IList<E> list) {
        firstNode = new Node<E>(newElement);
        lastNode = firstNode;
        numberOfEntries = 1;
        append(list);

    }

    // override slik at equals compare om 2 noder er lik, inner equals kaller Node klassen sin equal metode
    @Override
    public boolean equals(Object o) {
        if  ( !(o instanceof LinkedList)) {
            return false;
        }

        Node o1 = firstNode;
        LinkedList list = (LinkedList)o;
        Node o2  = list.firstNode;

        while(o1 != null && o2 != null) {
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

        while (currentNode != null) {
            if (o.equals(firstNode.getData())) {
                firstNode = firstNode.getNextNode();
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

    @Override
    public void append(IList<? extends E> list) {
        for (E element : list) {
            add(element);
        }

    }

    @Override
    public void prepend(IList<? extends E> list) {
//        for (E element : list) {
//            put(element);
//        }

        LinkedList<E> tempList = new LinkedList<>();
        for (E element : list) {
            tempList.put(element);
        }
        for (E element : tempList) {
            put(element);
        }


    }

    @Override
    public IList<E> concat(IList<? extends E>... lists) {
        IList<E> temp = new LinkedList<E>();

        for (IList<? extends E> l : lists) {
            for (E element : l) {
                temp.add(element);
            }
        }

        return temp;
    }

    @Override
    public void sort(Comparator<? super E> c) {
//        c.compare(firstNode.getData(),firstNode.getNextNode());

        if (size() > 1) {
            assert firstNode != null;

            // split into unsorted and sorted
            Node unsorted = firstNode.getNextNode();
            assert unsorted != null;
            firstNode.setNextNode(null);

            while (unsorted != null) {
                Node insertNode = unsorted;
                unsorted = unsorted.getNextNode();
                insertInRightPos(insertNode, c);
            }

        } // end if

    }

    private void insertInRightPos (Node insertedNode, Comparator<? super E> c) {
        E data = (E) insertedNode.getData();
        Node currentNode = firstNode;
        Node previousNode = null;

        // find insertion position
        while ((currentNode != null) && (c.compare(data, (E) currentNode.getData()) > 0)) { // some problems with c
            previousNode = currentNode;
            currentNode = currentNode.getNextNode();
        }

        // insert into chain
        if (previousNode != null) {
            previousNode.setNextNode(insertedNode);
            insertedNode.setNextNode(currentNode);
        } else { // insert at the beginning
            insertedNode.setNextNode(firstNode);
            firstNode = insertedNode;
        }

    }

    @Override
    public void filter(Predicate<? super E> filter) {
        // take each element and use filter on each againt them
        Node<E> tempNode = firstNode;
         while (tempNode != null) {
             boolean res = filter.test(tempNode.getData());
             if (res) {
                 remove(tempNode);
             }
             tempNode = tempNode.getNextNode();
         }

    }

    @Override
    public <U> IList<U> map(Function<? super E, ? extends U> f) {
       LinkedList<U> tempList = new LinkedList<>();

        // for each element in list apply f and return a IList<U>
        while (firstNode != null) {
            U result = f.apply(firstNode.getData());
            if (result != null) {
                tempList.add(result);
            }
            firstNode = firstNode.getNextNode();

        }

        return tempList;
    }

    @Override
    public <T> T reduce(T t, BiFunction<T, ? super E, T> f) {
//        Save t to a sum variable,
//        then use f function to combine all the values,
//        Then return the accumulated sum variable
        Node<E> tempNode = firstNode;
        T result = null;
        while (tempNode != null) {
            t = f.apply(t, tempNode.getData());


            tempNode = tempNode.getNextNode();
        }

        return t;
    }

    @Override
    public int size() {
        return numberOfEntries;
    }

    @Override
    public void clear() {
        firstNode = null;
        lastNode = null;
        mList = null;
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
