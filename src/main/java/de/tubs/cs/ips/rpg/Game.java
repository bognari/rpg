package de.tubs.cs.ips.rpg;

import de.tubs.cs.ips.rpg.inventary.Inventory;
import de.tubs.cs.ips.rpg.inventary.Item;

/**
 * @author rose
 * @date 15.05.15.
 */
public final class Game {

    public static void main(final String[] args) {

        Inventory<Item> inv = new Inventory<>();
        inv.append(new Item("foo", 12, 23));
        inv.insert(new Item("bar", 23, 42));
        inv.insert(new Item("bar", 1, 1));
        inv.insert(new Item("oof", 2, 203));
        System.out.println(inv);
    }
}
