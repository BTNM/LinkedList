package oblig2;
//package no.uib.info233;
//org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.function.Executable;

class ListTest {

    private LinkedList<Integer> mainList;

    @BeforeEach
    void setup() {
        mainList = new LinkedList<>();

    }

    void setupOneElement() {
        mainList.add(1);
    }

    void setupMultipleElement() {
        mainList.add(1);
        mainList.add(2);
        mainList.add(3);
        mainList.add(4);
    }

    @Test
    void emptyFirst() {
        // test exception throws when call first() on empty list
        assertThrows(NoSuchElementException.class, () -> mainList.first());
    }
    @Test
    void emptyRest() {
        //check when list kalls for non-existent element on empty list
        assertThrows(NoSuchElementException.class, () -> mainList.rest());
    }
    @Test
    void emptyAdd() {
        mainList.add(6);
        assertEquals(1, mainList.size());
//        mainList.remove();

        // check if first element in list are correct
        assertEquals (Integer.valueOf(6), mainList.first());

    }
    @Test
    void emptyPut() {
        mainList.put(6);
        assertEquals(1, mainList.size());
        // check if first element in list are correct
        assertEquals (Integer.valueOf(6),mainList.first());
    }
    @Test
    void emptyRemove(){
        // check if throws exception when trting to remove non-existent element
        assertThrows(NoSuchElementException.class, () -> mainList.remove());
    }

    @Test
    void OnlyOneElementFirst () {
        setupOneElement(); // mainlist have only element {1}

        // check if first method returns the right values
        assertEquals(Integer.valueOf(1),mainList.first() );
        mainList.put(7);
        assertEquals(Integer.valueOf(7),mainList.first() );
    }
    @Test
    void OnlyOneElementRest() {
        setupOneElement();
        assertThrows(NoSuchElementException.class, () -> mainList.rest());
    }
    @Test
    void OnlyOneElementAdd() {
        setupOneElement();

        // check if add method place value in right place
        mainList.add(7);
        assertEquals(2, mainList.size());

        assertTrue(7 != mainList.first());

        // remove first element and check element is put at
        mainList.remove();
        assertEquals(Integer.valueOf(7),mainList.first());
    }
    @Test
    void OnlyOneElementPut() {
        setupOneElement();

        // check if put method place value in right place
        mainList.put(7);
        assertEquals(2,mainList.size());

        assertTrue(7 == mainList.first());
    }
    @Test
    void OnlyOneElementRemove() {
        //check if remove method removes right value
        setupOneElement();
        assertEquals(Integer.valueOf(1),mainList.remove());
    }

    @Test
    void multipleElementsFirst () {
        setupMultipleElement();

        // check if first() returns value from right place
        mainList.put(8);
        assertEquals(Integer.valueOf(8), mainList.first());
        mainList.remove();
        assertEquals(Integer.valueOf(1),mainList.first() );
    }
    @Test
    void multipleElementsRest () {
        setupMultipleElement();
//        assertEquals(mainList.size()-1, mainList.rest());

        IList<Integer> rest = mainList.rest();
        mainList.remove();
        assertEquals(rest, mainList);
        // check innhold mot innhold for hver node i en liste
    }
    @Test
    void multipleElementsAdd () {
        setupMultipleElement();

        mainList.add(7);
        assertTrue(7 != mainList.first());
        mainList.add(8);

        // remove first element and check element is put at right place
        mainList.remove();
        assertEquals(Integer.valueOf(2),mainList.first());



    }
    @Test
    void multipleElementsPut () {
        setupMultipleElement();

        mainList.put(7);
        assertEquals(Integer.valueOf(7),mainList.first());
        mainList.put(8);
        assertEquals(Integer.valueOf(8),mainList.first());
    }
    @Test
    void multipleElementsRemove () {
        setupMultipleElement();
        assertEquals(Integer.valueOf(1),mainList.first());
        assertEquals(Integer.valueOf(2),mainList.rest());
        mainList.remove();
        mainList.remove();
        mainList.remove();
        assertEquals(Integer.valueOf(4),mainList.first());
    }

    @Test
    void removeSelectObjectIsFirstElement() {
        int t = 4;
        mainList.put(t);
        //check remove(T o) for one element
        assertTrue(mainList.remove(t) );
        assertFalse(mainList.contains(t));
        assertThrows(NoSuchElementException.class, () -> mainList.remove());

    }
    @Test
    void removeSelectMiddleElement() {
        setupMultipleElement();

//        assertTrue(mainList.remove(2) );
//        assertFalse(mainList.remove(6));

        LinkedList<Integer> testList = new LinkedList<>();
        testList.put(4);
        testList.put(3);
        testList.put(2);
        testList.put(1);

//        assertEquals(testList,mainList);
        // check list equals
        while(!testList.isEmpty()){
//            if (testList.isEmpty() ) {
//                assertThrows(NoSuchElementException.class, () -> testList.remove());
//            } else if ( mainList.isEmpty()) {
//                assertThrows(NoSuchElementException.class, () -> mainList.remove());
//            }

            // equals kaller klassens sin equal metode(int i dette tilfelle)
            assertEquals(testList.remove(),mainList.remove());
        }

    }
    @Test
    void removeSelectLastElement() {
        setupMultipleElement();

        // removes last element in mainlist
        mainList.remove(4);
        assertFalse(mainList.contains(4));
    }

    // maybe write some more tests
    @Test
    void containHowManyElement() {
        setupMultipleElement();
        int t1 = 7;
        int t2 = 8;
        mainList.put(t1);
        mainList.add(t2);
        assertTrue(mainList.contains(3));
        assertTrue(mainList.contains(1));
        assertTrue(mainList.contains(t1));
        assertTrue(mainList.contains(t2));
    }

    @Test
    void notContainElement() {
        mainList.put(9);
        assertFalse(mainList.contains(1));
        assertTrue(mainList.contains(9));
    }


    @Test
    void listIsEmpty() {
        assertTrue(mainList.isEmpty());
        mainList.put(22);
        assertFalse(mainList.isEmpty());
    }

    @Test
    void emptyListAfterRemove() {
        setupMultipleElement();
        mainList.remove();
        mainList.remove();
        mainList.remove();
        mainList.remove();
        assertFalse(mainList.contains(4));
    }

    @Test
    void listSize() {
        setupMultipleElement();
        assertEquals(4,mainList.size());
        mainList.put(5);
        mainList.add(6);
        assertEquals(6,mainList.size());
    }

    @Test
    void sizeAfterDifferentMethods() {
        setupMultipleElement();
        mainList.remove();
        mainList.add(5);
        mainList.put(11);
        assertEquals(5,mainList.size());

        mainList.remove(5);
        assertEquals(4,mainList.size());
    }


    @Test
    void appendList () {
        setupMultipleElement();

        LinkedList testList = new LinkedList();
        testList.put(44);
        testList.put(33);
        testList.put(22);
        testList.put(11);

        mainList.append(testList);

        assertEquals(8,mainList.size());
    }

    @Test
    void prependList() {
        setupMultipleElement();

        LinkedList testList = new LinkedList();
        testList.put(44);
        testList.put(33);
        testList.put(22);
        testList.put(11);

        mainList.prepend(testList);

        assertEquals(8,mainList.size());
    }

    @Test
    void iteratorThroughSimpleList () {
        setupMultipleElement();

        // check if iterator works and takes out/iterate through each element
        mainList.add(5);

        LinkedList<Integer> testList = new LinkedList<>();
        testList.put(5);
        testList.put(4);
        testList.put(3);
        testList.put(2);
        testList.put(1);

        Iterator<Integer> listIterator = mainList.iterator();
        while (listIterator.hasNext() ) {
//            assertEquals(testList.remove(),listIterator.next().getData() );
        }

    }

    @Test
    void sortElementList () {
        mainList.add(3);
        mainList.add(1);
        mainList.add(4);
        mainList.add(1);
        mainList.add(2);
        mainList.add(22);
        mainList.add(6);



    }

    @Test
    void filterThroughSimpleList () {

    }
    @Test
    void mapThroughSimpleList () {

    }
    @Test
    void reduceThoughSimpeList() {

    }



    @Test
    void oppg8_sortIntegers() {
        // Se oppgave 8
        IList<Integer> list = new LinkedList<>();
        List<Integer> values = Arrays.asList(3, 8, 4, 7, 10, 6, 1, 2, 9, 5);

        for (Integer value : values) {
            list.add(value);
        }
        list.sort(Comparator.comparingInt(x -> x));


        int n = list.remove();
        while (list.size() > 0) {
            int m = list.remove();
            if (n > m) {
                fail("Integer list is not sorted.");
            }
            n = m;
        }

    }
    @Test
    void oppg8_sortStrings() {
        // Se oppgave 8
        IList<String> list = new LinkedList<>();
        List<String> values = Arrays.asList(
                "g", "f", "a", "c", "b", "d", "e", "i", "j", "h"
        );
        for (String value : values) {
            list.add(value);
        }

        list.sort(Comparator.naturalOrder());

        String n = list.remove();
        while (list.size() > 0) {
            String m = list.remove();
            if (n.compareTo(m) > 0) {
                fail("String list is not sorted.");
            }

        }
    }

    @Test
    void oppg9_filter() {
        // Se oppgave 9
        List<Integer> values = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        IList<Integer> list = new LinkedList<>();
        for (Integer value : values) {
            list.add(value);
        }

        list.filter(n -> n % 2 == 1);

        int n = list.remove();
        while(list.size() > 0) {
            if (n % 2 == 1) {
                fail("List contains filtered out elements.");
            }
            n = list.remove();

        }

    }

    @Test
    void oppg10_map() {
        // Se oppgave 10
        List<String> values = Arrays.asList("1", "2", "3", "4", "5");

        IList<String> list = new LinkedList<>();
        for (String value : values) {
            list.add(value);
        }

        IList<Integer> result = list.map(Integer::parseInt);

        List<Integer> target = Arrays.asList(1, 2, 3, 4, 5);


        for (int t : target) {
            if (result.remove() != t) {
                fail("Result of map gives the wrong value.");
            }
        }
    }

    @Test
    void oppg11_reduceInts() {
        // Se oppgave 11
        List<Integer> values = Arrays.asList(1, 2, 3, 4, 5);

        IList<Integer> list = new LinkedList<>();
        for (Integer value : values) {
            list.add(value);
        }

        int result = list.reduce(0, Integer::sum);

        assertEquals(result, 5*((1 + 5)/2));
    }

    @Test
    void oppg11_reduceStrings() {
        List<String> values = Arrays.asList("e", "s", "t");
        IList<String> list = new LinkedList<>();
        for (String s : values) {
            list.add(s);
        }

        String result = list.reduce("t", (acc, s) -> acc + s);

        assertEquals(result, "test");
    }

    @Test
    void ex1_FastSort() {
        // Se ekstraoppgave 1
        Random r = new Random();
        IList<Integer> list = new LinkedList<>();
        for (int n = 0; n < 1000000; n++) {
            list.add(r.nextInt());
        }

        assertTimeout(Duration.ofSeconds(2), () -> list.sort(Integer::compare));

        int n = list.remove();
        for(int m = list.remove(); !list.isEmpty(); n = m) {
            if (n > m) {
                fail("List is not sorted");
            }
        }


    }
}
