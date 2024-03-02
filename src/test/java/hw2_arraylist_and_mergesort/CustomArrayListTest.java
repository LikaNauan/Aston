package hw2_arraylist_and_mergesort;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomArrayListTest {
    private CustomArrayList<String> testList;

    @BeforeEach
    void init() {
        testList = new CustomArrayList<>();
        testList.add("apple");
        testList.add("banana");
        testList.add("cherry");
    }

    @Test
    void testAdd() {
        testList.add("date");
        assertEquals("date", testList.get(3));
        assertEquals(4, testList.size());
    }
    @Test
    void testAddAtIndex() {
        testList.add(1, "date");
        assertEquals("date", testList.get(1));
        assertEquals("banana", testList.get(2));
        assertEquals(4, testList.size());
    }

    @Test
    void testAddAll() {
        List<String> additionalList = List.of("date", "fig");
        assertTrue(testList.addAll(additionalList));
        assertEquals("date", testList.get(3));
        assertEquals("fig", testList.get(4));
        assertEquals(5, testList.size());
    }
    @Test
    void testClear() {
        testList.clear();
        assertEquals(0, testList.size());
        assertTrue(testList.isEmpty());
    }

    @Test
    void testSort() {
        testList.sort(Comparator.naturalOrder());
        assertEquals("apple", testList.get(0));
        assertEquals("banana", testList.get(1));
        assertEquals("cherry", testList.get(2));
    }

    @Test
    void testRemoveWithExistingElement() {
        assertTrue(testList.remove("banana"));
        assertEquals(2, testList.size());
        assertEquals("cherry", testList.get(1));
    }

    @Test
    void testRemoveWithNonExistingElement() {
        assertFalse(testList.remove("orange"));
        assertEquals(3, testList.size());
        assertEquals("apple", testList.get(0));
        assertEquals("banana", testList.get(1));
        assertEquals("cherry", testList.get(2));
    }

    @Test
    void testRemoveValidIndex() {
        String removed = testList.remove(1);
        assertEquals(2, testList.size());
        assertEquals("banana", removed);
        assertEquals("apple", testList.get(0));
        assertEquals("cherry", testList.get(1));
    }

    @Test
    void testRemoveInvalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> testList.remove(3));
    }
}