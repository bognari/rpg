package de.tubs.cs.ips.rpg;

import de.tubs.cs.ips.rpg.characters.Player;
import de.tubs.cs.ips.rpg.dungeons.Level;
import de.tubs.cs.ips.rpg.dungeons.MapFactory;
import de.tubs.cs.ips.rpg.dungeons.fields.Direction;

import javax.management.timer.Timer;
import java.util.Scanner;

/**
 * @author rose
 */
public final class Game {

    /*
     * Prompt formatter strings.
     */
    public static final String PROMPT_ERROR = "\033[47m\033[31m";
    public static final String PROMPT_NORMAL = "\033[0m";
    public static final String PROMPT_CLEAR = "\033[H\033[2J";

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
        final Level level = new Level(map, player);
        final StringBuilder sb = new StringBuilder();
        sb.append("A new journey begins ... \n");
        sb.append("  - P: marks your current position\n");
        sb.append("  - O: marks a healing fountain\n");
        sb.append("  - T: marks a forge\n");
        sb.append("  - B: marks a possible foe\n");
        sb.append("  - Z: marks your goal!\n");
        sb.append("---------------------------------\n");
        sb.append("Input controls: \n" +
                "  w - move northward\n" +
                "  a - move westward\n" +
                "  s - move southward\n" +
                "  d - move eastward\n" +
                "  i - print inventory\n" +
                "  q - quit game");

        final String menu = sb.toString();

        final Scanner sc = new Scanner(System.in);
        String input;
        while (!level.finished()) {
            System.out.println(PROMPT_CLEAR);
            System.out.println(level);
            System.out.println(menu);
            System.out.println();
            input = sc.nextLine().trim();
            Direction dir = Direction.NONE;
            switch (input) {
                case "w":
                    dir = Direction.NORTH;
                    break;
                case "a":
                    dir = Direction.WEST;
                    break;
                case "s":
                    dir = Direction.SOUTH;
                    break;
                case "d":
                    dir = Direction.EAST;
                    break;
                case "i":
                    System.out.println(player.getInventary());
                    break;
                case "q":
                    System.exit(0);
                default:
                    System.out.println(PROMPT_ERROR + "Invalid input!" + PROMPT_NORMAL);
                    Thread.sleep(Timer.ONE_SECOND);

            }
            if (dir != Direction.NONE) {
                if (!level.move(dir)) {
                    System.out.println();
                    continue;
                }
            }
            if (player.isDefeated()) {
                System.out.println("You died on your journey ... ");
                return;
            }
            player.regenerateAp();
        }
        reachedGoal();
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
