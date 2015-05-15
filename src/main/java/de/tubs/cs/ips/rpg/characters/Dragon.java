package de.tubs.cs.ips.rpg.characters;

/**
 * @author rose
 * @date 15.05.15.
 */
public class Dragon extends Monster {

    private boolean flies;

    public Dragon(final String name, int maxHP, int atk, double hit) {
        super(name, maxHP, atk, hit);
        flies = false;
    }

    public Dragon() {
        this("Ganon", 300, 120, 0.6);
    }

    @Override
    public void takeDamage(int dmg) {
        if (!flies) {
            super.takeDamage(dmg);
        } else {
            System.out.println(name + " cannot be damaged, as long as it flies!");
        }
    }

    @Override
    public int attack(final Chara other) {
        if (other != null) {
            if (flies) {
                flies = false;
                System.out.println(name + " prepares to attack");
            } else {
                flies = true;
                double rand = Math.random();
                if (!(rand > hit)) {
                    double zf = Math.random() + 2;
                    int dmg = (int) (atk * zf);
                    other.takeDamage(dmg);
                    return dmg;
                }
            }
        }
        return -1;
    }
}
