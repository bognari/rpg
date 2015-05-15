package de.tubs.cs.ips.rpg.characters;


/**
 *
 * @author rose
 */
public class Player extends Chara {

    private final int MAX_ITEMS,
        POTION_EFFECT,
        MAX_AP, AP_PLUS,
        AP_COST_DOUBLE,
        AP_COST_HEAL,
        AP_COST_CRITICAL;
    private int usedItemCount, ap;

    public Player(final String name, int maxHP, int atk, double hit, int maxItems, int potion, int maxAP, int apPlus) {
        super(name, maxHP, atk, hit);
        usedItemCount = 0;
        POTION_EFFECT = potion;
        MAX_ITEMS = maxItems;
        MAX_AP = maxAP;
        AP_PLUS = apPlus;
        ap = maxAP;
        AP_COST_DOUBLE = Math.max(AP_PLUS * 3, maxAP);
        AP_COST_HEAL = Math.max(AP_PLUS * 4, maxAP);
        AP_COST_CRITICAL = Math.max(AP_PLUS * 5, maxAP);
    }

    public void heal() {
        if (usedItemCount >= MAX_ITEMS) {
            System.out.println("You don't have enough items left!");
        } else {
            hp = Math.min(hp + POTION_EFFECT, MAX_HP);
            usedItemCount++;
        }
    }

    public int regenerateAp() {
        int oldAp = ap;
        ap = Math.min(ap + AP_PLUS, MAX_AP);
        return ap - oldAp;
    }

    public int getRemainingItemUses() {
        return MAX_ITEMS - usedItemCount;
    }

    public int doubleAttack(final Chara other) {
        if (other != null && ap >= AP_COST_DOUBLE) {
            attack(other);
            attack(other);
            ap -= AP_COST_DOUBLE;
        } else {
            System.out.println("You don't have enough AP (" + ap + "/" + AP_COST_DOUBLE + ")");
        }
        return -1;
    }

    public int criticalAttack(final Chara other) {
        if (other != null && ap >= AP_COST_CRITICAL) {
            ap -= AP_COST_CRITICAL;
            double rand = Math.random();
            if (!(rand > hit)) {
                double zf = Math.random() + 1;
                int dmg = (int) (atk * 3 * zf);
                other.takeDamage(dmg);
                return dmg;
            }
        } else {
            System.out.println("You don't have enough AP (" + ap + "/" + AP_COST_CRITICAL + ")");
        }
        return -1;
    }

    public void fullHeal(boolean withCosts) {
        if (!withCosts) {
            hp = MAX_HP;
        } else if (ap >= AP_COST_HEAL) {
            hp = MAX_HP;
            ap -= AP_COST_HEAL;
        } else {
            System.out.println("You don't have enough AP (" + ap + "/" + AP_COST_HEAL + ")");
        }
    }

    public int apLeft() {
        return ap;
    }

    public void raiseAtk() {
        atk = (int) (atk * 1.1);
    }

    @Override
    public String toString() {
        return String.format("%-10s -- HP %3d -- ATK %3d -- AP %3d", name, hp, atk, ap);
    }
}
