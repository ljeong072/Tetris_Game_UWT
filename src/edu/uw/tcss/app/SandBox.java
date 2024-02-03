package edu.uw.tcss.app;

import java.util.logging.Level;
import java.util.logging.Logger;
import model.BoardClass;

/**
 package edu.uw.tcss.app;

 import java.util.logging.Level;
 import java.util.logging.Logger;
 import model.BoardClass;

 /**
 * Sandbox class to get experience with Git.
 *
 * @author Dmitry Khotinskiy
 * @author Aly Badr
 * @author Brandon Phan
 * @author Lucas Jeong
 * @version 1.0.0
 */
public final class SandBox {
    /**
     * A logger object to log the data.
     */
    private static final Logger LOGGER = Logger.getLogger(SandBox.class.getName());

    static {
        LOGGER.setLevel(Level.ALL);
    }

    /**
     * Private constructor to prevent construction of instances.
     */
    private SandBox() {
        super();
    }

    /**
     * Default constructor that initiates SandBox.
     *
     * @param theArgs Command line arguments (ignored).
     */
    public static void main(final String[] theArgs) {
        final BoardClass b = BoardClass.getInstance();
        b.newGame();
        LOGGER.info(b.toString());

        b.step();
        LOGGER.info(b.toString());
        b.rotateCW();
        LOGGER.info(b.toString());
        b.rotateCW();
        LOGGER.info(b.toString());
        b.rotateCW();
        LOGGER.info(b.toString());
        b.rotateCW();
        LOGGER.info(b.toString());
        b.drop();
        LOGGER.info(b.toString());
    }
}