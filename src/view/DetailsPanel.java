package view;

import static model.Appearance.DARK_BACKGROUND;
import static model.Appearance.LIGHT_BACKGROUND;
import static model.Appearance.DEFAULT_FONT;
import static model.Appearance.DEFAULT_PADDING;
import static model.Appearance.UPDATE_THEME_PROP_CHANGE;
import static model.Board.LINE_COUNT_MULTIPLIER;
import static model.Board.SCORE_PER_DROP;
import static model.Board.LINES_PER_LEVEL;
import static model.PropertyChangeEnabledBoard.ROW_FILLED_PROP_CHANGE;
import static model.PropertyChangeEnabledBoard.NEXT_PIECE_UPDATE_PROP_CHANGE;
import static model.PropertyChangeEnabledBoard.NEW_GAME_PROP_CHANGE;
import static model.PropertyChangeEnabledBoard.GAME_OVER_PROP_CHANGE;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import model.Appearance;
import model.AppearanceClass;
import model.BoardClass;
import model.ProfileClass;

/**
 * Class for creating a details panel.
 *
 * @author Dmitry Khotinskiy
 * @author Aly Badr
 * @author Brandon Phan
 * @author Lucas Jeong
 * @version 1.0.0
 */
public final class DetailsPanel extends JPanel implements PropertyChangeListener {
    /**
     * The integer representation of how many lines the user has cleared.
     */
    private int myLines;

    /**
     * The integer representation of how many total lines the user has cleared
     * (used to calculate levels).
     */
    private int myLeftoverLines;

    /**
     * The integer representation of what level the user is on.
     */
    private int myLevel;

    /**
     * The integer of the user's score.
     */
    private int myScore;

    /**
     * A Jlabel representing the number of lines cleared to reach the next level.
     */
    private JLabel myLeftoverLinesText;

    /**
     * A Jlabel representing the number of lines cleared.
     */
    private JLabel myLinesText;

    /**
     * A Jlabel representing the number of levels the user is on.
     */
    private JLabel myLevelText;

    /**
     * A Jlabel representing the score.
     */
    private JLabel myScoreText;

    /**
     * The labels on the screen.
     */
    private final ArrayList<JLabel> myLabels = new ArrayList<>();

    /**
     * Main constructor of the details panel.
     *
     * @param theBoard is the Boardclass
     */
    public DetailsPanel(final BoardClass theBoard) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(DEFAULT_PADDING, 2, DEFAULT_PADDING - 2, DEFAULT_PADDING));
        setBackground(new Color(0, 0, 0, 0));
        layoutComponents();
        AppearanceClass.getInstance().addPropertyChangeListener(this);
    }

    /**
     * Layouts details panel with score, lavel, etc.
     */
    private void layoutComponents() {
        final String dash = "-";

        final JPanel scorePanel = setUpScorePanel();
        myScoreText = createLabel(scorePanel, dash);

        final JPanel levelPanel = setUpLevelPanel();
        myLevelText = createLabel(levelPanel, dash);

        final JPanel linesPanel = setUpLinesPanel();
        myLinesText = createLabel(linesPanel, dash);

        final JPanel leftoverLinesPanel = setUpLeftoverLinesPanel();
        myLeftoverLinesText = createLabel(leftoverLinesPanel, dash);
    }

    private JPanel setUpScorePanel() {
        final JPanel scorePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 2));
        scorePanel.setBackground(LIGHT_BACKGROUND);
        createLabel(scorePanel, "Score: ");
        add(scorePanel);
        return scorePanel;
    }

    private JPanel setUpLevelPanel() {
        final JPanel levelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 2));
        levelPanel.setBackground(LIGHT_BACKGROUND);
        createLabel(levelPanel, "Level: ");
        add(levelPanel);
        return levelPanel;
    }

    private JPanel setUpLinesPanel() {
        final JPanel linesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 2));
        linesPanel.setBackground(LIGHT_BACKGROUND);
        createLabel(linesPanel, "Lines: ");
        add(linesPanel);
        return linesPanel;
    }

    private JPanel setUpLeftoverLinesPanel() {
        final JPanel leftoverLinesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 2));
        leftoverLinesPanel.setBackground(LIGHT_BACKGROUND);
        createLabel(leftoverLinesPanel, "Lines until next level: ");
        add(leftoverLinesPanel);
        return leftoverLinesPanel;
    }

    private JLabel createLabel(
        final JComponent theTarget,
        final String theStr
    ) {
        final JLabel label = new JLabel(theStr);
        label.setFont(DEFAULT_FONT);
        theTarget.add(label);
        myLabels.add(label);
        return label;
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        switch (theEvent.getPropertyName()) {
            case ROW_FILLED_PROP_CHANGE:
                updateLines((int) theEvent.getNewValue());
                break;
            case NEXT_PIECE_UPDATE_PROP_CHANGE:
                increaseScore(SCORE_PER_DROP);
                break;
            case NEW_GAME_PROP_CHANGE:
                resetDetails();
                break;
            case GAME_OVER_PROP_CHANGE:
                ProfileClass.getInstance().addScore(myScore);
                break;
            case UPDATE_THEME_PROP_CHANGE:
                setTheme((String) theEvent.getNewValue());
                break;
            default:
        }
    }

    private void updateLines(final int theLineCount) {
        increaseScore(LINE_COUNT_MULTIPLIER[theLineCount - 1]);
        myLeftoverLines -= theLineCount;
        if (myLeftoverLines <= 0) {
            myLeftoverLines = LINES_PER_LEVEL - myLeftoverLines;
            myLevel++;
            Tetris.setDifficulty(myLevel);
        }
        myLines += theLineCount;

        myLinesText.setText(Integer.toString(myLines));
        myLeftoverLinesText.setText(Integer.toString(myLeftoverLines));
        myLevelText.setText(Integer.toString(myLevel));
    }

    private void increaseScore(final int theAdditionalScore) {
        myScore += theAdditionalScore;
        myScoreText.setText(Integer.toString(myScore));
    }

    private void resetDetails() {
        myScore = 0;
        myLines = 0;
        myLeftoverLines = LINES_PER_LEVEL;
        myLevel = 1;

        myScoreText.setText(Integer.toString(myScore));
        myLinesText.setText(Integer.toString(myLines));
        myLeftoverLinesText.setText(Integer.toString(myLeftoverLines));
        myLevelText.setText(Integer.toString(myLevel));
    }

    private void setTheme(final String theTheme) {
        if (theTheme.equals(Appearance.DARK)) {
            for (final Component component : getComponents()) {
                component.setBackground(DARK_BACKGROUND);
            }

            for (final JLabel label : myLabels) {
                label.setForeground(Color.WHITE);
            }
        } else {
            for (final Component component : getComponents()) {
                component.setBackground(LIGHT_BACKGROUND);
            }

            for (final JLabel label : myLabels) {
                label.setForeground(Color.BLACK);
            }
        }
    }
}