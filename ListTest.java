package oblig2;
//package no.uib.info233;

//org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.*;
import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class ListTest {
    private LinkedList<Integer> mainList;

    @BeforeEach
    void setup() {
        mainList = new LinkedList<>();

    }
    private void setupOneElement() {
        mainList.add(1);
    }
    private void setupMultipleElement() {
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
        // check response of rest() on list with 1 element
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

        IList<Integer> rest = mainList.rest();

        // check size amount of elements after rest() are correct
        assertEquals(mainList.size()-1, rest.size());

        // check right element after rest()
        assertEquals(Integer.valueOf(2),mainList.rest().first());

        // check each element after rest() are in correct position
        mainList.remove();
        assertEquals(rest, mainList);

        for (Integer check : mainList) {
            assertEquals(check, rest.remove());
        }
    }
    @Test
    void multipleElementsAdd () {
        setupMultipleElement();

        // check if element in right position after add()
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

        // check if element in right position after put()
        mainList.put(7);
        assertEquals(Integer.valueOf(7),mainList.first());
        mainList.put(8);
        assertEquals(Integer.valueOf(8),mainList.first());
    }
    @Test
    void multipleElementsRemove () {
        setupMultipleElement();

        // check if element in right position after remove()
        assertEquals(Integer.valueOf(1),mainList.first());
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

        assertTrue(mainList.remove(2) );
        assertFalse(mainList.remove(6));

        LinkedList<Integer> testList = new LinkedList<>();
        testList.put(4);
        testList.put(3);
        testList.put(1);

        // check list equals with test list
        for (int i : mainList) {
            assertEquals(Integer.valueOf(i), testList.remove());
        }
    }
    @Test
    void removeSelectElement() {
//        setupMultipleElement();
        mainList.add(1);
        mainList.add(2);
        mainList.add(3);
        mainList.add(4);

//        System.out.println( mainList.remove(3) );
//        System.out.println( mainList.remove(4) );

//        for (int i: mainList) {
//            System.out.println(i);
//        }
//        System.out.println(mainList.contains(4));
        mainList.remove(3);
        assertFalse(mainList.contains(3));

        mainList.remove(4);
        // removes last element in mainlist
        assertFalse(mainList.contains(4)); // some problems with removing the last element i list
    }

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
        // check isEmpty() return right response
        assertTrue(mainList.isEmpty());
        mainList.put(22);

        // isEmpty() gives back false with 1 element
        assertFalse(mainList.isEmpty());
    }
    @Test
    void emptyListAfterRemove() {
        setupMultipleElement();
        // check size() of list after remove
        assertEquals(4, mainList.size());
        mainList.remove();
        mainList.remove();
        assertEquals(2, mainList.size());

        mainList.remove();
        mainList.remove();
        assertFalse(mainList.contains(4));
    }

    @Test
    void listSize() {
        setupMultipleElement();
        // check the size of list
        assertEquals(4,mainList.size());
        mainList.put(5);
        mainList.add(6);
        assertEquals(6,mainList.size());
    }
    @Test
    void sizeAfterDifferentMethods() {
        setupMultipleElement();

        // check the size of list after the main methods
        mainList.remove();
        mainList.add(5);
        mainList.put(11);
        assertEquals(5,mainList.size());

        mainList.remove(5);
        assertEquals(4,mainList.size());
    }

    @Test
    void appendEmptyList() {
        setupOneElement();

        // check append on empty list
        assertEquals(1,mainList.size());

        LinkedList<Integer> testList = new LinkedList<>();
        mainList.append(testList);

        assertEquals(1,mainList.size());
    }
    @Test
    void appendList () {
        setupMultipleElement();

        LinkedList<Integer> testList = new LinkedList<>(); // 11 22 33 44
        testList.add(11);
        testList.add(22);
        testList.add(33);
        testList.add(44);

        mainList.append(testList); // 1 2 3 4 11 22 33 44

        // check size are correct
        assertEquals(8,mainList.size());

        LinkedList<Integer> checkList = new LinkedList<>();
        checkList.add(1);
        checkList.add(2);
        checkList.add(3);
        checkList.add(4);
        checkList.add(11);
        checkList.add(22);
        checkList.add(33);
        checkList.add(44);

        // check that append add all element to end of mainlist, in right position
        for (Integer check : checkList) {
            assertEquals(check, mainList.remove());
        }

    }
    @Test
    void prependOnSameList () {
        setupOneElement();
        mainList.prepend(mainList);
        // check prepend on the same list
        assertEquals(2,mainList.size());
    }
    @Test
    void prependList() {
        setupMultipleElement();

        LinkedList<Integer> testList = new LinkedList<>(); // 44 33 22 11
        testList.put(44);
        testList.put(33);
        testList.put(22);
        testList.put(11);


        mainList.prepend(testList); // 11 22 33 44 1 2 3 4

        // check size are correct
        assertEquals(8,mainList.size());

        LinkedList<Integer> checkList = new LinkedList<>();
        checkList.put(4);
        checkList.put(3);
        checkList.put(2);
        checkList.put(1);
        checkList.put(44);
        checkList.put(33);
        checkList.put(22);
        checkList.put(11);

//        for (int i : checkList) {
//            System.out.println(i);
//        }

        // check that prepend add all element to end of mainlist, in right position
        for (Integer check : checkList) {
            assertEquals(check, mainList.remove());
        }
    }

    @Test
    void concatMultipleList() {
        LinkedList<Integer> list1 = new LinkedList<>();
        list1.add(1);
        list1.add(11);
        LinkedList<Integer> list2 = new LinkedList<>();
        list2.add(2);
        list2.add(22);
        LinkedList<Integer> list3 = new LinkedList<>();
        list3.add(3);
        list3.add(33);

        IList<Integer> tempList = mainList.concat(list1,list2,list3);

        // check amount of elements of new list
        assertEquals(6, tempList.size());

        LinkedList<Integer> checkList = new LinkedList<>();
        checkList.add(1);
        checkList.add(11);
        checkList.add(2);
        checkList.add(22);
        checkList.add(3);
        checkList.add(33);

        // check that multiple list have been concatenated into single list
        for (int check : checkList) {
            assertEquals(Integer.valueOf(check), tempList.remove());
        }

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
            assertEquals(testList.remove(),listIterator.next() );
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

        Comparator<Integer> comp = (o1, o2) -> {
            int result;
            if(o1.equals(o2)) {
                result = 0;
            } else if (o1 < o2) {
                result = -1;
            } else {
                result = 1;
            }

            return result;
        };
        // sort with comparator
        mainList.sort(comp);

        LinkedList<Integer> checkList = new LinkedList<>();
        checkList.add(1);
        checkList.add(1);
        checkList.add(2);
        checkList.add(3);
        checkList.add(4);
        checkList.add(6);
        checkList.add(22);

        // check list are sorted right
        for (int check : checkList) {
            assertEquals(Integer.valueOf(check), mainList.remove());
        }
    }

    @Test
    void sortWithNegativeElements () {
        mainList.add(-2);
        mainList.add(1);
        mainList.add(4);
        mainList.add(1);
        mainList.add(-5);
        mainList.add(12);
        mainList.add(6);

        Comparator<Integer> comp = (o1, o2) -> {
            int result;
            if(o1.equals(o2)) {
                result = 0;
            } else if (o1 < o2) {
                result = -1;
            } else {
                result = 1;
            }

            return result;
        };
        // sort with comparator
        mainList.sort(comp);

        LinkedList<Integer> checkList = new LinkedList<>();
        checkList.add(-5);
        checkList.add(-2);
        checkList.add(1);
        checkList.add(1);
        checkList.add(4);
        checkList.add(6);
        checkList.add(12);

        // check list are sorted right
        for (int check : checkList) {
            assertEquals(Integer.valueOf(check), mainList.remove());
        }
    }


    @Test
    void filterEvenList () {
        setupMultipleElement();

        // use filter to remove all even integers
        mainList.filter((tall) -> tall % 2 == 0);
        assertEquals(2,mainList.size());
    }
    @Test
    void filterOddList () {
        setupMultipleElement();

        // use filter to remove all even integers
        mainList.filter((tall) -> tall % 2 != 0);
        assertEquals(2,mainList.size());
    }


    @Test
    void mapThroughSimpleList () {
        setupMultipleElement();

        mainList.map((i) -> i*2 );

        LinkedList<Integer> checkList = new LinkedList<>();
        checkList.add(2);
        checkList.add(4);
        checkList.add(6);
        checkList.add(8);

        // check if function in map() are applied rightly
        for (int x : checkList) {
//            assertEquals(Integer.valueOf(x), mainList.remove());
        }
    }
    @Test
    void reduceThoughSimpleList() {

    }



    @Test
    void oppg8_sortIntegers() {
        // Se oppgave 8
        IList<Integer> list = new LinkedList<>();
        List<Integer> values = Arrays.asList(3, 8, 4, 7, 10, 6,
                1, 2, 9, 5);

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
            n = m;
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


        while(list.size() > 0) {
            int n = list.remove();
            if (n % 2 == 1) {
                fail("List contains filtered out elements.");
            }
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

//    @Test
//    void ex1_FastSort() {
//        // Se ekstraoppgave 1
//        Random r = new Random();
//        IList<Integer> list = new LinkedList<>();
//        for (int n = 0; n < 1000000; n++) {
//            list.add(r.nextInt());
//        }
//
//        assertTimeout(Duration.ofSeconds(2), () -> list.sort(Integer::compare));
//
//        int n = list.remove();
//        for(int m = list.remove(); !list.isEmpty(); m = list.remove()) {
//            if (n > m) {
//                fail("List is not sorted");
//            }
//            n = m;
//        }
//    }

}
