package de.tubs.cs.ips.rpg.inventary;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author rose
 * @date 15.05.15.
 */
public class InventaryTest {

    private List<Item> inventary;

    @Before
    public void setUp() throws Exception {
        inventary = new Inventary<>();
    }

    @Test
    public void testIsEmpty() throws Exception {
        assertEquals(true, inventary.isEmpty());
    }

    @Test
    public void testLength() throws Exception {
        assertEquals(0, inventary.length());
    }

    @Test
    public void testIsInList() throws Exception {

    }

    @Test
    public void testFirstItem() throws Exception {

    }

    @Test
    public void testGetItem() throws Exception {

    }

    @Test
    public void testInsert() throws Exception {

    }

    @Test
    public void testAppend() throws Exception {

    }

    @Test
    public void testDelete() throws Exception {

    }

    @Test
    public void testDelete1() throws Exception {

    }
}