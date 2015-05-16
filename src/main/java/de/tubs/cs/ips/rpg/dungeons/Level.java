package de.tubs.cs.ips.rpg.dungeons;

import de.tubs.cs.ips.rpg.characters.MonsterFactory;
import de.tubs.cs.ips.rpg.characters.Player;
import de.tubs.cs.ips.rpg.dungeons.fields.Direction;
import de.tubs.cs.ips.rpg.dungeons.fields.Field;
import de.tubs.cs.ips.rpg.dungeons.fields.Fight;
import de.tubs.cs.ips.rpg.dungeons.fields.Position;

import javax.management.timer.Timer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static de.tubs.cs.ips.rpg.dungeons.fields.Direction.*;
import static de.tubs.cs.ips.rpg.dungeons.fields.Field.FieldType;


/**
 * @author rose
 */
public class Level {

    public enum Difficulty {

        EASY, NORMAL, HARD
    }

    private final Position startPos, endPos;
    private Position currPos;

    private final Set<Position> visited;
    private final Difficulty diff;

    private final Field[][] originalMap;
    private Field[][] darkMap;

    private final Player player;

    public Level(char[][] map, final Player player, final Difficulty diff) {
        this.diff = diff;
        this.visited = new HashSet<>();
        this.player = player;
        this.originalMap = readMap(map);
        startPos = findPosition(FieldType.START);
        currPos = new Position(startPos);
        endPos = findPosition(FieldType.END);
        initDarkMap();
        peek();
    }

    public Level(char[][] map, final Player player) {
        this(map, player, Difficulty.NORMAL);
    }

    public boolean move(final Direction dir) throws InterruptedException {
        final Position p = currPos.positionAt(dir);

        if (validMovePosition(p)) {
            currPos = new Position(p);
            System.out.print("You went " + dir.name().toLowerCase());
            switch (getOriginalFieldType(currPos)) {
                case END:
                    Thread.sleep(Timer.ONE_SECOND);
                    System.out.println(" and found finally your goal :)");
                    break;
                case BATTLE:
                    Thread.sleep(Timer.ONE_SECOND);
                    System.out.print(" where a wild beast awaits you:  ");
                    final Fight f = new Fight(player, MonsterFactory.create(diff));
                    System.out.println(f.monster());
                    System.out.println("You must defeat it!");
                    if (f.start()) {
                        System.out.println("Well done young adventurer! You defeted " + f.monster());
                    }
                    break;
                case EMPTY:
                    Thread.sleep(Timer.ONE_SECOND);
                    break;
                case FOUNTAIN:
                    Thread.sleep(Timer.ONE_SECOND);
                    System.out.println(" and found a healing fountain, which heals your wounds :)");
                    player.fullHeal(false);
                    break;
                case FORGE:
                    Thread.sleep(Timer.ONE_SECOND);
                    System.out.print(" and found a forge, which raises your attack value to ");
                    player.raiseAtk();
                    System.out.println(player.getAtk());
                    break;
            }
            visited.add(p);
            peek();
            return true;
        } else {
            System.out.println("You can't go " + dir.name().toLowerCase() + "! ... ");
            printFreeDirections();
            Thread.sleep(Timer.ONE_SECOND);
        }
        return false;
    }

    private boolean inMap(final Position pos) {
        return (pos.row >= 0 && pos.col >= 0 && pos.row < originalMap.length && pos.col < originalMap[0].length);
    }

    private boolean validMovePosition(final Position pos) {
        if (inMap(pos)) {
            return getOriginalFieldType(pos) != FieldType.WALL;
        }
        return false;
    }

    public boolean finished() {
        return currPos.equals(endPos);
    }

    public void setNewPlayerPosition(final Position pos) {
        if (!currPos.equals(pos)) {
            setMarkerOnMap(pos, FieldType.PLAYER);
            setMarkerOnMap(currPos, getOriginalFieldType(currPos));
            currPos = new Position(pos);
        }
    }

    private void setMarkerOnMap(final Position pos, final FieldType type) {
        if (pos.row >= 0 && pos.col >= 0) {
            darkMap[pos.row][pos.col].changeType(type);
        }
    }

    private Position findPosition(final FieldType type) {
        for (int row = 0; row < originalMap.length; row++) {
            for (int col = 0; col < originalMap[row].length; col++) {
                if (originalMap[row][col].ofType(type)) {
                    return new Position(row, col);
                }
            }
        }
        return null;
    }

    private FieldType getOriginalFieldType(final Position pos) {
        return originalMap[pos.row][pos.col].type();
    }

    private void printFreeDirections() {
        final List<Position> neighbors = findAdjacentNeighbors(currPos);
        String text = "You can move ";
        for (final Position p : neighbors) {
            if (!originalMap[p.row][p.col].ofType(FieldType.WALL)) {
                if (currPos.west(p)) {
                    text += "west, ";
                } else if (currPos.east(p)) {
                    text += "east, ";
                }

                if (currPos.north(p)) {
                    text += "north, ";
                } else if (currPos.south(p)) {
                    text += "south, ";
                }
            }
        }
        text = text.substring(0, text.length() - 2);
        text = replaceLast(text, ", ", " and ");
        System.out.println(text);
    }

    private String replaceLast(String string, String toReplace, String replacement) {
        int pos = string.lastIndexOf(toReplace);
        if (pos > -1) {
            return string.substring(0, pos)
                    + replacement
                    + string.substring(pos + toReplace.length(), string.length());
        } else {
            return string;
        }
    }

    private List<Position> findAdjacentNeighbors(final Position pos) {
        final List<Position> positions = new ArrayList<>(4);
        positions.add(pos.positionAt(WEST));
        positions.add(pos.positionAt(EAST));
        positions.add(pos.positionAt(NORTH));
        positions.add(pos.positionAt(SOUTH));
        return positions;
    }

    private List<Position> findNeighbors(final Position pos) {
        final List<Position> positions = new ArrayList<>(8);
        positions.addAll(findAdjacentNeighbors(pos));

        positions.add(pos.positionAt(WEST).positionAt(NORTH));
        positions.add(pos.positionAt(WEST).positionAt(SOUTH));
        positions.add(pos.positionAt(EAST).positionAt(NORTH));
        positions.add(pos.positionAt(EAST).positionAt(SOUTH));
        return positions;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        sb.append("\n");
        for (Field[] row : darkMap) {
            for (Field field : row) {
                sb.append(field).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public String clearMap() {
        final StringBuilder sb = new StringBuilder();

        sb.append("\n");
        for (Field[] row : originalMap) {
            for (Field field : row) {
                sb.append(field).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private Field[][] readMap(char[][] map) {
        final Field[][] newMap = new Field[map.length][map[0].length];
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                newMap[row][col] = new Field(map[row][col], new Position(row, col));
            }
        }
        return newMap;
    }

    private void initDarkMap() {
        darkMap = new Field[originalMap.length][originalMap[0].length];

        for (int row = 0; row < originalMap.length; row++) {
            darkMap[row] = new Field[originalMap[row].length];
            for (int col = 0; col < originalMap[row].length; col++) {
                darkMap[row][col] = new Field(' ', new Position(row, col));
            }
        }

        setMarkerOnMap(currPos, FieldType.PLAYER);
    }

    private void peek() {
        final List<Position> points = findNeighbors(currPos);
        for (final Position pos : points) {
            if (!pos.equals(currPos)) {
                setMarkerOnMap(pos, getOriginalFieldType(pos));
            }
        }

        for (final Position pos : visited) {
            if (getOriginalFieldType(pos) != FieldType.START) {
                setMarkerOnMap(pos, FieldType.EMPTY);
            }
        }
        setMarkerOnMap(currPos, FieldType.PLAYER);
    }
}
