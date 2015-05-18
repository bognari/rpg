package de.tubs.cs.ips.rpg.inventary;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Inventar Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Mai 16, 2015</pre>
 */
public class InventoryTest {

    private static Item[] items = new Item[10];
    private List sorted;
    private List unsorted;


    @BeforeClass
    public static void testSetup() {
        items[0] = new Item("zzz", 1, 1); // 7
        items[1] = new Item("zzz", 1, 2); // 6
        items[2] = new Item("zzz", 1, 0); // 8
        items[3] = new Item("zzz", 0, 0); // 9
        items[4] = new Item("zzz", 2, 0); // 5
        items[5] = new Item("a", 1, 0);   // 3
        items[6] = new Item("a", 1, 1);   // 1 / 2
        items[7] = new Item("a", 5, 2);   // 0
        items[8] = new Item("a", 1, 1);   // 1 / 2
        items[9] = new Item("b", 1, 5);   // 4
    }

    @Before
    public void before() throws Exception {
        sorted = new Inventar();
        unsorted = new Inventar();
        for (Item item : items) {
            sorted.insert(item);
            unsorted.append(item);
        }
    }

    /**
     * Method: isEmpty()
     */
    @Test(timeout = 100)
    public void testIsEmpty() throws Exception {
        assertTrue("check new Invenory", (new Inventar()).isEmpty());
        assertFalse("check sorted", sorted.isEmpty());
        assertFalse("check unsorted", unsorted.isEmpty());
    }

    /**
     * Method: firstItem()
     */
    @Test(timeout = 100)
    public void testFirstItem() throws Exception {
        assertEquals("check sorted", items[7], sorted.firstItem());
        assertEquals("check unsorted", items[0], unsorted.firstItem());
    }

    @Test(timeout = 100, expected = IllegalStateException.class)
    public void testFirstItem_Empty() throws Exception {
        (new Inventar()).firstItem();
    }

    /**
     * Method: getItem(int i)
     */
    @Test(timeout = 100)
    public void testGetItem() throws Exception {
        for (int i = 0; i < items.length; i++) {
            assertEquals("check unsorted", items[i], unsorted.getItem(i));
        }
        assertEquals("check sorted", items[7], sorted.getItem(0));
        assertEquals("check sorted", items[8], sorted.getItem(1));
        assertEquals("check sorted", items[6], sorted.getItem(2));
        assertEquals("check sorted", items[5], sorted.getItem(3));
        assertEquals("check sorted", items[9], sorted.getItem(4));
        assertEquals("check sorted", items[4], sorted.getItem(5));
        assertEquals("check sorted", items[1], sorted.getItem(6));
        assertEquals("check sorted", items[0], sorted.getItem(7));
        assertEquals("check sorted", items[2], sorted.getItem(8));
        assertEquals("check sorted", items[3], sorted.getItem(9));
    }

    @Test(timeout = 100, expected = IndexOutOfBoundsException.class)
    public void testGetItem_Exception_Min() throws Exception {
        sorted.getItem(-1);
    }

    @Test(timeout = 100, expected = IndexOutOfBoundsException.class)
    public void testGetItem_Exception_Max() throws Exception {
        sorted.getItem(10);
    }

    /**
     * Method: length()
     */
    @Test(timeout = 100)
    public void testLength() throws Exception {
        assertEquals("check empty", 0, (new Inventar()).length());
        assertEquals("check sorted", 10, sorted.length());
        assertEquals("check unsorted", 10, unsorted.length());
    }

    /**
     * Method: insert(Item x)
     */
    @Test(timeout = 100)
    public void testInsert() throws Exception {
        assertEquals("check sorted", items[7], sorted.getItem(0));
        assertEquals("check sorted", items[8], sorted.getItem(1));
        assertEquals("check sorted", items[6], sorted.getItem(2));
        assertEquals("check sorted", items[5], sorted.getItem(3));
        assertEquals("check sorted", items[9], sorted.getItem(4));
        assertEquals("check sorted", items[4], sorted.getItem(5));
        assertEquals("check sorted", items[1], sorted.getItem(6));
        assertEquals("check sorted", items[0], sorted.getItem(7));
        assertEquals("check sorted", items[2], sorted.getItem(8));
        assertEquals("check sorted", items[3], sorted.getItem(9));
    }

    /**
     * Method: append(Item x)
     */
    @Test(timeout = 100)
    public void testAppend() throws Exception {
        for (int i = 0; i < items.length; i++) {
            assertEquals("check unsorted", items[i], unsorted.getItem(i));
        }
    }

    /**
     * Method: delete(Item x)
     */
    @Test(timeout = 100)
    public void testDeleteX() throws Exception {
        for (int i = 0; i < 10; i++) {
            sorted.delete(new Item("deadbeef", (int) (Math.random() * 100 + 1), (int) (Math.random() * 100 + 1)));
        }
        assertEquals("check delete", 10, sorted.length());

        for (int i = 0; i < 5; i++) {
            Item item = unsorted.getItem(3);
            assertTrue("check delete", unsorted.isInList(item));
            unsorted.delete(item);
            //assertFalse("check delete", unsorted.isInList(item));
        }

        while (!sorted.isEmpty()) {
            int l = sorted.length();
            sorted.delete(sorted.firstItem());
            assertEquals("check delete", l - 1, sorted.length());
        }
    }

    /**
     * Method: delete()
     */
    @Test(timeout = 100)
    public void testDelete() throws Exception {
        while (!sorted.isEmpty()) {
            Item item = sorted.firstItem();
            int l = sorted.length();
            sorted.delete();
            assertEquals("check delete", l - 1, sorted.length());
            // assertFalse("check delete", sorted.isInList(item));
        }
    }

    /**
     * Method: isInList(Item x)
     */
    @Test(timeout = 100)
    public void testIsInList() throws Exception {
        for (int i = 0; i < sorted.length(); i++) {
            assertTrue("check isInList", sorted.isInList(sorted.getItem(i)));
            assertFalse("check delete", sorted.isInList(new Item("deadbeef", (int) (Math.random() * 100 + 1), (int) (Math.random() * 100 + 1))));
        }

    }
} 

/**
 * @author rose
 * @date 15.05.15.
 */
public class InventoryTest {

    private List<Item> inventory;
    private Item a, b1, b2, c, z;

    @Before
    public void setUp() throws Exception {
        // test items
        a = new Item("a", 12, 23);
        b1 = new Item("b", 23, 42);
        b2 = new Item("b", 3, 1);
        c = new Item("c", 23, 4);
        z = new Item("z", 23, 42);

        inventory = new Inventory<>();
        inventory.append(a);
        inventory.insert(b1);
        inventory.insert(b2);
        inventory.insert(c);
    }

    @Test
    public void testIsEmpty() throws Exception {
        assertEquals(true, new Inventory<>().isEmpty());
    }

    @Test
    public void testLength() throws Exception {
        assertEquals(4, inventory.length());
    }

    @Test
    public void testIsInList() throws Exception {
        assertEquals(true, inventory.isInList(a));
    }

    @Test
    public void testFirstItem() throws Exception {
        assertEquals(a, inventory.firstItem());
    }

    @Test
    public void testInsert() throws Exception {
        int len = inventory.length();
        inventory.insert(z);
        // has to be on the last position
        assertEquals(z, inventory.getItem(len));
    }

    @Test
    public void testAppend() throws Exception {
        int len = inventory.length();
        inventory.insert(z);
        assertEquals(len + 1, inventory.length());
    }

    @Test
    public void testDelete() throws Exception {
        // delete first
        int len = inventory.length();
        inventory.delete();
        assertEquals(len - 1, inventory.length());

        // delete item x
        assertEquals(true, inventory.isInList(b1));
        inventory.delete(b1);
        assertEquals(false, inventory.isInList(b1));
    }

    @Test
    public void testClear() throws Exception {
        assertEquals(false, inventory.isEmpty());
        inventory.clear();
        assertEquals(true, inventory.isEmpty());
    }
}
