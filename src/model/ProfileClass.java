package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * A class for application's appearance.
 *
 * @author Dmitry Khotinskiy
 * @author Aly Badr
 * @author Brandon Phan
 * @author Lucas Jeong
 * @version 1.0.0
 */
public final class ProfileClass implements Profile {
    /**
     * Object for the tetris board.
     */
    private static final ProfileClass INSTANCE = new ProfileClass();

    /**
     * Manager for Property Change Listeners.
     */
    private final PropertyChangeSupport myPcs;

    /**
     * Name of the user.
     */
    private String myName = "Name";

    /**
     * Constructor for the application's appearance class.
     */
    private ProfileClass() {
        super();
        myPcs = new PropertyChangeSupport(this);
    }

    /**
     * Getter for the application's appearance class.
     * @return Instance of application's appearance class.
     */
    public static ProfileClass getInstance() {
        return INSTANCE;
    }

    @Override
    public void addScore(final int theScore) {
        myPcs.firePropertyChange(NEW_SCORE_PROP_CHANGE, null, theScore);
    }

    @Override
    public void setName(final String theName) {
        myName = theName;
        myPcs.firePropertyChange(SET_NAME_PROP_CHANGE, null, theName);
    }

    @Override
    public String getName() {
        return myName;
    }

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
}