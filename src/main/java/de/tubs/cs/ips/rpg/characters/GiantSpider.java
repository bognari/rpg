package de.tubs.cs.ips.rpg.characters;

/**
 * Created by rose on 15.05.15.
 */
public class GiantSpider extends Monster {

    private int poisened;

    public GiantSpider(final String name, int maxHP, int atk, double hit) {
        super(name, maxHP, atk, hit);
        poisened = 3;
    }

    public GiantSpider() {
        this("Spiderboss", 250, 80, 0.7);
    }

    @Override
    public int attack(final Chara other) {
        if (other != null) {
            if (poisened > 0) {
                double rand = Math.random();
                if (!(rand > hit)) {
                    double zf = Math.random() + 2;
                    int dmg = (int) (atk * zf);
                    other.takeDamage(dmg);
                    if (poisened == 3) {
                        System.out.println(name + " poisenes " + other.getName() + " with its attack!");
                    }
                    return dmg;
                }
                other.takeDamage(other.MAX_HP / 20); // 5%
                poisened--;
            }
        }
        return -1;
    }
}
