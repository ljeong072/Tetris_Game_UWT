package model;

import java.awt.Color;
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
public final class AppearanceClass implements Appearance {
    /**
     * Object for the tetris board.
     */
    private static final AppearanceClass INSTANCE = new AppearanceClass();

    /**
     * Object for the tetris board.
     */
    private String myCurrentTheme = LIGHT;

    /**
     * Manager for Property Change Listeners.
     */
    private final PropertyChangeSupport myPcs;

    /**
     * Constructor for the application's appearance class.
     */
    private AppearanceClass() {
        super();
        myPcs = new PropertyChangeSupport(this);
    }

    /**
     * Getter for the application's appearance class.
     * @return Instance of application's appearance class.
     */
    public static AppearanceClass getInstance() {
        return INSTANCE;
    }

    @Override
    public void setTheme(final String theTheme) {
        myCurrentTheme = theTheme;
        themeChanged(theTheme);
    }

    @Override
    public String getTheme() {
        return myCurrentTheme;
    }

    @Override
    public Color getBlockColor(final Block theBlockType) {
        final Color returnColor;
        if (myCurrentTheme.equals(LIGHT)) {
            returnColor = getLightBlockColor(theBlockType);
        } else {
            returnColor = getDarkBlockColor(theBlockType);
        }

        return returnColor;
    }

    private Color getLightBlockColor(final Block theBlockType) {
        return switch (theBlockType) {
            case I -> I_LIGHT_COLOR;
            case J -> J_LIGHT_COLOR;
            case L -> L_LIGHT_COLOR;
            case O -> O_LIGHT_COLOR;
            case S -> S_LIGHT_COLOR;
            case T -> T_LIGHT_COLOR;
            case Z -> Z_LIGHT_COLOR;
            default -> Color.BLACK;
        };
    }

    private Color getDarkBlockColor(final Block theBlockType) {
        return switch (theBlockType) {
            case I -> I_DARK_COLOR;
            case J -> J_DARK_COLOR;
            case L -> L_DARK_COLOR;
            case O -> O_DARK_COLOR;
            case S -> S_DARK_COLOR;
            case T -> T_DARK_COLOR;
            case Z -> Z_DARK_COLOR;
            default -> Color.WHITE;
        };
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

    private void themeChanged(final String theTheme) {
        myPcs.firePropertyChange(UPDATE_THEME_PROP_CHANGE, null, theTheme);
    }
}
