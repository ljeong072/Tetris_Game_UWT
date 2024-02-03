package model;

import java.beans.PropertyChangeListener;

/**
 * Interface for PropertyChange and update variables.
 *
 * @author Dmitry Khotinskiy
 * @author Aly Badr
 * @author Brandon Phan
 * @author Lucas Jeong
 * @version 1.0.0
 */
public interface PropertyChangeEnabledBoard extends Board {
    /**
     * Variable for updates that will occur when a new game starts.
     */
    String NEW_GAME_PROP_CHANGE = "New Game";
    /**
     * Variable for updates that occur when there are changes to the board.
     */
    String BOARD_UPDATE_PROP_CHANGE = "Board Update";
    /**
     * Varibale for updates when a row gets filled.
     */
    String ROW_FILLED_PROP_CHANGE = "Row Filled";
    /**
     * Variable for updates when a game ends.
     */
    String GAME_OVER_PROP_CHANGE = "Game Over";
    /**
     * Variable for changes that will occur for the next piece.
     */
    String NEXT_PIECE_UPDATE_PROP_CHANGE = "Next Piece Update";
    /**
     * Variable for changes that happen to the current piece.
     */
    String CURR_PIECE_UDATE_PROP_CHANGE = "Current Piece Update";
    /**|
     * Variable for when the level updates.
     */
    String LEVEL_UPDATE_GAME = "LEVELED UP IN GAME";
    /**
     * Variable for updates when a block freezes.
     */
    String BLOCK_UPDATE_FREEZE = "Frozen Block";

    /**
     * Add a PropertyChangeListener to the listener list. The listener is registered for
     * all properties. The same listener object may be added more than once, and will be
     * called as many times as it is added. If listener is null, no exception is thrown and
     * no action is taken.
     *
     * @param theListener The PropertyChangeListener to be added
     */
    void addPropertyChangeListener(PropertyChangeListener theListener);

    /**
     * Add a PropertyChangeListener for a specific property. The listener will be invoked only
     * when a call on firePropertyChange names that specific property. The same listener object
     * may be added more than once. For each property, the listener will be invoked the number
     * of times it was added for that property. If propertyName or listener is null, no
     * exception is thrown and no action is taken.
     *
     * @param thePropertyName The name of the property to listen on.
     * @param theListener The PropertyChangeListener to be added
     */
    void addPropertyChangeListener(String thePropertyName, PropertyChangeListener theListener);

    /**
     * Remove a PropertyChangeListener from the listener list. This removes a
     * PropertyChangeListener that was registered for all properties. If listener was added
     * more than once to the same event source, it will be notified one less time after being
     * removed. If listener is null, or was never added, no exception is thrown and no action
     * is taken.
     *
     * @param theListener The PropertyChangeListener to be removed
     */
    void removePropertyChangeListener(PropertyChangeListener theListener);

    /**
     * Remove a PropertyChangeListener for a specific property. If listener was added more than
     * once to the same event source for the specified property, it will be notified one less
     * time after being removed. If propertyName is null, no exception is thrown and no action
     * is taken. If listener is null, or was never added for the specified property, no
     * exception is thrown and no action is taken.
     *
     * @param thePropertyName The name of the property that was listened on.
     * @param theListener The PropertyChangeListener to be removed
     */
    void removePropertyChangeListener(String thePropertyName,
                                      PropertyChangeListener theListener);
}