/*
 * TCSS 305
 * 
 * An implementation of the classic game "Tetris".
 */

package model;

import java.util.Objects;

/**
 * Represents a 2D Point with x and y coordinates.
 * Point objects are immutable.
 * (Compare to java.awt.Point which are mutable)
 * 
 * @author Alan Fowler
 * @version 1.2
 */
public final class Point implements PointInterface {
    /** The X coordinate. */
    private final int myX;

    /** The Y coordinate. */
    private final int myY;

    /**
     * Constructs a Point using the provided coordinates.
     * 
     * @param theX the X coordinate.
     * @param theY the Y coordinate.
     */
    public Point(final int theX, final int theY) {
        super();
        myX = theX;
        myY = theY;
    }

    // Queries
    @Override
    public int x() {
        return myX;
    }

    @Override
    public int y() {
        return myY;
    }

    @Override
    public Point transform(final int theX, final int theY) {
        return new Point(myX + theX, myY + theY);
    }

    @Override
    public Point transform(final Point thePointClass) {
        return transform(thePointClass.x(), thePointClass.y());
    }

    // overridden methods of class Object

    @Override
    public boolean equals(final Object theOther) {
        boolean result = false;
        if (theOther == this) {
            result = true;
        } else if (theOther != null && theOther.getClass() == getClass()) {
            final Point p = (Point) theOther;
            result = myX == p.myX && myY == p.myY;
        }
        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(myX, myY);
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", myX, myY);
    }
}
