package de.tubs.cs.ips.rpg.dungeons;

import de.tubs.cs.ips.rpg.characters.Player;
import de.tubs.cs.ips.rpg.dungeons.fields.Direction;

import java.util.Scanner;

/**
 *
 * @author rose
 */
public class Crawler {

    private static final int HERO_HP = 300;
    private static final int HERO_AP = 80;
    private static final int HERO_AP_PLUS = 10;
    private static final int HERO_ATK = 50;
    private static final double HERO_HIT = 0.8;
    private static final int MAX_ITEMS = 4;
    private static final int POTION_EFFECT = 50;



    public static void main(final String[] args) throws InterruptedException {
        final Player player = callAHero("Link");
        int height = 8, width = 30;
        final MapFactory mf = new MapFactory();
        char[][] map = mf.generate(height, width);
        final Level m = new Level(map, player);
        System.out.println("A new journey begins ... ");
        System.out.println("  - P: marks your current position");
        System.out.println("  - O: marks a healing fountain");
        System.out.println("  - T: marks a forge");
        System.out.println("  - B: marks a possible foe");
        System.out.println("  - Z: marks your goal!");
        System.out.println();
        System.out.println("By typing w, a, s and d, you can move north, west, south and east. Where do you want?");
        System.out.println(m);

        final Scanner sc = new Scanner(System.in);
        String input;
        while (!m.finished()) {
            System.out.println();
            input = sc.nextLine().trim();
            Direction dir = readDirection(input.trim().toLowerCase());

            if (!m.move(dir)) {
                System.out.println();
                continue;
            }
            if (player.isDefeated()) {
                System.out.println("You died on your journey ... ");
                break;
            }
            player.regenerateAp();
            System.out.println(m);
        }
        reachedGoal();
    }

    private static Direction readDirection(final String str) {
        switch (str) {
            case "w":
            case "north":
                return Direction.NORTH;
            case "a":
            case "west":
                return Direction.WEST;
            case "s":
            case "south":
                return Direction.SOUTH;
            case "d":
            case "east":
                return Direction.EAST;
            default:
                return Direction.NONE;
        }
    }

    private static Player callAHero(final String name) {
        return new Player(name, HERO_HP, HERO_ATK, HERO_HIT, MAX_ITEMS, POTION_EFFECT, HERO_AP, HERO_AP_PLUS);
    }

    private static void reachedGoal() {
        System.out.println("                                              :\n"
            + "                                              ::\n"
            + "                                             ::\n"
            + "                                             ::\n"
            + "                                            ::\n"
            + "                                            ::\n"
            + "                              __           ::\n"
            + "   _..-'/-??--/_          ,.--. ''.     |`\\=,.. \n"
            + "-:--.---''-..  /_         |\\\\_\\..  \\    `-.=._/\n"
            + ".-|?         '.  \\         \\_ \\-`/\\ |    ::`\n"
            + "  /  @  \\      \\  -_   _..--|-\\??``'--.-/_\\\n"
            + "  |   .-'|      \\  \\\\-'\\_/     ?/-.|-.\\_\\_/\n"
            + "  \\_./` /        \\_//-''/    .-'\n"
            + "       |           '-/'@====/              _.--.\n"
            + "   __.'             /??'-. \\-'.          _/   /?'\n"
            + ".''____|   /       |'--\\__\\/-._        .'    |\n"
            + " \\ \\_. \\  |       _| -/        \\-.__  /     /\n"
            + "  \\___\\ '/   _.  ('-..| /       '_  ''   _.'\n"
            + "        /  .'     ???? /        | ``'--''\n"
            + "       (  / ?```?????|-|        |\n"
            + "        \\ \\_.         \\ \\      /  \n"
            + "         \\___\\         '.'.   /\n"
            + "                         /    |\n"
            + "                        /   .'\n"
            + "                       /  .' |\n"
            + "                     .'  / \\  \\\n"
            + "                    /___| /___'");
    }
}
