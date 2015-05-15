package de.tubs.cs.ips.rpg.characters;


import de.tubs.cs.ips.rpg.dungeons.Level;

/**
 *
 * @author rose
 */
public final class MonsterFactory {

    private MonsterFactory() {
    }

    public static Monster create(final Level.Difficulty diff) {
        switch (diff) {
            case NORMAL:
                return new GiantSpider();
            case HARD:
                return new Dragon();
        }
        return null;
    }
}
