package view;

import static model.Appearance.DARK_BACKGROUND;
import static model.Appearance.LIGHT_BACKGROUND;
import static model.Board.STEP_INTERVAL;
import static view.MusicClass.GAME_OVER;
import static view.MusicClass.LEVEL_UP;
import static view.MusicClass.ROW_CLEAR;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serial;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import model.Appearance;
import model.AppearanceClass;
import model.BoardClass;
import model.Profile;
import model.ProfileClass;
import model.PropertyChangeEnabledBoard;
import model.TetrisPiece;

/**
 * Main class for creating a tetris application.
 *
 * @author Dmitry Khotinskiy
 * @author Aly Badr
 * @author Brandon Phan
 * @author Lucas Jeong
 * @version 1.0.0
 */
public final class Tetris extends JPanel implements PropertyChangeListener, KeyListener {
    /**
     * Create main window and give it a title.
     */
    static final JFrame WINDOW = new JFrame("Tetris");

    /**
     * Instance of board class.
     */
    private static final BoardClass BOARD = BoardClass.getInstance();

    /**
     * Instance of appearance class.
     */
    private static final AppearanceClass APPEARANCE = AppearanceClass.getInstance();

    /**
     * Instance of the profile.
     */
    private static final ProfileClass PROFILE = ProfileClass.getInstance();

    @Serial
    private static final long serialVersionUID = -1155574959121886543L;

    /**
     * Implementation of the timer.
     */
    private static final Timer TIMER = new Timer(1, action -> BOARD.step());

    /**
     * A variable that stores the main game sound.
     */
    private static final MusicClass MUSIC_PLAYER = new MusicClass("Assets//Tetris.wav");

    /**
     * A variable that stores the game over sound.
     */
    private static final MusicClass GAME_OVER_SOUND = new MusicClass(GAME_OVER);

    /**
     * A variabel that stores the level up sound.
     */
    private static final MusicClass LEVEL_UP_SOUND = new MusicClass(LEVEL_UP);

    /**
     * A variable that stores the row clear sound.
     */
    private static final MusicClass ROW_CLEAR_SOUND = new MusicClass(ROW_CLEAR);

    /**
     * Variable for myNextPiece.
     */
    private TetrisPiece myNextPiece;

    /**
     * Constructor for tetris.
     */
    public Tetris() {
        super();
        WINDOW.addKeyListener(this);
        PROFILE.addPropertyChangeListener(this);
        APPEARANCE.addPropertyChangeListener(this);

        setBackground(Appearance.LIGHT_BACKGROUND);
        layoutComponents();
    }

    /**
     * Main class for launching application.
     */
    public static void main(final String[] theArgs) {
        /* Use an appropriate Look and Feel */
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "WikiTeX");
        javax.swing.SwingUtilities.invokeLater(Tetris::createWindows);
    }

    private static void createWindows() {
        Tetris.createGUI();
        NameFrame.createGUI();
        LeaderboardFrame.createGUI();
        GameControlsFrame.createGUI();
        AboutScoreFrame.createGUI();
        AboutMusicFrame.createGUI();
        PausedFrame.createGUI();
    }

    /**
     * Lay out the components and makes this frame visible.
     */
    private void layoutComponents() {
        setLayout(new BorderLayout());

        final JPanel gameWrapper = new GamePanel();
        add(gameWrapper);

        final JPanel sidePanel = new JPanel(new BorderLayout());
        sidePanel.setBackground(new Color(0, 0, 0, 0));
        add(sidePanel, BorderLayout.EAST);

        final NextPiecePanel nextPiecePanel = new NextPiecePanel();
        sidePanel.add(nextPiecePanel, BorderLayout.NORTH);

        final DetailsPanel detailpanel = new DetailsPanel(BOARD);
        BOARD.addPropertyChangeListener(detailpanel);
        sidePanel.add(detailpanel, BorderLayout.SOUTH);
        detailpanel.addPropertyChangeListener(this);
    }

    /**
     * Method for creating a main window of the tetris game.
     */
    public static void createGUI() {
        final Tetris mainPanel = new Tetris();

        // Set the close behavior for the main window
        WINDOW.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize property change listener for this window
        BOARD.addPropertyChangeListener(mainPanel);

        // Adds content to the main window
        WINDOW.setContentPane(mainPanel);
        WINDOW.setJMenuBar(new MenuBar());

        // Resize the window down to the size of it's contents.
        WINDOW.pack();

        // Center the window.
        WINDOW.setLocationRelativeTo(null);

        // Replace default about menu with a custom one
        final Desktop desktop = Desktop.getDesktop();
        if (desktop.isSupported(Desktop.Action.APP_ABOUT)) {
            desktop.setAboutHandler(theEvent -> showAboutDialog());
        }
    }

    private static void showAboutDialog() {
        JOptionPane.showMessageDialog(Tetris.WINDOW,
                """
                        Application developed by:
                        
                        Aly Badr
                        Brandon Phan
                        Dmitry Khotinskiy
                        Lucas Jeong
                        """,
                "About Tetris",
                JOptionPane.INFORMATION_MESSAGE);
    }

    //my code
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        switch (theEvent.getPropertyName()) {
            case PropertyChangeEnabledBoard.NEW_GAME_PROP_CHANGE -> {
                TIMER.setDelay(STEP_INTERVAL);
                musicPropertyChange(PropertyChangeEnabledBoard.NEW_GAME_PROP_CHANGE);
            }
            case PropertyChangeEnabledBoard.GAME_OVER_PROP_CHANGE -> {
                endGame(false);
                MUSIC_PLAYER.stopMusic();
                musicPropertyChange(PropertyChangeEnabledBoard.GAME_OVER_PROP_CHANGE);
            }
            case PropertyChangeEnabledBoard.ROW_FILLED_PROP_CHANGE ->
                    musicPropertyChange(PropertyChangeEnabledBoard.ROW_FILLED_PROP_CHANGE);
            case Profile.SET_NAME_PROP_CHANGE -> WINDOW.setVisible(true);
            case Appearance.UPDATE_THEME_PROP_CHANGE ->
                    setTheme((String) theEvent.getNewValue());
            default -> { }
        }
    }

    private void musicPropertyChange(final String theKey) {
        switch (theKey) {
            case PropertyChangeEnabledBoard.NEW_GAME_PROP_CHANGE:
                MUSIC_PLAYER.restartMusic();
                MUSIC_PLAYER.playMusic();
                break;
            case PropertyChangeEnabledBoard.GAME_OVER_PROP_CHANGE:
                GAME_OVER_SOUND.restartMusic();
                GAME_OVER_SOUND.playMusic();
                break;
            case PropertyChangeEnabledBoard.ROW_FILLED_PROP_CHANGE:
                ROW_CLEAR_SOUND.restartMusic();
                ROW_CLEAR_SOUND.playMusic();
                break;
            default:
        }
    }

    private void setTheme(final String theTheme) {
        if (theTheme.equals(Appearance.DARK)) {
            setBackground(DARK_BACKGROUND);
        } else {
            setBackground(LIGHT_BACKGROUND);
        }
    }

    @Override
    public void keyTyped(final KeyEvent theEvent) {
    }

    @Override
    public void keyReleased(final KeyEvent theKeyEvent) {
    }

    @Override
    public void keyPressed(final KeyEvent theEvent) {
        if (TIMER != null && TIMER.isRunning()) {
            getKeyEvents(theEvent.getKeyCode());
        }
    }

    private void getKeyEvents(final int theKeyCode) {
        if (leftKeyEvent(theKeyCode)) {
            BOARD.left();
        } else if (rightKeyEvent(theKeyCode)) {
            BOARD.right();
        } else if (upKeyEvent(theKeyCode)) {
            BOARD.rotateCW();
        } else if (downKeyEvent(theKeyCode)) {
            BOARD.down();
        } else if (theKeyCode == KeyEvent.VK_SHIFT) {
            BOARD.rotateCCW();
        } else if (theKeyCode == KeyEvent.VK_SPACE) {
            BOARD.drop();
        }
    }

    private boolean leftKeyEvent(final int theKeyCode) {
        return theKeyCode == KeyEvent.VK_LEFT || theKeyCode == KeyEvent.VK_A;
    }
    private boolean rightKeyEvent(final int theKeyCode) {
        return theKeyCode == KeyEvent.VK_RIGHT || theKeyCode == KeyEvent.VK_D;
    }

    private boolean upKeyEvent(final int theKeyCode) {
        return theKeyCode == KeyEvent.VK_UP || theKeyCode == KeyEvent.VK_W;
    }

    private boolean downKeyEvent(final int theKeyCode) {
        return theKeyCode == KeyEvent.VK_DOWN || theKeyCode == KeyEvent.VK_S;
    }


    /**
     * Starts the game.
     */
    static void startGame() {
        if (TIMER.getDelay() == 1) {
            // Initialize new game in the Board class
            BOARD.newGame();

            // Initialize timer
            TIMER.setDelay(STEP_INTERVAL);
            TIMER.start();
        }
    }

    /**
     * Pauses the game.
     */
    static void togglePause() {
        if (TIMER.getDelay() != 1) {
            if (TIMER.isRunning()) {
                TIMER.stop();
                MUSIC_PLAYER.stopMusic();
                WINDOW.setVisible(false);
                PausedFrame.WINDOW.setVisible(true);
            } else {
                TIMER.start();
                WINDOW.setVisible(true);
                PausedFrame.WINDOW.setVisible(false);
                if (!MUSIC_PLAYER.isMuted()) {
                    MUSIC_PLAYER.playMusic();
                }
            }
        }
    }
    static void muteMusic() {
        if (MUSIC_PLAYER.getClip() != null && MUSIC_PLAYER.getClip().isRunning()
                        && TIMER.isRunning()) {
            MUSIC_PLAYER.getClip().stop();
            MUSIC_PLAYER.toggleMuteState();
        } else if (TIMER.isRunning()) {
            MUSIC_PLAYER.playMusic();
            MUSIC_PLAYER.toggleMuteState();
        }
    }

    /**
     * Stops the game.
     */
    static void endGame(final boolean theForceEnd) {
        if (TIMER.isRunning()) {
            TIMER.setDelay(1);

            if (!theForceEnd) {
                TIMER.stop();
                MUSIC_PLAYER.stopMusic();
            }
        }
    }

    /**
     * A method that increases the difficulty of the game progressionally.
     * Once the game reaches the threshold of 6 then stop decreasing delay.
     * @param theLevel of type int.
     */
    public static void setDifficulty(final int theLevel) {
        final int x = 100;
        final int level = Math.min(theLevel, 6);
        TIMER.setDelay(STEP_INTERVAL - (level * x));
        LEVEL_UP_SOUND.playMusic();
        LEVEL_UP_SOUND.restartMusic();
    }
}