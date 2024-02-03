package view;

import static model.Appearance.LIGHT_BACKGROUND;
import static model.Appearance.DARK_BACKGROUND;
import static model.Appearance.HEADER_FONT;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import model.Appearance;
import model.AppearanceClass;

/**
 * Main class for creating a game controls window.
 *
 * @author Dmitry Khotinskiy
 * @author Aly Badr
 * @author Brandon Phan
 * @author Lucas Jeong
 * @version 1.0.0
 */
public final class GameControlsFrame extends JPanel implements PropertyChangeListener {
    /**
     * Create window for game controls with a title.
     */
    static final JFrame WINDOW = new JFrame("");

    /**
     * Minimum window size.
     */
    static final Dimension MIN_WINDOW_SIZE = new Dimension(450, 200);

    /**
     * Default font of the labels.
     */
    static final Font DEFAULT_FONT = new Font("Calibri", Font.PLAIN, 11);

    /**
     * String used to separate the key from its description
     */
    static final String LABEL_SEPARATOR = "- ";

    /**
     * Instance of application's appearance class.
     */
    private static final AppearanceClass APPEARANCE = AppearanceClass.getInstance();

    /**
     * The description labels on the screen.
     */
    private final ArrayList<BorderedLabel> myKeyLabels = new ArrayList<>();

    /**
     * The description labels on the screen.
     */
    private final ArrayList<JLabel> myDescLabels = new ArrayList<>();

    /**
     * Constructor for game controls.
     */
    public GameControlsFrame() {
        super();
        layoutComponents();
        APPEARANCE.addPropertyChangeListener(this);
    }

    /**
     * Method for creating a main window of the tetris game.
     */
    public static void createGUI() {
        final GameControlsFrame mainPanel = new GameControlsFrame();

        // Set the close behavior for the main window
        WINDOW.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // Adds content to the main window
        WINDOW.setContentPane(mainPanel);
        WINDOW.setJMenuBar(new MenuBar());
        WINDOW.setMinimumSize(MIN_WINDOW_SIZE);
        WINDOW.setResizable(false);

        // Resize the window down to the size of it's contents
        WINDOW.pack();

        // Center the window at the location of the main window
        WINDOW.setLocationRelativeTo(Tetris.WINDOW);
    }

    /**
     * Method for toggling the visibility of the window.
     */
    public static void toggleShow() {
        WINDOW.setVisible(!WINDOW.isShowing());
    }

    /**
     * Lay out the components and makes this frame visible.
     */
    private void layoutComponents() {
        setBackground(LIGHT_BACKGROUND);
        setLayout(new BorderLayout());

        final int padding = 15;
        setBorder(new EmptyBorder(padding, padding, padding, padding));

        final JPanel headerPanel = new JPanel(new FlowLayout());
        headerPanel.setBackground(new Color(0, 0, 0, 0));
        headerPanel.setBorder(new EmptyBorder(0, padding, padding, padding));
        createLabel(headerPanel, "Game Controls", HEADER_FONT);
        add(headerPanel, BorderLayout.NORTH);

        final JPanel controlsPanel = new JPanel();
        controlsPanel.setBackground(new Color(0, 0, 0, 0));
        controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.Y_AXIS));
        add(controlsPanel, BorderLayout.CENTER);
        printControls(controlsPanel);
    }

    private void printControls(final JPanel theParent) {
        printKeyDesc(theParent, "Right Arrow", "D", "Moves tetromino one unit to the right");
        printKeyDesc(theParent, "Left Arrow", "A", "Moves tetromino one unit to the left");
        printKeyDesc(theParent, "Down Arrow", "S", "Moves tetromino one unit down");
        printKeyDesc(theParent, "Up Arrow", "W", "Rotates tetromino clockwise");
        printKeyDesc(theParent, "Space", "Drops tertomino to the bottom");
        printKeyDesc(theParent, "Shift", "Rotates tetromino counterclockwise");
    }

    private void printKeyDesc(
        final JPanel theParent,
        final String theKey,
        final String theDesc
    ) {
        final JPanel controlsRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlsRow.setBackground(new Color(0, 0, 0, 0));
        createKeyLabel(controlsRow, theKey);
        createLabel(controlsRow, LABEL_SEPARATOR + theDesc, DEFAULT_FONT);
        theParent.add(controlsRow);
    }

    private void printKeyDesc(
            final JPanel theParent,
            final String theKey,
            final String theAltKey,
            final String theDesc
    ) {
        final JPanel controlsRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlsRow.setBackground(new Color(0, 0, 0, 0));
        createKeyLabel(controlsRow, theKey);
        createLabel(controlsRow, "/", DEFAULT_FONT);
        createKeyLabel(controlsRow, theAltKey);
        createLabel(controlsRow, LABEL_SEPARATOR + theDesc, DEFAULT_FONT);
        theParent.add(controlsRow);
    }

    /**
     * Helper method to create a key label and it to the panel.
     *
     * @param theTarget the element that will contain the label.
     * @param theKey the textual representation of the key.
     */
    private void createKeyLabel(final JComponent theTarget, final String theKey) {
        final BorderedLabel label = new BorderedLabel(theKey);
        label.setFont(DEFAULT_FONT);
        theTarget.add(label);
        myKeyLabels.add(label);
    }

    /**
     * Helper method to create a label and it to the panel.
     *
     * @param theTarget the element that will contain the label.
     * @param theStr the string of the label.
     */
    private void createLabel(
        final JComponent theTarget,
        final String theStr,
        final Font theFont
    ) {
        final JLabel label = new JLabel(theStr);
        label.setFont(theFont);
        theTarget.add(label);
        myDescLabels.add(label);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (theEvent.getPropertyName().equals(Appearance.UPDATE_THEME_PROP_CHANGE)) {
            setTheme((String) theEvent.getNewValue());
        }
    }

    private void setTheme(final String theTheme) {
        if (theTheme.equals(Appearance.DARK)) {
            setBackground(DARK_BACKGROUND);
            for (final BorderedLabel label : myKeyLabels) {
                label.setForeground(Color.WHITE);
                label.setColor(Color.LIGHT_GRAY, Color.DARK_GRAY);
            }
            for (final JLabel label : myDescLabels) {
                label.setForeground(Color.WHITE);
            }
        } else {
            setBackground(LIGHT_BACKGROUND);
            for (final BorderedLabel label : myKeyLabels) {
                label.setForeground(Color.BLACK);
                label.setColor(Color.BLACK, Color.LIGHT_GRAY);
            }
            for (final JLabel label : myDescLabels) {
                label.setForeground(Color.BLACK);
            }
        }
    }

    private static final class BorderedLabel extends JLabel {
        /**
         * Border's radius of the label.
         */
        static final int RADIUS = 10;

        /**
         * Border's width of the label.
         */
        static final int BORDER_WIDTH = 2;

        /**
         * Horizontal padding of the label.
         */
        static final int HORIZONTAL_PADDING = 9;

        /**
         * Vertical padding of the label.
         */
        static final int VERTICAL_PADDING = 6;

        /**
         * The border color.
         */
        private Color myBorderColor;

        /**
         * The background.
         */
        private Color myBackground;

        /**
         * Default constructor for bordered label.
         * @param theStr the content of the label.
         */
        BorderedLabel(final String theStr) {
            super(theStr);
            setBorder(
                new EmptyBorder(VERTICAL_PADDING, HORIZONTAL_PADDING,
                        VERTICAL_PADDING, HORIZONTAL_PADDING)
            );

            myBorderColor = Color.BLACK;
            myBackground = Color.LIGHT_GRAY;
        }

        /**
         * Set the color of the label.
         * @param theBorderColor the border color of the label.
         * @param theBackground the background color of the label.
         */
        public void setColor(final Color theBorderColor, final Color theBackground) {
            myBorderColor = theBorderColor;
            myBackground = theBackground;
            repaint();
        }

        @Override
        protected void paintComponent(final Graphics theGraphics) {
            final Graphics2D g2d = (Graphics2D) theGraphics.create();

            g2d.setColor(myBackground);
            g2d.fillRoundRect(
                    BORDER_WIDTH / 2, BORDER_WIDTH / 2,
                    getWidth() - BORDER_WIDTH, getHeight() - BORDER_WIDTH,
                    RADIUS, RADIUS
            );
            g2d.setColor(myBorderColor);
            g2d.setStroke(new BasicStroke(BORDER_WIDTH));
            g2d.drawRoundRect(
                    BORDER_WIDTH / 2, BORDER_WIDTH / 2,
                    getWidth() - BORDER_WIDTH, getHeight() - BORDER_WIDTH,
                    RADIUS, RADIUS
            );

            super.paintComponent(g2d);

            g2d.dispose();
        }
    }
}