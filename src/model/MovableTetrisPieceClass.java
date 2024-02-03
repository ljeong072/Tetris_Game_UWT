/*
 * TCSS 305
 * 
 * An implementation of the classic game "Tetris".
 */

package model;

/**
 * Represents a TetrisPiece with a position and a rotation.
 * <p>
 * A MovableTetrisPiece is immutable.
 * 
 * @author Charles Bryan
 * @author Alan Fowler
 * @version 1.3
 */
public final class MovableTetrisPieceClass implements MovableTetrisPiece {
    
    /**
     * The number of Points in a TetrisPiece.
     */
    private static final int BLOCKS = 4;
    
    /**
     * The TetrisPiece.
     */
    private final TetrisPiece myTetrisPiece;
    
    /**
     * The board position of this TetrisPiece.
     */
    private final Point myPosition;

    /**
     * The rotation value of this TetrisPiece.
     */
    private final Rotation myRotation;
    
    // This constructor allows creation of pieces in the zero rotational state
    /**
     * Constructs a MovablTetrisPiece using the specified type and position;
     * the initial rotation is set to the default zero (NONE) Rotation.
     * 
     * @param theTetrisPiece the type of TetrisPiece.
     * @param thePosition the position on the Board.
     */
    public MovableTetrisPieceClass(final TetrisPiece theTetrisPiece,
                                   final Point thePosition) {
        
        this(theTetrisPiece, thePosition, Rotation.NONE);
    }

    // This constructor allows creation of pieces in any rotation
    /**
     * Constructs a MovablTetrisPiece using the specified type, position, and initial rotation.
     * 
     * @param theTetrisPiece the type of TetrisPiece.
     * @param thePosition the position on the Board.
     * @param theRotation the initial angle of the TetrisPiece.
     */
    public MovableTetrisPieceClass(final TetrisPiece theTetrisPiece,
                                   final Point thePosition,
                                   final Rotation theRotation) {
        super();

        myTetrisPiece = theTetrisPiece;
        myPosition = thePosition;
        myRotation = theRotation;
    }

    @Override
    public TetrisPiece getTetrisPiece() {
        return myTetrisPiece;
    }

    @Override
    public Point getPosition() {
        return myPosition;
    } 

    @Override
    public Rotation getRotation() {
        return myRotation;
    }
    
    
    // methods overridden from class Object
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(myPosition.toString());
        sb.append('\n');
        final String[][] blocks = new String[BLOCKS][BLOCKS];
        for (int h = 0; h < BLOCKS; h++) {
            for (int w = 0; w < BLOCKS; w++) {
                blocks[w][h] = "   ";
            }
        }       
        for (final Point block : getLocalPoints()) {
            blocks[block.y()][block.x()] = "[ ]";
        }

        for (int h = BLOCKS - 1; h >= 0; h--) {
            for (int w = 0; w < BLOCKS; w++) {
                if (blocks[h][w] != null) {
                    sb.append(blocks[h][w]);
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }
    
    // protected getters - used by the Board class

    @Override
    public Point[] getBoardPoints() {
        return getPoints(myPosition);
    }
    
    // protected movement methods - used by the Board class

    @Override
    public MovableTetrisPieceClass rotateCW() {
        return new MovableTetrisPieceClass(myTetrisPiece,
                                      myPosition,
                                      myRotation.clockwise());
    }

    @Override
    public MovableTetrisPieceClass rotateCCW() {
        return new MovableTetrisPieceClass(myTetrisPiece,
                                      myPosition,
                                      myRotation.counterClockwise());
    }

    @Override
    public MovableTetrisPieceClass left() {
        return new MovableTetrisPieceClass(myTetrisPiece,
                                      myPosition.transform(-1, 0),
                                      myRotation);
    }

    @Override
    public MovableTetrisPieceClass right() {
        return new MovableTetrisPieceClass(myTetrisPiece,
                                      myPosition.transform(1, 0),
                                      myRotation);
    }

    @Override
    public MovableTetrisPieceClass down() {
        return new MovableTetrisPieceClass(myTetrisPiece,
                                      myPosition.transform(0, -1),
                                      myRotation);
    }
    
    // This protected method is used by the Board class rotation methods
    // in order to support wall kicks during rotations

    @Override
    public MovableTetrisPieceClass setPosition(final Point thePosition) {
        return new MovableTetrisPieceClass(myTetrisPiece, thePosition, myRotation);
    }


    
    // private methods

    /**
     * Get the block points of the TetrisPiece transformed by x and y.
     * 
     * @param thePointClass the point to transform the points around.
     * @return array of TetrisPiece block points.
     */
    private Point[] getPoints(final Point thePointClass) {

        final Point[] blocks = myTetrisPiece.getPoints();
        
        for (int i = 0; i < blocks.length; i++) {
            final Point block = blocks[i];
            if (myTetrisPiece != TetrisPiece.O) {
                switch (myRotation) {
                    case QUARTER:
                        blocks[i] = new Point(block.y(),
                                              myTetrisPiece.getWidth() - block.x() - 1);
                        
                        break;
                    case HALF:
                        blocks[i] = new Point(myTetrisPiece.getWidth() - block.x() - 1,
                                              myTetrisPiece.getWidth() - block.y() - 1);
                        
                        break;
                    case THREEQUARTER:                 
                        blocks[i] = new Point(myTetrisPiece.getWidth() - block.y() - 1,
                                              block.x());
                        
                        
                        break;
                    default:
                }
            }
            if (thePointClass != null) {
                blocks[i] = blocks[i].transform(thePointClass);
            }
        }

        return blocks;
    }
    
    /**
     * Gets the local points of the TetrisPiece rotated.
     * 
     * @return array of TetrisPiece block points.
     */
    private Point[] getLocalPoints() {
        return getPoints(null);
    }


}
