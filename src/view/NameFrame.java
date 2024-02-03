package view;

import static model.Appearance.DARK_BACKGROUND;
import static model.Appearance.HEADER_FONT;
import static model.Appearance.LIGHT_BACKGROUND;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import model.Appearance;
import model.AppearanceClass;
import model.ProfileClass;

/**
 * Main class for creating a leaderboard window.
 *
 * @author Dmitry Khotinskiy
 * @author Aly Badr
 * @author Brandon Phan
 * @author Lucas Jeong
 * @version 1.0.0
 */
public final class NameFrame extends JPanel implements PropertyChangeListener {
    /**
     * Create window for game controls with a title.
     */
    static final JFrame WINDOW = new JFrame("");

    /**
     * Minimum window size.
     */
    static final Dimension MIN_WINDOW_SIZE = new Dimension(400, 195);

    /**
     * Default font of the labels.
     */
    static final Font DEFAULT_FONT = new Font("Calibri", Font.PLAIN, 13);

    /**
     * Header label.
     */
    static final JLabel HEADER_LABEL = new JLabel("What's your name?");

    /**
     * Name field.
     */
    static final BorderedInput NAME_FIELD = new BorderedInput();

    /**
     * Wrapper of the submit button.
     */
    static final JPanel SUBMIT_WRAPPER = new JPanel(new FlowLayout(FlowLayout.RIGHT));

    /**
     * Border of the wrapper for submit button.
     */
    static final Border SUBMIT_WRAPPER_BORDER = new EmptyBorder(10, 0, 0, 0);

    /**
     * Submit button.
     */
    static final JButton SUBMIT_BUTTON = new JButton("Submit");

    /**
     * Instance of application's appearance class.
     */
    private static final AppearanceClass APPEARANCE = AppearanceClass.getInstance();

    /**
     * Instance of application's appearance class.
     */
    private static final ProfileClass PROFILE = ProfileClass.getInstance();

    /**
     * Constructor for game controls.
     */
    public NameFrame() {
        super();
        layoutComponents();
        APPEARANCE.addPropertyChangeListener(this);
        PROFILE.addPropertyChangeListener(this);
    }

    /**
     * Method for creating a main window of the tetris game.
     */
    public static void createGUI() {
        final NameFrame mainPanel = new NameFrame();

        // Set the close behavior for the main window
        WINDOW.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Customize the frame of the window
        WINDOW.setContentPane(mainPanel);
        WINDOW.setJMenuBar(new MenuBar());
        WINDOW.setMinimumSize(MIN_WINDOW_SIZE);
        WINDOW.setResizable(false);
        WINDOW.setVisible(true);

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

        // Create a title for the window
        final JPanel headerPanel = new JPanel(new FlowLayout());
        headerPanel.setBackground(new Color(0, 0, 0, 0));
        headerPanel.setBorder(new EmptyBorder(0, padding, padding, padding));
        HEADER_LABEL.setFont(HEADER_FONT);
        headerPanel.add(HEADER_LABEL);
        add(headerPanel, BorderLayout.NORTH);

        // Create an input for the window
        NAME_FIELD.setBackground(new Color(0, 0, 0, 0));
        add(NAME_FIELD, BorderLayout.CENTER);

        // Create a submit button for the window
        SUBMIT_WRAPPER.setBorder(SUBMIT_WRAPPER_BORDER);
        SUBMIT_WRAPPER.setBackground(LIGHT_BACKGROUND);
        add(SUBMIT_WRAPPER, BorderLayout.SOUTH);

        SUBMIT_BUTTON.addActionListener(this::setName);
        SUBMIT_WRAPPER.add(SUBMIT_BUTTON);
    }

    private void setName(final ActionEvent theEvent) {
        WINDOW.setVisible(false);
        WINDOW.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        PROFILE.setName(NAME_FIELD.getText());
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
            SUBMIT_WRAPPER.setBackground(DARK_BACKGROUND);
            HEADER_LABEL.setForeground(Color.WHITE);
            NAME_FIELD.setForeground(Color.WHITE);
            NAME_FIELD.setColor(Color.GRAY, Color.DARK_GRAY);
        } else {
            setBackground(LIGHT_BACKGROUND);
        }
    }

    private static final class BorderedInput extends JTextField {
        /**
         * Border's radius of the label.
         */
        static final int RADIUS = 10;

        /**
         * Border's width of the label.
         */
        static final int BORDER_WIDTH = 1;

        /**
         * Horizontal padding of the label.
         */
        static final int HORIZONTAL_PADDING = 20;

        /**
         * Vertical padding of the label.
         */
        static final int VERTICAL_PADDING = 5;

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
         */
        BorderedInput() {
            super();
            setBorder(
                new EmptyBorder(VERTICAL_PADDING, HORIZONTAL_PADDING,
                    VERTICAL_PADDING, HORIZONTAL_PADDING)
            );

            setDocument(new MaxLengthDocument());

            myBorderColor = Color.LIGHT_GRAY;
            myBackground = Color.WHITE;
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

            // Increase graphics display
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.setColor(myBackground);
            g2d.fillRoundRect(
                (HORIZONTAL_PADDING + BORDER_WIDTH) / 2, BORDER_WIDTH / 2,
                getWidth() - (HORIZONTAL_PADDING + BORDER_WIDTH), getHeight() - BORDER_WIDTH,
                RADIUS, RADIUS
            );
            g2d.setColor(myBorderColor);
            g2d.setStroke(new BasicStroke(BORDER_WIDTH));
            g2d.drawRoundRect(
                (HORIZONTAL_PADDING + BORDER_WIDTH) / 2, BORDER_WIDTH / 2,
                getWidth() - (HORIZONTAL_PADDING + BORDER_WIDTH), getHeight() - BORDER_WIDTH,
                RADIUS, RADIUS
            );

            super.paintComponent(g2d);

            g2d.dispose();
        }
    }

    private static final class MaxLengthDocument extends PlainDocument {
        /**
         * Max length of the string in the input field.
         */
        static final int MAX_LENGTH = 20;

        @Override
        public void insertString(final int theOffset, final String theStr,
                                 final AttributeSet theAttr) throws BadLocationException {
            if ((getLength() + theStr.length()) <= MAX_LENGTH) {
                super.insertString(theOffset, theStr, theAttr);
            }
        }
    }
}