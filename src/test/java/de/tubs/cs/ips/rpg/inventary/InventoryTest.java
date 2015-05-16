package de.tubs.cs.ips.rpg.inventary;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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