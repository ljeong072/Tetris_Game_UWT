package view;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import model.ProfileClass;

/**
 * Music class.
 *
 * @author Dmitry Khotinskiy
 * @author Aly Badr
 * @author Brandon Phan
 * @author Lucas Jeong
 * @version 1.0.0
 */
public class MusicClass implements Music {
    /**
     * A variable to hold the file path of game over sound.
     */
    public static final String GAME_OVER = "Assets//GameOver.wav";

    /**
     * A variable to hold the file path of the level up sound.
     */
    public static final String LEVEL_UP = "Assets//LevelUp.wav";

    /**
     * A variable to hold the file path of the row clear sound.
     */
    public static final String ROW_CLEAR = "Assets//RowClear.wav";

    /**
     * Instance of the profile.
     */
    private static final ProfileClass PROFILE = ProfileClass.getInstance();

    /**
     * Variable used to open the audio clips.
     */
    private Clip myMusic;

    /**
     * A string variable for the audio file path.
     */
    private final String myFilePath;

    /**
     * A string variable for the audio file path.
     */
    private boolean myIsMuted;

    /**
     * Constructor for the Music class that accepts the audio file path.
     *
     * @param theFilePath of type String.
     */
    public MusicClass(final String theFilePath) {
        super();
        this.myFilePath = theFilePath;
        final Logger logger = Logger.getLogger(PROFILE.getName());
        try {
            final File audioFile = new File(theFilePath);
            final AudioInputStream gamePlayAudio =
                    AudioSystem.getAudioInputStream(audioFile);

            myMusic = AudioSystem.getClip();
            myMusic.open(gamePlayAudio);
        } catch (final UnsupportedAudioFileException
                       | LineUnavailableException | IOException e
        ) {
            logger.log(Level.SEVERE, "An error occurred", e);
        }
    }

    @Override
    public Clip getClip() {
        return myMusic;
    }

    @Override
    public void playMusic() {
        if (!myMusic.isRunning() && !GAME_OVER.equals(this.myFilePath)
                && !LEVEL_UP.equals(this.myFilePath)
                && !ROW_CLEAR.equals(this.myFilePath)) {
            myMusic.start();
            myMusic.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            myMusic.start();
        }
    }

    @Override
    public void restartMusic() {
        myMusic.setFramePosition(0);
    }

    @Override
    public void stopMusic() {
        if (myMusic.isRunning() && myMusic != null) {
            myMusic.stop();
        }
    }

    @Override
    public boolean isMuted() {
        return myIsMuted;
    }

    @Override
    public void toggleMuteState() {
        myIsMuted = !myIsMuted;
    }
}