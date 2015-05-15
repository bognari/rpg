package de.tubs.cs.ips.rpg.dungeons.fields;

import de.tubs.cs.ips.rpg.characters.Chara;
import de.tubs.cs.ips.rpg.characters.Dragon;
import de.tubs.cs.ips.rpg.characters.Monster;
import de.tubs.cs.ips.rpg.characters.Player;

import java.util.Scanner;

/**
 * @author rose
 */
public class Fight {

    private static final String ATK = "1",
            ITEM = "2",
            ABILITY = "3",
            DOUBLE_ATTACK = "1",
            FULL_HEAL = "2",
            CRITICAL = "3";

    private final Player player;
    private final Monster monster;

    public Fight(final Player player, final Monster monster) {
        this.player = player;
        this.monster = monster;
    }

    public Fight(final Player player) {
        this.player = player;
        this.monster = callAMonster("Ganon");
    }

    public String monster() {
        return monster.getName();
    }

    public boolean start() {
        final Scanner sc = new Scanner(System.in);
        printStats(player, monster);
        printSeparator();

        String input;
        while (!player.isDefeated() && !monster.isDefeated()) {
            printPossibilities(player);
            input = sc.nextLine().trim();
            printSeparator();
            switch (input) {
                case ATK:
                    attack(player, monster);
                    break;
                case ITEM:
                    player.heal();
                    break;
                case ABILITY:
                    System.out.println("Choose your ability: \n"
                            + "\t1 -> double attack\n"
                            + "\t2 -> full heal\n"
                            + "\t3 -> critical attack");
                    input = sc.nextLine().trim();
                    switch (input) {
                        case DOUBLE_ATTACK:
                            player.doubleAttack(monster);
                            break;
                        case FULL_HEAL:
                            player.fullHeal(true);
                            break;
                        case CRITICAL:
                            player.criticalAttack(monster);
                            break;
                        default:
                            wrongChoice();
                    }
                    break;
                default:
                    wrongChoice();
            }

            if (monster.isDefeated()) {
                return true;
            }
            printStats(player, monster);
            attack(monster, player);
            if (player.isDefeated()) {
                break;
            }
            printStats(player, monster);
            player.regenerateAp();
        }
        return false;
    }

    private static Monster callAMonster(final String name) {
        return new Dragon();
    }

    private static void printSeparator() {
        System.out.println("-----------------------------------------------");
    }

    private static void wrongChoice() {
        System.out.println("Erroneous action! \n");
    }

    private static void attack(final Chara attacker, final Chara defender) {
        System.out.printf("%s attacks!%n", attacker.getName());
        int didDamage = attacker.attack(defender);
        if (didDamage == -1) {
            System.out.printf("... but %s avoids the attack!%n", defender.getName());
        } else {
            System.out.printf("... and reduce %s's HP by %d%n", defender.getName(), didDamage);
        }
    }

    private static void printStats(final Chara hero, final Chara monster) {
        System.out.println(hero);
        System.out.println(monster);
        System.out.println();
    }

    private static void printPossibilities(final Player hero) {
        System.out.printf("Possible actions: %n"
                + "1 -> Attack your foe! %n"
                + "2 -> Use a potion (%d left)%n"
                + "3 -> Use a special ability (%d AP left)%n"
                + "%n"
                + "Choose: ", hero.getRemainingItemUses(), hero.apLeft());
    }
}
