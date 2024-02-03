package view;

import static model.Appearance.DARK_BACKGROUND;
import static model.Appearance.HEADER_FONT;
import static model.Appearance.LIGHT;
import static model.Appearance.LIGHT_BACKGROUND;
import static model.Profile.CURRENT_KEY;
import static model.Profile.NAME_KEY;
import static model.Profile.SCORE_KEY;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import model.Appearance;
import model.AppearanceClass;
import model.Profile;
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
public final class LeaderboardFrame extends JPanel implements PropertyChangeListener {
    /**
     * Create window for game controls with a title.
     */
    static final JFrame WINDOW = new JFrame("");

    /**
     * Minimum window size.
     */
    static final Dimension MIN_WINDOW_SIZE = new Dimension(300, 160);

    /**
     * Default font of the labels.
     */
    static final String FONT_FAMILY = "PT Mono";

    /**
     * Default font of the labels.
     */
    static final Font DEFAULT_FONT = new Font(FONT_FAMILY, Font.PLAIN, 13);

    /**
     * Bold font of the labels.
     */
    static final Font BOLD_FONT = new Font(FONT_FAMILY, Font.BOLD, 13);

    /**
     * Default font of the labels.
     */
    static final int ROW_LENGTH = 50;

    /**
     * Instance of application's appearance class.
     */
    private static final AppearanceClass APPEARANCE = AppearanceClass.getInstance();

    /**
     * Instance of application's appearance class.
     */
    private static final ProfileClass PROFILE = ProfileClass.getInstance();

    /**
     * The dimensions of the score panel.
     */
    private static final Dimension SCORE_PANEL_DIMENSIONS = new Dimension(100, 10);

    /**
     * The border of the score panel.
     */
    private static final Border SCORE_PANEL_BORDER = new EmptyBorder(10, 10, 5, 10);

    /**
     * The border of the label.
     */
    private static final Border LABEL_BORDER = new EmptyBorder(5, 5, 5, 5);

    /**
     * The labels on the screen.
     */
    private JPanel myScoresPanel;

    /**
     * The labels on the screen.
     */
    private final ArrayList<JLabel> myLabels = new ArrayList<>();

    /**
     * The scores with user details.
     */
    private final ArrayList<Map<String, Object>> myScores = new ArrayList<>();

    /**
     * Constructor for game controls.
     */
    public LeaderboardFrame() {
        super();
        layoutComponents();
        APPEARANCE.addPropertyChangeListener(this);
        PROFILE.addPropertyChangeListener(this);
    }

    /**
     * Method for creating a main window of the tetris game.
     */
    public static void createGUI() {
        final LeaderboardFrame mainPanel = new LeaderboardFrame();

        // Set the close behavior for the main window
        WINDOW.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // Adds content to the main window
        WINDOW.setContentPane(mainPanel);
        WINDOW.setJMenuBar(new MenuBar());
        WINDOW.setMinimumSize(MIN_WINDOW_SIZE);
        WINDOW.setResizable(false);

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
        createLabel(headerPanel, "Leaderboard", HEADER_FONT);
        add(headerPanel, BorderLayout.NORTH);

        // Create score panel
        myScoresPanel = new JPanel();
        myScoresPanel.setBackground(new Color(0, 0, 0, 0));
        myScoresPanel.setLayout(new BoxLayout(myScoresPanel, BoxLayout.Y_AXIS));
        myScoresPanel.setPreferredSize(SCORE_PANEL_DIMENSIONS);

        // Create a message that says there are no scores
        final JPanel missingScoresPanel = new JPanel();
        createLabel(missingScoresPanel, "There are no scores yet!", DEFAULT_FONT);
        createLabel(missingScoresPanel, "Go play some tetris first!", DEFAULT_FONT);
        missingScoresPanel.setBackground(new Color(0, 0, 0, 0));
        myScoresPanel.add(missingScoresPanel);
        add(myScoresPanel, BorderLayout.CENTER);
    }

    private void printScores() {
        if (!myScores.isEmpty()) {
            // Create a wrapper element for scores
            remove(myScoresPanel);
            myScoresPanel = new JPanel();
            myScoresPanel.setBackground(new Color(0, 0, 0, 0));
            myScoresPanel.setLayout(new BoxLayout(myScoresPanel, BoxLayout.Y_AXIS));
            myScoresPanel.setBorder(SCORE_PANEL_BORDER);
            add(myScoresPanel, BorderLayout.CENTER);

            // Add scores to the panel
            myScores.sort(Comparator.comparingInt(o -> -((int) o.get(SCORE_KEY))));
            myScores.forEach(item -> createScoreLabel(myScoresPanel, item));

            // Update the visual of the main panel
            revalidate();
            repaint();

            // Resize the window down to the size of it's contents
            WINDOW.pack();
        }
    }

    private void createScoreLabel(
            final JComponent theTarget,
            final Map<String, Object> theItem
    ) {
        // Get score info
        final String name = (String) theItem.get(NAME_KEY);
        final Integer score = (Integer) theItem.get(SCORE_KEY);
        final boolean current = (boolean) theItem.get(CURRENT_KEY);
        final int leftoverLength = ROW_LENGTH - name.length() - score.toString().length();

        Font currentIndicator = DEFAULT_FONT;
        if (current) {
            currentIndicator = BOLD_FONT;
        }

        Color labelColor = Color.WHITE;
        if (APPEARANCE.getTheme().equals(LIGHT)) {
            labelColor = Color.BLACK;
        }

        // Create a row with a score
        final JLabel label = createLabel(theTarget,
                name + ".".repeat(leftoverLength) + score, currentIndicator);
        label.setForeground(labelColor);
        label.setBorder(LABEL_BORDER);
    }

    private JLabel createLabel(
            final JComponent theTarget,
            final String theStr,
            final Font theFont
    ) {
        final JLabel label = new JLabel(theStr);
        label.setFont(theFont);
        theTarget.add(label);
        myLabels.add(label);
        return label;
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (theEvent.getPropertyName().equals(Appearance.UPDATE_THEME_PROP_CHANGE)) {
            setTheme((String) theEvent.getNewValue());
        } else if (theEvent.getPropertyName().equals(Profile.NEW_SCORE_PROP_CHANGE)) {
            // Remove current state from the scores
            removeCurrentProp();

            // Add a score to the array
            final Map<String, Object> newLeaderboardItem = new HashMap<>();
            newLeaderboardItem.put(NAME_KEY, PROFILE.getName());
            newLeaderboardItem.put(SCORE_KEY, theEvent.getNewValue());
            newLeaderboardItem.put(CURRENT_KEY, true);
            myScores.add(newLeaderboardItem);

            printScores();
        }
    }

    private void removeCurrentProp() {
        myScores.forEach(item -> item.put(CURRENT_KEY, false));
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