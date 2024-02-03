package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;
import java.util.List;

/**
 * Abstract class for the board.
 *
 * @author Dmitry Khotinskiy
 * @author Aly Badr
 * @author Brandon Phan
 * @author Lucas Jeong
 * @version 1.0.0
 */
public abstract class AbstractBoard implements PropertyChangeEnabledBoard {
    /**
     * Piece that is next to play.
     */
    protected TetrisPiece myNextPiece;

    /**
     * Piece that is currently movable.
     */
    protected MovableTetrisPieceClass myCurrentPiece;

    /**
     * The frozen blocks on the board.
     */
    protected final List<Block[]> myFrozenBlocks = new LinkedList<>();

    /**
     * The game over state.
     */
    protected boolean myGameOver;

    /**
     * Manager for Property Change Listeners.
     */
    private final PropertyChangeSupport myPcs = new PropertyChangeSupport(this);

    @Override
    public void addPropertyChangeListener(final PropertyChangeListener theListener) {
        myPcs.addPropertyChangeListener(theListener);
    }

    @Override
    public void addPropertyChangeListener(final String thePropertyName,
                                          final PropertyChangeListener theListener) {
        myPcs.addPropertyChangeListener(thePropertyName, theListener);
    }

    @Override
    public void removePropertyChangeListener(final PropertyChangeListener theListener) {
        myPcs.removePropertyChangeListener(theListener);
    }

    @Override
    public void removePropertyChangeListener(final String thePropertyName,
                                             final PropertyChangeListener theListener) {
        myPcs.removePropertyChangeListener(thePropertyName, theListener);
    }

    protected void newGameSubscription() {
        myPcs.firePropertyChange(NEW_GAME_PROP_CHANGE, null, myCurrentPiece);
    }

    protected void gameOverSubscription() {
        myPcs.firePropertyChange(GAME_OVER_PROP_CHANGE, null, myGameOver);
    }

    protected void nextPieceSubscription() {
        myPcs.firePropertyChange(NEXT_PIECE_UPDATE_PROP_CHANGE, null, myNextPiece);
    }

    protected void currentPieceSubscription() {
        myPcs.firePropertyChange(CURR_PIECE_UDATE_PROP_CHANGE, null , myCurrentPiece);
    }

    protected void rowFilledSubscription(final int theRowCount) {
        myPcs.firePropertyChange(ROW_FILLED_PROP_CHANGE, null, theRowCount);
    }

    protected void boardSubscription() {
        myPcs.firePropertyChange(BOARD_UPDATE_PROP_CHANGE, null, myFrozenBlocks);
    }
}