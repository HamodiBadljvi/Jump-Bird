import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameSurface extends JPanel implements KeyListener, MouseListener, ActionListener {
    private int monkeySize, fallspeed, pipeSpeed, ticks;
    private BufferedImage background;
    private List<Rectangle> pipe1, pipe2, pipe3, pipe4;
    // private transient FrameUpdater updater;
    private Rectangle monkey = new Rectangle((App.WIDTH / 2) - (monkeySize / 2), (App.HEIGHT / 2) - (monkeySize / 2),
            monkeySize, monkeySize);
    private transient BufferedImage monkeySprite;
    private boolean gameOver, started;
    private Timer fps;

    public GameSurface(final int width, final int height) {
        monkeySize = 75;
        fallspeed = 4;
        pipeSpeed = 4;
        ticks = 0;

        addKeyListener(this);
        addMouseListener(this);

        try {
            this.monkeySprite = ImageIO.read(new File("resources/Apan200x200.png"));
            this.background = ImageIO.read(new File("resources/background.png"));
        } catch (Exception e) {
            System.err.println("Monke problem");
            // TODO: handle exception
        }
        Pipe p = new Pipe();
        pipe1 = p.addPipe(true);

        this.gameOver = false;

        fps = new Timer(0, this);
        fps.setRepeats(true);
        // Aprox. 60 FPS
        fps.setDelay(17);
        fps.start();

        // pipeRate = new Timer();

        // pipeRate.setRepeats(true);
        // pipeRate.setDelay(100);
        // pipeRate.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ticks++;
        movePipes(pipe1);
        repaint();
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
            g.drawImage(monkeySprite, (int) monkey.getX(), (int) monkey.getY(), (int) monkey.getWidth(),
                    (int) monkey.getHeight(), null);
        } else {
            g.setColor(Color.red);
            g.fillRect(0, 0, d.width, d.height);
        }
        drawRectangles(g, pipe1);
        fall(monkey);
        // g.setColor(Color.black);
        // g.fillRect((int) monkey.getX(), (int) monkey.getY(), (int) monkey.getWidth(),
        // (int) monkey.getHeight());

    }

    public void movePipes(List<Rectangle> pipes) {
        for (Rectangle rect : pipes) {
            rect.x = rect.x - pipeSpeed;
        }
    }

    private void fall(Rectangle r) {
        if (gameOver) {
            return;
        } else {
            r.y = (int) (r.getY() + fallspeed);
        }

    }

    private void jump() {
        monkey.translate(0, -75);
    }

    private void playAudio() {
        // try{
        // File wavFile = new File(sound.wav);
        // Clip clip = AudioSystem.getClip();
        // clip.open(AudioSystem.getAudioInputStrean(wavFile));
        // clip.start();
        // } catch (Exception e) {
        // System.out.println(e);
        // }
    }

    public void drawRectangle(Graphics g, Rectangle rect) {
        g.fillRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
    }

    public void drawRectangles(Graphics g, List<Rectangle> rectangles) {
        for (Rectangle rect : rectangles) {
            drawRectangle(g, rect);
        }
    }
    // #region keyStuff

    @Override
    public void keyReleased(KeyEvent e) {
        if (gameOver) {
            return;
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            jump();
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            jump();
        }
    }

    // #region Unused

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
    // #endregion
    // #endregion

}
