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
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import model.Appearance;
import model.AppearanceClass;
import model.Block;
import model.BoardClass;
import model.PropertyChangeEnabledBoard;
import model.Rotation;
import model.TetrisPiece;

/**
 * Class for creating a next piece panel.
 *
 * @author Dmitry Khotinskiy
 * @author Aly Badr
 * @author Brandon Phan
 * @author Lucas Jeong
 * @version 1.0.0
 */
public final class NextPiecePanel extends JPanel implements PropertyChangeListener {
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
    private static final NextPieceContent NEXT_PIECE_CONTENT_PANEL = new NextPieceContent();

    /**
     * Main constructor of the game panel.
     */
    public NextPiecePanel() {
        super();
        setBackground(LIGHT_BACKGROUND);
        setLayout(new FlowLayout(FlowLayout.CENTER, 2, (int) PADDING));
        setBorder(new EmptyBorder(0, 0, 0, (int) PADDING - 2));
        add(NEXT_PIECE_CONTENT_PANEL);
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

        final Line2D leftLine = new Line2D.Double(0.5, PADDING + 1,
                0.5, 3 * SCALE_FACTOR + PADDING - 1);
        graphics.draw(leftLine);

        final Line2D rightLine = new Line2D.Double(5 * SCALE_FACTOR + 2.5, PADDING + 1,
                5 * SCALE_FACTOR + 2.5, 3 * SCALE_FACTOR + PADDING - 1);
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
            NEXT_PIECE_CONTENT_PANEL.setBackground(Appearance.DARK_BACKGROUND);
            NEXT_PIECE_CONTENT_PANEL.repaint();
            setBackground(DARK_BACKGROUND);
            repaint();
        } else {
            NEXT_PIECE_CONTENT_PANEL.setBackground(Appearance.LIGHT_BACKGROUND);
            NEXT_PIECE_CONTENT_PANEL.repaint();
            setBackground(LIGHT_BACKGROUND);
            repaint();
        }
    }

    private static final class NextPieceContent extends JPanel
            implements PropertyChangeListener {
        /**
         * Border width of the block.
         */
        static final int BORDER_WIDTH = 2;

        /**
         * Height of the next piece panel.
         */
        static final int HEIGHT = 3 * SCALE_FACTOR;

        /**
         * Width of the next piece panel.
         */
        static final int WIDTH = 5 * SCALE_FACTOR;

        /**
         * Variable for the next piece.
         */
        private TetrisPiece myNextPiece;

        /**
         * Main constructor of the details panel.
         */
        NextPieceContent() {
            super();
            setBackground(Appearance.LIGHT_BACKGROUND);
            setPreferredSize(new Dimension(WIDTH, HEIGHT));
            BOARD.addPropertyChangeListener(this);
        }

        @Override
        public void propertyChange(final PropertyChangeEvent theEvent) {
            if (PropertyChangeEnabledBoard.NEXT_PIECE_UPDATE_PROP_CHANGE.
                    equals(theEvent.getPropertyName())) {
                updatePiece(theEvent.getNewValue());
            }
        }

        /**
         * Update with the new piece.
         *
         * @param theNewPiece the next piece.
         */
        private void updatePiece(final Object theNewPiece) {
            if (theNewPiece instanceof TetrisPiece) {
                myNextPiece = (TetrisPiece) theNewPiece;
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

            final Graphics2D g2d = (Graphics2D) theGraphics;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            double offsetX = 0;
            double offsetY = (double) 1 / 2;
            final int width = 3;

            if (myNextPiece != null) {
                final Block blockType = myNextPiece.getBlock();
                if (blockType.equals(Block.I)) {
                    offsetX = (double) 1 / 2;
                    offsetY = 0;
                }
                if (blockType.equals(Block.O)) {
                    offsetX = (double) 1 / 2;
                    offsetY = (double) -1 / 2;
                }

                final int[][] points = myNextPiece.getPointsByRotation(Rotation.HALF);
                for (final int[] pos : points) {
                    drawBlock(graphics, blockType, width - pos[0] + offsetX, pos[1] + offsetY);
                }
            }
        }

        /**
         * Method for drawing tetromino's block.
         *
         * @param theBlockType type of block that is being drawn.
         * @param theX         x-coordinate on the screen where the tetromino will be placed.
         * @param theY         y-coordinate on the screen where the tetromino will be placed.
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