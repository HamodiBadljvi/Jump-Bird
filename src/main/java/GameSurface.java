package src.main.java;

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
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameSurface extends JPanel implements KeyListener, MouseListener, ActionListener {
    private BufferedImage background;
    private List<Rectangle> pipes;
    // private List<List<Rectangle>> pipes;

    // private transient FrameUpdater updater;
    private Rectangle monkey;
    private transient BufferedImage monkeySprite;
    private boolean gameOver, started, grounded;
    private Timer fps;
    private Pipe pipeMaker;
    private int fallspeed, pipeSpeed, ticks;
    private int score, lastScore, highScore;
    private String message;
    private int monkeyHeight, monkeyWidth;

    public GameSurface(final int width, final int height) {
        try {
            this.monkeySprite = ImageIO.read(new File("src/main/resources/Apan200x200.png"));
            this.background = ImageIO.read(new File("src/main/resources/background.png"));
        } catch (Exception e) {
            System.err.println("Monke problem");
            // TODO: handle exception
        }

        pipeMaker = new Pipe();
        pipes = new ArrayList<>();
        message = "Welcome";
        score = 0;
        monkeyWidth = (int) (monkeySprite.getWidth() * 0.4);
        monkeyHeight = (int) (monkeySprite.getHeight() * 0.4);
        ticks = 0;

        monkey = new Rectangle((App.WIDTH / 2) - (monkeyWidth / 2), (App.HEIGHT / 2) - (monkeyHeight / 2),
                monkeyWidth, monkeyHeight);

        addKeyListener(this);
        addMouseListener(this);

        fps = new Timer(0, this);
        fps.setRepeats(true);
        // Aprox. 60 FPS
        fps.setDelay(17);
        fps.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ticks++;

        if (started) {
            message = Integer.toString(score);
            // If you collide with the ground
            if (monkey.y + monkey.height >= App.HEIGHT) {
                fallspeed = 0;
                gameOver = true;
                grounded = true;
            }
            // If you collide with the ceiling.
            if (monkey.y <= 0) {
                gameOver = true;
            }
            if (!grounded) {
                for (int i = 0; i < pipes.size(); i++) {
                    Rectangle currentRec = pipes.get(i);

                    if (currentRec.intersects(monkey)) {
                        gameOver = true;
                        pipeSpeed = 0;
                        if (score > highScore) {
                            highScore = score;
                        }
                    }
                    if (i % 2 == 0 && monkey.x + (monkey.width / 2) > currentRec.x + (currentRec.width / 2) - 2
                            && monkey.x + (monkey.width / 2) < currentRec.x + (currentRec.width / 2) + 2) {
                        // Fråga Hampus varför detta inte funkar
                        // i % 2 == 0 && currentRec.x + (currentRec.width / 2) ==
                        // monkey.x + (monkey.width / 2)
                        score++;
                    }
                    if (currentRec.x + currentRec.width < 0) {
                        pipes.remove(currentRec);
                    }
                }
                // "fallspeed < X" where X = maximum fallspeed.
                if (ticks % 2 == 0 && fallspeed < 10) {
                    fallspeed += 2;
                }
                monkey.y += fallspeed;

                if (ticks % (int) (App.WIDTH / 12) == 0) {
                    pipeMaker.addPipe(pipes);
                }
                if (!pipes.isEmpty()) {
                    movePipes(pipes);
                }
            }
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(background, 0, 0, this);
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g);
        // TODO
        drawSurface(g2d);
    }

    private void drawSurface(Graphics2D g) {
        final Dimension d = this.getSize();

        g.drawImage(background, 0, 0, null);

        if (monkeySprite != null) {
            g.drawImage(monkeySprite, (int) monkey.getX(), (int) monkey.getY(), (int) monkey.getWidth(),
                    (int) monkey.getHeight(), null);
        } else {
            g.setColor(Color.red);
            g.fillRect(0, 0, d.width, d.height);
        }

        if (started) {
            drawRectangles(g, pipes);
        }

        if (gameOver) {
            message = "You died ";
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", 1, 25));
            g.drawString("Score: " + score, App.WIDTH / 2 - 160, 150);
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", 1, 50));
        g.drawString(message, App.WIDTH / 2 - 160, 75);
    }

    public void movePipes(List<Rectangle> pipes) {
        for (Rectangle rect : pipes) {
            rect.x = rect.x - pipeSpeed;
        }
    }

    private void jump() {
        pipeSpeed = 4;
        if (gameOver) {
            grounded = false;
            score = 0;
            monkey = new Rectangle((App.WIDTH / 2) - (monkeyWidth / 2), (App.HEIGHT / 2) - (monkeyHeight / 2),
                    monkeyWidth, monkeyHeight);
            fallspeed = 0;
            gameOver = false;
            pipes.clear();
        }
        if (!started) {
            started = true;

        }
        if (!gameOver) {
            if (fallspeed > 0) {
                fallspeed = 0;
            }
            if (fallspeed > -9) {
                fallspeed -= 9;
            }
        }
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

    public void drawRectangles(Graphics g, List<Rectangle> pipes) {
        g.setColor(Color.GREEN);
        for (Rectangle rect : pipes) {
            drawRectangle(g, rect);
        }

    }

    // #region keyStuff

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            jump();
            if (!fps.isRunning()) {
                fps.start();
            }
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
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    // #endregion
    // #endregion

}
