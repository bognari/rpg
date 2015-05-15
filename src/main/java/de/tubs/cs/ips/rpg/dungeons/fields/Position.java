package de.tubs.cs.ips.rpg.dungeons.fields;

/**
 *
 * @author rose
 */
public class Position {

    public final int row, col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Position(final Position pos) {
        this.row = pos.row;
        this.col = pos.col;
    }

    public boolean north(final Position p) {
        return p.row < row;
    }

    public boolean south(final Position p) {
        return p.row > row;
    }

    public boolean west(final Position p) {
        return p.col < col;
    }

    public boolean east(final Position p) {
        return p.col > col;
    }

    public Position positionAt(final Direction dir) {
        switch (dir) {
            case WEST:
                return new Position(row, col - 1);
            case SOUTH:
                return new Position(row + 1, col);
            case EAST:
                return new Position(row, col + 1);
            case NORTH:
                return new Position(row - 1, col);
            default:
                return new Position(-1, -1);
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.row;
        hash = 97 * hash + this.col;
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
        final Position other = (Position) obj;
        return this.row == other.row && this.col == other.col;
    }

    @Override
    public String toString() {
        return "(" + row + "," + col + ")";
    }
}
