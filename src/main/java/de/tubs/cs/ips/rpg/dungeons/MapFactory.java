package de.tubs.cs.ips.rpg.dungeons;

import de.tubs.cs.ips.rpg.dungeons.fields.Position;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

import static de.tubs.cs.ips.rpg.dungeons.fields.Direction.*;

/**
 * Map Generator.
 * <p>
 * @author rose
 */
public class MapFactory implements MazeGenerator {

    protected static final int MIN_HEIGHT = 5;
    protected static final int MAX_HEIGHT = 500;
    protected static final int MIN_WIDTH = 5;
    protected static final int MAX_WIDTH = 500;

    private char[][] map;
    private boolean[][] visited;

    public static void main(final String[] args) {
        int height = 8, width = 12;
        final MapFactory mf = new MapFactory();
        char[][] map = mf.generate(height, width);
        System.out.println(mf.toString(map));
    }

    @Override
    public char[][] generate(int height, int width) {
        return generate(height, width, Level.Difficulty.NORMAL);
    }

    @Override
    public char[][] generate(int height, int width, Level.Difficulty diff) {
        assert (height > MIN_HEIGHT && width > MIN_WIDTH
            && height < MAX_HEIGHT && width < MAX_WIDTH && diff != null);

        if (height % 2 == 0) {
            height++;
        }
        if (width % 2 == 0) {
            width++;
        }

        // create map and visited fields marker
        initializeMap(height, width);

        // mark start and end position
        final Position start = startPosition(),
            end = endPosition();
        map[start.row][start.col] = START;
        map[end.row][end.col] = END;

        // build map skeletton
        // traverseMapPartiallyIterative(start);
        traverseMap(start);

        // compute the number of other fields depending on the given difficulty
        int numOfFields = (height - 1) * (width - 1) / 2;
        int numOfEnemies = Math.max(2, numOfFields / 10), // 10%
            numOfForges = Math.max(1, numOfFields / 20), // 5%
            numOfFountaints = Math.max(1, numOfFields / 20); // 5%

        switch (diff) {
            case EASY:
                numOfEnemies = Math.max(1, numOfFields / 12);
                numOfForges = Math.max(2, numOfFields / 18);
                numOfFountaints = Math.max(2, numOfFields / 18);
                break;
            case HARD:
                numOfEnemies = Math.max(3, numOfFields / 8);
                numOfForges = Math.max(1, numOfFields / 22);
                numOfFountaints = Math.max(1, numOfFields / 22);
                break;
        }

        // find all free positions in map
        final Stack<Position> positions = freePositions();

        // mark battle fields
        for (int i = 0; i < numOfEnemies; i++) {
            final Position p = positions.pop();
            map[p.row][p.col] = BATTLE;
        }

        // mark forges
        for (int i = 0; i < numOfForges; i++) {
            final Position p = positions.pop();
            map[p.row][p.col] = FORGE;
        }

        // mark fountains
        for (int i = 0; i < numOfFountaints; i++) {
            final Position p = positions.pop();
            map[p.row][p.col] = FOUNTAIN;
        }
        return map;
    }

    private Stack<Position> freePositions() {
        final Stack<Position> positions = new Stack<>();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == EMPTY) {
                    positions.add(new Position(i, j));
                }
            }
        }
        Collections.shuffle(positions);
        return positions;
    }

    private void traverseMapPartiallyIterative(final Position cur) {
        final Position[] neighbors = findNeighbors(cur);
        randomize(neighbors);
        for (final Position neighbor : neighbors) {
            if (validPosition(neighbor)) {
                visit(cur, neighbor);
                traverseMapPartiallyIterative(neighbor);
            }
        }
    }

    private void traverseMap(final Position cur) {
        final Position next = findRandomUnvisitedNeighbor(cur);

        if (next != null) {
            visit(cur, next);
            traverseMap(next);
            traverseMap(cur);
        }
    }

    private Position findRandomUnvisitedNeighbor(Position cur) {
        final Position[] neighbors = findNeighbors(cur);
        int idx = new Random().nextInt(neighbors.length);
        int comp = idx;

        do {
            final Position pos = neighbors[idx++];
            if (validPosition(pos) && !visited(pos)) {
                return pos;
            }
            if (idx >= neighbors.length) {
                idx = 0;
            }
        } while (comp != idx);
        return null;
    }

    private boolean visited(final Position pos) {
        return visited[pos.row][pos.col];
    }

    private void visit(final Position prev, final Position cur) {
        visited[cur.row][cur.col] = true;
        int row = (prev.row + cur.row) / 2;
        int col = (prev.col + cur.col) / 2;
        map[row][col] = EMPTY;
    }

    private Position startPosition() {
        return new Position(1, 1);
    }

    private Position endPosition() {
        return new Position(map.length - 2, map[0].length - 2);
    }

    private Position[] findNeighbors(final Position cur) {
        final Position[] neighbors = new Position[4];
        neighbors[0] = cur.positionAt(WEST).positionAt(WEST);
        neighbors[1] = cur.positionAt(EAST).positionAt(EAST);
        neighbors[2] = cur.positionAt(NORTH).positionAt(NORTH);
        neighbors[3] = cur.positionAt(SOUTH).positionAt(SOUTH);
        return neighbors;
    }

    private void randomize(final Position[] neighbors) {
        Collections.shuffle(Arrays.asList(neighbors));
    }

    private boolean validPosition(final Position pos) {
        return (pos != null && pos.row >= 0 && pos.col >= 0
            && pos.row < map.length && pos.col < map[0].length
            && !visited[pos.row][pos.col]);
    }

    private void initializeMap(int height, int width) {
        map = new char[height][width];
        visited = new boolean[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (row % 2 == 0 || col % 2 == 0) {
                    map[row][col] = WALL;
                } else {
                    map[row][col] = EMPTY;
                }
            }
        }
    }

    public String toString(final char[][] map) {
        final StringBuilder sb = new StringBuilder();

        for (char[] row : map) {
            for (char field : row) {
                sb.append(field).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
