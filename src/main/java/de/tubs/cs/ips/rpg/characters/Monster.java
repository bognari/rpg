package de.tubs.cs.ips.rpg.characters;

/**
 * @author rose
 */
public abstract class Monster extends Chara {

    protected static final int DEFAULT_HP = 260;
    protected static final int DEFAULT_ATK = 60;
    protected static final double DEFAULT_HIT = 0.53;
    protected static char DEFAULT_NAME = 'A';

    protected Monster(final String name, int maxHP, int atk, double hit) {
        super(name, maxHP, atk, hit);
    }

    protected Monster(final String name) {
        this(name + " (" + (DEFAULT_NAME++) + ")", DEFAULT_HP, DEFAULT_ATK, DEFAULT_HIT);
    }
}

