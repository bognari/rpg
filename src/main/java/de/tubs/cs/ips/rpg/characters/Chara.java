package de.tubs.cs.ips.rpg.characters;

import de.tubs.cs.ips.rpg.inventary.Inventory;
import de.tubs.cs.ips.rpg.inventary.Item;
import de.tubs.cs.ips.rpg.inventary.List;

/**
 * @author rose
 */
public abstract class Chara {

    protected final int MAX_HP;
    protected int hp, atk;
    protected double hit;
    protected final String name;
    protected int gold;
    protected List<Item> inventary;

    public Chara(final String name, int maxHP, int atk, double hit) {
        MAX_HP = maxHP;
        this.hp = MAX_HP;
        this.atk = atk;
        this.hit = hit;
        this.name = name;
        this.inventary = new Inventory<>();
        this.gold = 0;
    }

    public int attack(final Chara other) {
        if (other != null) {
            double rand = Math.random();
            if (!(rand > hit)) {
                double zf = Math.random() + 1;
                int dmg = (int) (atk * zf);
                other.takeDamage(dmg);
                if (other.isDefeated()) {
                    while (!other.getInventary().isEmpty()) {
                        inventary.insert(other.getInventary().firstItem());
                        other.getInventary().delete();
                    }
                    gold += other.getGold();
                }
                return dmg;
            }
        }
        return -1;
    }

    public void takeDamage(int dmg) {
        if (dmg > 0) {
            hp -= dmg;
        }
    }

    public boolean isDefeated() {
        return hp <= 0;
    }

    public String getName() {
        return name;
    }

    public int getGold() {
        return gold;
    }

    public List<Item> getInventary() {
        return inventary;
    }

    public int getAtk() {
        return atk;
    }

    @Override
    public String toString() {
        return String.format("%-10s -- HP %3d -- ATK %3d", name, hp, atk);
    }
}
