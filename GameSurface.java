import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Timer;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameSurface extends JPanel implements KeyListener, MouseListener {
    private int ticks, yMotion, score, highscore;
    private static final double PIPE_PIXELS_PER_MS = 0.12;
    private BufferedImage background;
    private List<Pipe> pipes;
    private int monkeySize = 75;
    // private transient FrameUpdater updater;
    private Rectangle monkey = new Rectangle((App.WIDTH / 2) - (monkeySize / 2), (App.HEIGHT / 2) - (monkeySize / 2),
            monkeySize, monkeySize);
    private transient BufferedImage monkeySprite;
    private boolean gameOver;
    Timer timer;

    public GameSurface(final int width, final int height) {
        addKeyListener(this);
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
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", 1, 75));
        g.drawString("Welcome", App.WIDTH / 2 - 160, 75);
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
            // g.drawImage(monkeySprite, (d.width / 2 - 100) + offset,
            // (d.height / 2 - 100) + offset - 15, 100, 100, null);
            g.drawImage(monkeySprite, (int) monkey.getX(), (int) monkey.getY(), (int) monkey.getWidth(),
                    (int) monkey.getHeight(), null);
        } else {
            // TODO
            g.setColor(Color.red);
            g.fillRect(0, 0, d.width, d.height);
        }
        fall(monkey);
        // g.setColor(Color.black);
        // g.fillRect((int) monkey.getX(), (int) monkey.getY(), (int) monkey.getWidth(),
        // (int) monkey.getHeight());

    }
    /*
     * Unused method
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

    private void fall(Rectangle r) {
        int fallspeed = 5;
        if (gameOver) {
            return;
        } else {
            r.y = (int) (r.getY() + fallspeed);
        }

    }

public void drawRectangle (Graphics g, Rectangle rect){
    g.fillRect((int)rect.getX(), (int)rect.getY(), (int)rect.getWidth(),(int)rect.getHeight());
}
    // #region keySTuff

    @Override
    public void keyReleased(KeyEvent e) {
        int y = getY();

        if (gameOver) {
            return;
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            monkey.translate(0, -100);
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
        /*
        try{
            File wavFile = new File(sound.wav);
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStrean(wavFile));
            clip.start();
        } catch (Exception e) {
            System.out.println(e);
        }
        */
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }
    
    //#region Unused

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
    }
    

    @Override
    public void keyTyped(KeyEvent e) {
        // Do nothing.
    }
    

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    //#endregion
    // #endregion

}
