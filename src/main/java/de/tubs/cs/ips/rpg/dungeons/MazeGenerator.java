package de.tubs.cs.ips.rpg.dungeons;

/**
 * @author rose
 */
public interface MazeGenerator {

    char WALL = '#';
    char EMPTY = ' ';
    char START = 'S';
    char BATTLE = 'B';
    char FORGE = 'T';
    char FOUNTAIN = 'O';
    char END = 'Z';

    default char[][] generate(int height, int width) {
        return generate(height, width, Level.Difficulty.NORMAL);
    }

    char[][] generate(int height, int width, Level.Difficulty diff);
}
