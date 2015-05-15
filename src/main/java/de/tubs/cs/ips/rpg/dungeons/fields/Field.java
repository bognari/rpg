package de.tubs.cs.ips.rpg.dungeons.fields;

/**
 *
 * @author rose
 */
public class Field {

    private static final char FOUNTAIN_FIELD = 'O';
    private static final char EMPTY_FIELD = ' ';
    private static final char WALL_FIELD = '#';
    private static final char FORGE_FIELD = 'T';
    private static final char BATTLE_FIELD = 'B';
    private static final char START_FIELD = 'S';
    private static final char END_FIELD = 'Z';
    private static final char PLAYER_FIELD = 'P';

    private final Position pos;
    private char mark;
    private FieldType type;

    public enum FieldType {

        WALL, EMPTY, BATTLE, END, START, FORGE, FOUNTAIN, PLAYER
    }

    public Field(char mark, Position pos) {
        this.mark = mark;
        this.pos = pos;

        switch (mark) {
            case FOUNTAIN_FIELD:
                type = FieldType.FOUNTAIN;
                break;
            case EMPTY_FIELD:
                type = FieldType.EMPTY;
                break;
            case WALL_FIELD:
                type = FieldType.WALL;
                break;
            case FORGE_FIELD:
                type = FieldType.FORGE;
                break;
            case BATTLE_FIELD:
                type = FieldType.BATTLE;
                break;
            case START_FIELD:
                type = FieldType.START;
                break;
            case END_FIELD:
                type = FieldType.END;
                break;
            case PLAYER_FIELD:
                type = FieldType.PLAYER;
                break;
        }
    }

    public Field(FieldType type, Position pos) {
        changeType(type);
        this.pos = pos;
    }

    public Field(final Field f) {
        this.mark = f.mark;
        this.pos = f.pos;
    }

    public final void changeType(final FieldType type) {
        this.type = type;

        switch (type) {
            case FOUNTAIN:
                mark = FOUNTAIN_FIELD;
                break;
            case EMPTY:
                mark = EMPTY_FIELD;
                break;
            case BATTLE:
                mark = BATTLE_FIELD;
                break;
            case END:
                mark = END_FIELD;
                break;
            case FORGE:
                mark = FORGE_FIELD;
                break;
            case PLAYER:
                mark = PLAYER_FIELD;
                break;
            case START:
                mark = START_FIELD;
                break;
            case WALL:
                mark = WALL_FIELD;
                break;
        }
    }

    public FieldType type() {
        return type;
    }

    public boolean ofType(final FieldType type) {
        return this.type == type;
    }

    public Position pos() {
        return pos;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.pos.hashCode();
        hash = 97 * hash + this.mark;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Field other = (Field) obj;
        return this.mark == other.mark && this.pos == other.pos;
    }

    @Override
    public String toString() {
        return "" + mark;
    }
}

