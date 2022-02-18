import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameSurface extends JPanel implements KeyListener {
    private static final double PIPE_PIXELS_PER_MS = 0.12;
    private BufferedImage background;
    private List<Pipe> pipes;
    // private transient FrameUpdater updater;
    // private Rectangle monkey;
    private transient BufferedImage monkeySprite;
    private boolean gameOver;
    Timer timer;

    public GameSurface(final int width, final int height) {

        try {
            this.monkeySprite = ImageIO.read(new File("resources/Apan200x200.png"));
            this.background = ImageIO.read(new File("resources/background.png"));
        } catch (Exception e) {
            System.err.println("Monke problem");
            // TODO: handle exception
        }

        this.gameOver = false;
        /*
         * Comments/unused code
         * this.pipes = new ArrayList<>();
         * this.monkey = new Rectangle(20, width / 2 - 15, 50, 50); // (xpos, ypos,
         * width, height) ??????
         *
         * this.updater = new FrameUpdater(this, 60);
         * this.updater.setDaemon(true);
         * this.updater.start();
         */
        timer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        timer.setRepeats(true);
        // Aprox. 60 FPS
        timer.setDelay(17);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(background, 0, 0, this);
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // TODO
        drawSurface(g2d);
    }

    private void drawSurface(Graphics2D g) {
        int offset = 46;
        final Dimension d = this.getSize();
        if (gameOver) { // gameover do this
            // TODO
        }

        g.drawImage(background, 0, 0, null);
        /*
         * This is not used but could be used for a simpler background thats not using
         * an image file
         * g.setColor(Color.CYAN);
         * g.fillRect(0, 0, d.width, d.height);
         *
         * color during gameover?
         *
         * TODO
         * for (Pipe pipe : pipes) { // color and pos?
         * }
         */
        if (monkeySprite != null) {
            g.drawImage(monkeySprite, 200 + offset,
                    100 + offset - 15, 100, 100, null); // Anton ska hitta algoritm för position, (utan offset)
        } else {
            // TODO
            g.setColor(Color.red);
            g.fillRect(0, 0, d.width, d.height);
        }

    }
    /* Unused method
     * public void update(int time) {
     * if (gameOver) {
     * updater.interrupt();
     * return;
     * }
     *
     * final Dimension d = getSize();
     * if (d.height <= 0 || d.width <= 0) {
     * // if the panel has not been placed properly in the frame yet
     * // just return without updating any state
     * return;
     * }
     * }
     */

    // #region keySTuff
    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Do nothing.

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Do nothing.

    }
    // #endregion
}
