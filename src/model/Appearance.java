package model;

import java.awt.Color;
import java.awt.Font;
import java.beans.PropertyChangeListener;

/**
 * An interface for application's appearance.
 *
 * @author Dmitry Khotinskiy
 * @author Aly Badr
 * @author Brandon Phan
 * @author Lucas Jeong
 * @version 1.0.0
 */
public interface Appearance {
    /**
     * Property change static value for theme update.
     */
    String UPDATE_THEME_PROP_CHANGE = "Update Theme";

    /**
     * Constant for light theme.
     */
    String LIGHT = "light";

    /**
     * Constant for dark theme.
     */
    String DARK = "dark";

    /**
     * Background color for light theme.
     */
    Color LIGHT_BACKGROUND = new Color(240, 240, 240);

    /**
     * Background color for dark theme.
     */
    Color DARK_BACKGROUND = new Color(25, 25, 25);

    /**
     * The height and the width of tetramino's square.
     */
    int SCALE_FACTOR = 40;

    /**
     * Default padding of the wrapper.
     */
    int DEFAULT_PADDING = 14;

    /**
     * Default font family.
     */
    String DEFAULT_FONT_FAMILY = "Calibri";

    /**
     * Header font.
     */
    Font HEADER_FONT = new Font(DEFAULT_FONT_FAMILY, Font.BOLD, 25);

    /**
     * Default font.
     */
    Font DEFAULT_FONT = new Font(DEFAULT_FONT_FAMILY, Font.PLAIN, 13);

    /**
     * I piece color in light theme.
     */
    Color I_LIGHT_COLOR = new Color(0, 150, 230);

    /**
     * I piece color in light theme.
     */
    Color J_LIGHT_COLOR = new Color(0, 0, 200);

    /**
     * I piece color in light theme.
     */
    Color L_LIGHT_COLOR = new Color(250, 100, 0);

    /**
     * I piece color in light theme.
     */
    Color O_LIGHT_COLOR = new Color(250, 200, 0);

    /**
     * I piece color in light theme.
     */
    Color S_LIGHT_COLOR = new Color(0, 175, 0);

    /**
     * I piece color in light theme.
     */
    Color T_LIGHT_COLOR = new Color(120, 0, 230);

    /**
     * I piece color in light theme.
     */
    Color Z_LIGHT_COLOR = new Color(230, 0, 0);

    /**
     * I piece color in dark theme.
     */
    Color I_DARK_COLOR = new Color(90, 175, 225);

    /**
     * J piece color in dark theme.
     */
    Color J_DARK_COLOR = new Color(35, 35, 220);

    /**
     * L piece color in dark theme.
     */
    Color L_DARK_COLOR = new Color(250, 110, 25);

    /**
     * O piece color in dark theme.
     */
    Color O_DARK_COLOR = new Color(255, 210, 60);

    /**
     * S piece color in dark theme.
     */
    Color S_DARK_COLOR = new Color(0, 210, 0);

    /**
     * T piece color in dark theme.
     */
    Color T_DARK_COLOR = new Color(140, 25, 250);

    /**
     * Z piece color in dark theme.
     */
    Color Z_DARK_COLOR = new Color(255, 30, 30);

    /**
     * Set the theme of the application. Allowed options are "light" (default) or "dark."
     */
    void setTheme(String theTheme);

    /**
     * Get the theme of the application.
     */
    String getTheme();

    /**
     * Get the color of the block.
     */
    Color getBlockColor(Block theBlockType);

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
