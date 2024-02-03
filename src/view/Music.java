package view;

import javax.sound.sampled.Clip;

/**
 * An interface for music.
 *
 * @author Dmitry Khotinskiy
 * @author Aly Badr
 * @author Brandon Phan
 * @author Lucas Jeong
 * @version 1.0.0
 */
public interface Music {
    /**
     * Getter for the application's appearance class.
     * @return Instance of application's appearance class.
     */
    Clip getClip();

    /**
     * Method to play the music and loop if necessary.
     * If the audio clip is the main audio then loop it until the audio
     * changes to the game over clip that will place once at the end of the game.
     */
    void playMusic();

    /**
     * A method to restart the audio clips from the beginning.
     */
    void restartMusic();

    /**
     * Method to stop the audio clips.
     */
    void stopMusic();

    /**
     * Get the muted state of the music.
     */
    boolean isMuted();

    /**
     * Toggle mute state of the music.
     */
    void toggleMuteState();
}