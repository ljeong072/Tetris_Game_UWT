package view;



import static model.Appearance.LIGHT_BACKGROUND;
import static model.Appearance.DARK_BACKGROUND;
import static model.Appearance.HEADER_FONT;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
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
 * Main class for creating a about Music window.
 *
 * @author Dmitry Khotinskiy
 * @author Aly Badr
 * @author Brandon Phan
 * @author Lucas Jeong
 * @version 1.0.0
 */
public final class AboutMusicFrame extends JPanel implements PropertyChangeListener {
    /**
     * Create window for game controls with a title.
     */
    static final JFrame WINDOW = new JFrame("");

    /**
     * Default font of the labels.
     */
    static final Font DEFAULT_FONT = new Font("Calibri", Font.PLAIN, 13);

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
    public AboutMusicFrame() {
        super();
        layoutComponents();
        APPEARANCE.addPropertyChangeListener(this);
    }

    /**
     * Method for creating a main window of the tetris game.
     */
    public static void createGUI() {
        final view.AboutMusicFrame mainPanel = new view.AboutMusicFrame();

        // Set the close behavior for the main window
        WINDOW.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // Adds content to the main window
        WINDOW.setContentPane(mainPanel);
        WINDOW.setJMenuBar(new MenuBar());
        WINDOW.setResizable(false);
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

        // Create a title for the window
        final JPanel headerPanel = new JPanel(new FlowLayout());
        headerPanel.setBackground(new Color(0, 0, 0, 0));
        headerPanel.setBorder(new EmptyBorder(0, padding, padding, padding));
        createLabel(headerPanel, "About Music", HEADER_FONT);
        add(headerPanel, BorderLayout.NORTH);

        final JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(0, 0, 0, 0));
        contentPanel.setBorder(new EmptyBorder(padding, padding, padding / 2, padding));
        createDetails(contentPanel);
        add(contentPanel, BorderLayout.CENTER);
    }

    private void createDetails(final JPanel theParent) {
        final String emptyLineStr = " ";

        createLabel(theParent, "Each sound in this game was implemented "
                + "using the SOUND class in java.", DEFAULT_FONT);
        createLabel(theParent, emptyLineStr, DEFAULT_FONT);
        createLabel(theParent, "The specific classes used in the SOUND class "
                + "was AudioInputStream, Clip , and AudioSystem", DEFAULT_FONT);
        createLabel(theParent, emptyLineStr, DEFAULT_FONT);

        createLabel(theParent, "<html>The game over sound was legally borrowed from: "
            + "<a href=\"https://opengameart.org/content/512-sound-effects-8-bit-style\">"
            + "https://opengameart.org/content/512-sound-effects-8-bit-style</a></html>",
            DEFAULT_FONT);
        createLabel(theParent, emptyLineStr, DEFAULT_FONT);

        createLabel(theParent, "<html>The main Tetris music was legally borrowed from: "
            + "<a href=\"https://www.101soundboards.com/boards/10378-tetris-sounds\">"
            + "https://www.101soundboards.com/boards/10378-tetris-sounds</a></html>",
            DEFAULT_FONT);
        createLabel(theParent, emptyLineStr, DEFAULT_FONT);

        createLabel(theParent, "<html>The level up sound was legally borrowed from: "
            + "<a href=\"https://www.myinstants.com/en/search/?name=pokemon\">"
            + "https://www.myinstants.com/en/search/?name=pokemon</a></html>",
            DEFAULT_FONT);
    }

    private void createLabel(
            final JComponent theTarget,
            final String theStr,
            final Font theFont
    ) {
        final JLabel label = new JLabel(theStr);
        label.setFont(theFont);
        theTarget.add(label);
        myLabels.add(label);
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
