package oblig2;

public class Node<T> {
    private T data;     //entry in queue
    private Node next;  // link to next node

    public Node(T data) {
        this(data,null);
    }

    public Node(T data, Node nextNode) {
        this.data= data;
        next = nextNode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node getNextNode() {
        return next;
    }

    public void setNextNode(Node next) {
        this.next = next;
    }

    // equal metode for å sjekke om to noder er like, ved å sjekke innhold/data field
    public boolean equals(Object o) {
        if  ( !(o instanceof Node)) {
            return false;
        }

        if ( ! ( ((Node<T>) o).getData() .equals(data))) {
            return false;
        }

        return true;

    }

}

