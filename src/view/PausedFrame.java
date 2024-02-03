package view;

import static model.Appearance.DARK_BACKGROUND;
import static model.Appearance.HEADER_FONT;
import static model.Appearance.LIGHT_BACKGROUND;

import java.awt.Color;
import java.awt.FlowLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import model.Appearance;
import model.AppearanceClass;

/**
 * Main class for creating a paused window.
 *
 * @author Dmitry Khotinskiy
 * @author Aly Badr
 * @author Brandon Phan
 * @author Lucas Jeong
 * @version 1.0.0
 */
public final class PausedFrame extends JPanel implements PropertyChangeListener {
    /**
     * Create window for game controls with a title.
     */
    static final JFrame WINDOW = new JFrame("Tetris");

    /**
     * Instance of application's appearance class.
     */
    private static final AppearanceClass APPEARANCE = AppearanceClass.getInstance();

    /**
     * The labels on the screen.
     */
    private final ArrayList<JLabel> myLabels = new ArrayList<>();

    /**
     * Constructor for game controls.
     */
    public PausedFrame() {
        super();
        layoutComponents();
        APPEARANCE.addPropertyChangeListener(this);
    }

    /**
     * Method for creating a main window of the tetris game.
     */
    public static void createGUI() {
        final PausedFrame mainPanel = new PausedFrame();

        // Set the close behavior for the main window
        WINDOW.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Adds content to the main window
        WINDOW.setContentPane(mainPanel);
        WINDOW.setJMenuBar(new MenuBar());
        WINDOW.setResizable(false);
        WINDOW.pack();

        // Center the window at the location of the main window
        WINDOW.setLocationRelativeTo(Tetris.WINDOW);
    }

    /**
     * Lay out the components and makes this frame visible.
     */
    private void layoutComponents() {
        setBackground(LIGHT_BACKGROUND);

        final int padding = 15;
        setBorder(new EmptyBorder(padding, padding, padding, padding));

        // Create a title for the window
        final JPanel headerPanel = new JPanel(new FlowLayout());
        headerPanel.setBackground(new Color(0, 0, 0, 0));
        headerPanel.setBorder(new EmptyBorder(0, padding, padding, padding));
        final JLabel label = new JLabel("Paused");
        label.setFont(HEADER_FONT);
        headerPanel.add(label);
        myLabels.add(label);
        add(headerPanel);
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
            for (final JLabel label : myLabels) {
                label.setForeground(Color.WHITE);
            }
        } else {
            setBackground(LIGHT_BACKGROUND);
            for (final JLabel label : myLabels) {
                label.setForeground(Color.BLACK);
            }
        }
    }
}