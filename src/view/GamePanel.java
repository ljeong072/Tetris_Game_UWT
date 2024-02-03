package view;

import static model.Appearance.DARK;
import static model.Appearance.DARK_BACKGROUND;
import static model.Appearance.LIGHT_BACKGROUND;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import model.Appearance;
import model.AppearanceClass;
import model.Block;
import model.BoardClass;
import model.MovableTetrisPiece;
import model.Point;
import model.PropertyChangeEnabledBoard;
import model.Rotation;
import model.TetrisPiece;

/**
 * Class for creating a game panel.
 *
 * @author Dmitry Khotinskiy
 * @author Aly Badr
 * @author Brandon Phan
 * @author Lucas Jeong
 * @version 1.0.0
 */
public final class GamePanel extends JPanel implements PropertyChangeListener {
    /**
     * The height and the width of tetramino's square.
     */
    static final int SCALE_FACTOR = 40;

    /**
     * The padding of the wrapper.
     */
    static final double PADDING = 14;

    /**
     * Instance of board class.
     */
    private static final BoardClass BOARD = BoardClass.getInstance();

    /**
     * Instance of appearance class.
     */
    private static final AppearanceClass APPEARANCE = AppearanceClass.getInstance();

    /**
     * The border thickness of tetramino's square.
     */
    private static final GameContent GAME_CONTENT_PANEL = new GameContent();

    /**
     * Main constructor of the game panel.
     */
    public GamePanel() {
        super();
        setLayout(new FlowLayout(FlowLayout.CENTER, (int) PADDING, (int) PADDING));
        setBackground(new Color(0, 0, 0, 0));
        add(GAME_CONTENT_PANEL);
        APPEARANCE.addPropertyChangeListener(this);
    }

    /**
     * Paints graphics inside of the panel.
     */
    @Override
    protected void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D graphics = (Graphics2D) theGraphics;

        // Increase graphics display
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        Color lineColor = Color.BLACK;
        if (APPEARANCE.getTheme().equals(DARK)) {
            lineColor = Color.WHITE;
        }
        graphics.setPaint(lineColor);

        final Line2D leftLine = new Line2D.Double(PADDING - 2, PADDING + 1,
                PADDING - 2, BOARD.getHeight() * SCALE_FACTOR + PADDING - 1);
        graphics.draw(leftLine);

        final Line2D rightLine = new Line2D.Double(BOARD.getWidth() * SCALE_FACTOR
                + PADDING + 2, PADDING + 1, BOARD.getWidth() * SCALE_FACTOR
                + PADDING + 2, BOARD.getHeight() * SCALE_FACTOR + PADDING - 1);

        graphics.draw(rightLine);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (theEvent.getPropertyName().equals(Appearance.UPDATE_THEME_PROP_CHANGE)) {
            setTheme((String) theEvent.getNewValue());
        }
    }

    private void setTheme(final String theTheme) {
        if (theTheme.equals(Appearance.DARK)) {
            GAME_CONTENT_PANEL.setBackground(Appearance.DARK_BACKGROUND);
            GAME_CONTENT_PANEL.repaint();
            setBackground(DARK_BACKGROUND);
            repaint();
        } else {
            GAME_CONTENT_PANEL.setBackground(Appearance.LIGHT_BACKGROUND);
            GAME_CONTENT_PANEL.repaint();
            setBackground(LIGHT_BACKGROUND);
            repaint();
        }
    }

    private static final class GameContent extends JPanel implements PropertyChangeListener {
        /**
         * The border thickness of tetramino's square.
         */
        private static final int BORDER_WIDTH = 2;

        /**
         * Current moving tetromino on the screen.
         */
        private MovableTetrisPiece myCurrentPiece;

        /**
         * The list of frozen blocks on each row.
         */
        private List<Block[]> myFrozenBlocks;

        GameContent() {
            super();
            setBackground(Appearance.LIGHT_BACKGROUND);
            setPreferredSize(new Dimension(BOARD.getWidth() * SCALE_FACTOR,
                    BOARD.getHeight() * SCALE_FACTOR));
            BOARD.addPropertyChangeListener(this);
        }

        @Override
        public void propertyChange(final PropertyChangeEvent theEvent) {
            switch (theEvent.getPropertyName()) {
                case PropertyChangeEnabledBoard.NEW_GAME_PROP_CHANGE:
                case PropertyChangeEnabledBoard.CURR_PIECE_UDATE_PROP_CHANGE:
                    updatePiece(theEvent.getNewValue());
                    break;
                case PropertyChangeEnabledBoard.BOARD_UPDATE_PROP_CHANGE:
                    updatePiece(theEvent.getNewValue());
                    updateBoardData(theEvent.getNewValue());
                    break;
                default:
            }
        }

        private void updatePiece(final Object theNewValue) {
            if (theNewValue instanceof MovableTetrisPiece) {
                myCurrentPiece = (MovableTetrisPiece) theNewValue;
                repaint();
            }
        }

        private void updateBoardData(final Object theNewValue) {
            // Create a list to store board data of a specific type
            final List<Block[]> newData = new ArrayList<>();

            // Safely cast function's input
            if (theNewValue instanceof List<?>) {
                for (final Object row : (List<?>) theNewValue) {
                    if (row instanceof Block[]) {
                        newData.add((Block[]) row);
                    }
                }
            }

            // Check if new board data was passed
            if (!newData.isEmpty()) {
                myFrozenBlocks = newData;
                repaint();
            }
        }

        /**
         * Paints graphics inside of the panel.
         */
        @Override
        protected void paintComponent(final Graphics theGraphics) {
            super.paintComponent(theGraphics);
            final Graphics2D graphics = (Graphics2D) theGraphics;

            // Increase graphics display
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            paintCurrentPiece(graphics);
            paintFrozenBlocks(graphics);
        }

        /**
         * Method for painting current piece on the screen.
         *
         * @param theGraphics where the block will be drawn.
         */
        @SuppressWarnings("LawOfDemeter")
        private void paintCurrentPiece(final Graphics2D theGraphics) {
            if (myCurrentPiece != null) {
                final Point position = myCurrentPiece.getPosition();
                final Rotation rotation = myCurrentPiece.getRotation();
                final TetrisPiece tetrisPiece = myCurrentPiece.getTetrisPiece();
                final Block blockType = tetrisPiece.getBlock();

                final int x = position.x();
                final int y = BOARD.getHeight() - position.y() - 1;
                final int[][] points = tetrisPiece.getPointsByRotation(rotation);

                for (final int[] pos : points) {
                    drawBlock(theGraphics, blockType, x + pos[0], y - pos[1]);
                }
            }
        }

        /**
         * Method for painting frozen pieces on the screen.
         *
         * @param theGraphics where the frozen blocks will be drawn.
         */
        private void paintFrozenBlocks(final Graphics2D theGraphics) {
            if (myFrozenBlocks != null) {
                for (int i = myFrozenBlocks.size() - 1; i >= 0; i--) {
                    final Block[] row = myFrozenBlocks.get(i);
                    int columnIndex = 0;
                    for (final Block c : row) {
                        if (c != null) {
                            drawBlock(theGraphics, c, columnIndex, BOARD.getHeight() - i - 1);
                        }
                        columnIndex++;
                    }
                }
            }
        }

        /**
         * Method for drawing tetromino's block.

         * @param theGraphics graphics where the block will be drawn.
         * @param theBlockType type of block that is being drawn.
         * @param theX x-coordinate on the screen where the tetromino will be placed.
         * @param theY y-coordinate on the screen where the tetromino will be placed.
         */
        private void drawBlock(
            final Graphics2D theGraphics,
            final Block theBlockType,
            final double theX,
            final double theY
        ) {
            final double x = theX * SCALE_FACTOR;
            final double y = theY * SCALE_FACTOR;

            final Shape square = new Rectangle2D.Double(x, y, SCALE_FACTOR, SCALE_FACTOR);
            final Color color = getShapeColor(theBlockType);

            theGraphics.setStroke(new BasicStroke(BORDER_WIDTH));
            theGraphics.setPaint(color);
            theGraphics.fill(square);

            Color borderColor = LIGHT_BACKGROUND;
            if (APPEARANCE.getTheme().equals(DARK)) {
                borderColor = DARK_BACKGROUND;
            }
            theGraphics.setPaint(borderColor);
            theGraphics.draw(square);
        }

        /**
         * Method for retrieving the color of the tetromino.
         *
         * @param theBlockType type of the block that is being drawn.
         * @return the color of the block.
         */
        private Color getShapeColor(final Block theBlockType) {
            return APPEARANCE.getBlockColor(theBlockType);
        }
    }
}