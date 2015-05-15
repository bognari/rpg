package de.tubs.cs.ips.rpg.inventary;

/**
 * @author rose
 * @date 15.05.15.
 */
public class Inventary<T extends Comparable<T>> implements List<T> {

    private Inventary<T> next;
    private T item;

    public Inventary() {
        this.next = null;
        this.item = null;
    }

    /**
     * Ueberprueft ob die Liste leer ist
     *
     * @return true, Liste ist leer
     */
    @Override
    public boolean isEmpty() {
        return next == null;
    }

    /**
     * Gibt die Laenge der Liste zurück
     *
     * @return die Laenge
     */
    @Override
    public int length() {
        if (isEmpty())
            return 0;
        return 1 + next.length();
    }

    /**
     * Prueft ob ein Item in der Liste ist
     *
     * @param x das Item
     * @return true, x ist in der Liste enthalten
     */
    @Override
    public boolean isInList(T x) {
        if (x == null)
            return false;

        if (isEmpty())
            return false;
        else if (item.equals(x))
            return true;
        return next.isInList(x);
    }

    /**
     * Gibt das erste Item der Liste zurueck
     *
     * @return das erste Item
     * @throws IllegalStateException wenn die Liste leer ist
     */
    @Override
    public T firstItem() throws IllegalStateException {
        if (isEmpty())
            throw new IllegalStateException("Liste ist leer!");
        return next.item;
    }

    /**
     * Gibt das i-te Item der Liste zurueck
     *
     * @param i der Index
     * @return das i-te Item
     * @throws IndexOutOfBoundsException wenn i < 0 oder  i >= length()
     */
    @Override
    public T getItem(int i) throws IndexOutOfBoundsException {
        if (i < 0 || i >= length())
            throw new IndexOutOfBoundsException("Der Index " + i + " liegt nicht innnerhalb der Liste");
        else if (i == 0)
            return next.item;
        return next.getItem(--i);
    }

    /**
     * Fuegt ein Element sortiert in die Liste ein
     *
     * @param x das Item
     * @return die geanderte Liste
     */
    @Override
    public List<T> insert(T x) {
        if (x == null)
            return this;

        if (isEmpty())
            return append(x);
        else if (x.compareTo(next.item) < 0) {
            Inventary<T> inv = new Inventary<>();
            inv.item = x;
            inv.next = next;
            next = inv;
            return this;
        } else {
            return next.insert(x);
        }
    }

    /**
     * Fuegt ein Element an das Ende der Liste ein
     *
     * @param x das Item
     * @return die geanderte Liste
     */
    @Override
    public List<T> append(T x) {
        if (x == null)
            return this;

        if (isEmpty()) {
            Inventary<T> inv = new Inventary<>();
            inv.item = x;
            inv.next = next;
            next = inv;
            return this;
        } else {
            next.append(x);
        }
        return this;
    }

    /**
     * Loescht das erste vorkommen des Items x
     *
     * @param x das Item
     * @return die geanderte Liste
     */
    @Override
    public List<T> delete(T x) {
        if (x == null)
            return this;

        if (isEmpty())
            return this;
        else if (next.item.equals(x))
            next = next.next;
        return next.delete(x);
    }

    /**
     * Loescht das erste Element der Liste
     *
     * @return die geanderte Liste
     */
    @Override
    public List<T> delete() {
        if (!isEmpty())
            next = next.next;
        return this;
    }

    @Override
    public String toString() {
        int i = 1;
        if (isEmpty())
            return "";
        return String.format("%03d. %s%n", i, next.item) + next.toString(++i);
    }

    private String toString(int i) {
        if (isEmpty())
            return "";
        return String.format("%03d. %s%n", i, next.item) + next.toString(++i);
    }
}
