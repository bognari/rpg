package de.tubs.cs.ips.rpg.inventary;

import java.util.Random;

/**
 * @author rose
 * @date 15.05.15.
 */
public class Item implements Comparable<Item> {

    private static final String[] NAMES = {"foo", "bar", "baz"};
    private static final int MAX_VALUE = 100, MIN_VALUE = 1, MAX_WEIGHT = 100, MIN_WEIGHT = 100;
    private final String name;
    private final int value, weight;

    public Item() {
        final Random rand = new Random();
        this.name = NAMES[rand.nextInt(NAMES.length)];
        this.value = MIN_VALUE + rand.nextInt(MAX_VALUE + MIN_VALUE);
        this.weight = MIN_WEIGHT + rand.nextInt(MAX_WEIGHT + MIN_WEIGHT);
    }

    public Item(final String name, int value, int weight) {
        this.name = name;
        this.value = value;
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;
        return name.equals(item.name) && value == item.value && weight == item.weight;

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + value;
        result = 31 * result + weight;
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s {value=%d, weight=%d}", name, value, weight);
    }

    @Override
    public int compareTo(final Item item) {
        if (!name.equals(item.name))
            return name.compareTo(item.name);
        else if (value != item.value)
            return Integer.compare(value, item.value);
        return Integer.compare(weight, item.weight);
    }


}
