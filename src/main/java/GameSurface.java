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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameSurface extends JPanel implements KeyListener, MouseListener, ActionListener {
    private BufferedImage background;
    private List<Rectangle> pipes;
    private Rectangle monkey;
    private BufferedImage monkeySprite;
    private BufferedImage[] monkeyBufferedImages = new BufferedImage[4];
    private int currentMonkey;
    private boolean gameOver, started, bounce;
    private Timer fps;
    private Pipe pipeMaker;
    private int fallspeed, pipeSpeed, ticks, bounceSpeed;
    private int score, highScore;
    private int monkeyHeight, monkeyWidth;
    private int[] pipeSpace = { 150, 200, 250 };
    private static int difficulty = 0;
    private Clip jumpSound, deathSound, scoreSound;
    private Color myGreen;

    public GameSurface() {
        try {
            this.monkeySprite = ImageIO.read(new File("src/main/resources/apan_bak.png"));
            this.background = ImageIO.read(new File("src/main/resources/newBackg.png"));

            jumpSound = AudioSystem.getClip();
            jumpSound.open(AudioSystem.getAudioInputStream(new File(("src/main/resources/jump.wav"))));
            scoreSound = AudioSystem.getClip();
            scoreSound.open(AudioSystem.getAudioInputStream(new File(("src/main/resources/score.wav"))));
            deathSound = AudioSystem.getClip();
            deathSound.open(AudioSystem.getAudioInputStream(new File(("src/main/resources/death.wav"))));

            getNewMonkeyImage();
        } catch (Exception e) {
            System.err.println("Monke problem");
            // TODO: handle exception
        }

        myGreen = new Color(60, 159, 0);

        pipeMaker = new Pipe();
        pipeMaker.setSpace(pipeSpace[difficulty]);

        pipes = new ArrayList<>();
        monkeyWidth = (int) (monkeySprite.getWidth() * 0.5);
        monkeyHeight = (int) (monkeySprite.getHeight() * 0.5);

        newMonkey();

        addKeyListener(this);
        addMouseListener(this);

        fps = new Timer(0, this);
        fps.setRepeats(true);
        // Aprox. 60 FPS
        fps.setDelay(25);
        // fps.start();
    }

    private void jump() {
        pipeSpeed = 4;

        if (gameOver) {
            newMonkey();
            bounceSpeed = 0;
            fallspeed = 0;
            ticks = 0;
            score = 0;
            pipes.clear();
            gameOver = false;
        }

        if (!started) {
            getHighScore();
            started = true;
            fps.start();
        }

        if (!gameOver) {
            if (fallspeed > 0) {
                fallspeed = 0;
            }

            if (fallspeed > -9) {
                fallspeed -= 9;
            }
            playAudio(jumpSound);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ticks++;

        if (started) {
            if (monkey.y + monkey.height >= App.getHEIGHT()) {
                grounded();
            }
            // If you collide with the ceiling.
            if (monkey.y <= 0) {
                hitHead();
            }

            for (int i = 0; i < pipes.size(); i++) {
                Rectangle currentRec = pipes.get(i);

                if (currentRec.intersects(monkey)) {
                    bounce = true;
                    gameOver();
                }
                if (i % 2 == 0 && monkey.x + (monkey.width / 2) > currentRec.x + (currentRec.width / 2) - 2
                        && monkey.x + (monkey.width / 2) < currentRec.x + (currentRec.width / 2) + 2) {
                    // Fråga Hampus varför detta inte funkar
                    // i % 2 == 0 && currentRec.x + (currentRec.width / 2) ==
                    // monkey.x + (monkey.width / 2)
                    score++;
                    playAudio(scoreSound);
                    if (score > highScore) {
                        highScore = score;
                    }
                }
                if (currentRec.x + currentRec.width < 0) {
                    pipes.remove(currentRec);
                }
            }
            // "fallspeed < X" where X = maximum fallspeed.
            if (ticks % 2 == 0 && fallspeed < 10) {
                fallspeed += 2;
                if (bounce) {
                    if (fallspeed < 0) {
                        fallspeed = 0;
                    }
                    bounceSpeed = 9;
                    bounce = false;
                }
            }

            if (bounceSpeed > 0) {
                monkey.x -= bounceSpeed;
                bounceSpeed--;
            }
            monkey.y += fallspeed;

            if (ticks % (int) (App.getWIDTH() / 12) == 0) {
                pipeMaker.addPipe(pipes);
            }

            if (!pipes.isEmpty()) {
                movePipes(pipes);
            }
            repaint();
        }
    }

    private void drawSurface(Graphics2D g) {
        final Dimension d = this.getSize();

        g.drawImage(background, 0, 0, App.getWIDTH(), App.getHEIGHT(), null);

        if (monkeySprite != null) {
            monkeySprite = monkeyBufferedImages[currentMonkey];
            g.drawImage(monkeySprite, (int) monkey.getX(), (int) monkey.getY(), (int) monkey.getWidth(),
                    (int) monkey.getHeight(), null);
    
            currentMonkey = (currentMonkey + 1) % monkeyBufferedImages.length;
            
            } else {
            g.setColor(Color.red);
            g.fillRect(0, 0, d.width, d.height);
        }

        if (!started) {
            g.setFont(new Font("Calibri", 1, 50));
            g.setColor(Color.BLACK);
            g.drawString("Welcome", App.getWIDTH() / 2 - 155, 80);
            g.setColor(Color.WHITE);
            g.drawString("Welcome", App.getWIDTH() / 2 - 160, 75);
        }

        if (started && !gameOver) {
            drawRectangles(g, pipes);

            g.setFont(new Font("Calibri", 1, 25));
            g.setColor(Color.BLACK);
            g.drawString("Score: " + score + " | " + highScore, 52, 52);
            g.setColor(Color.WHITE);
            g.drawString("Score: " + score + " | " + highScore, 50, 50);
        }

        if (gameOver) {
            drawRectangles(g, pipes);

            g.setFont(new Font("Calibri", 1, 50));
            g.setColor(Color.BLACK);
            g.drawString("You died", App.getWIDTH() / 2 - 155, 80);
            g.setColor(Color.WHITE);
            g.drawString("You died", App.getWIDTH() / 2 - 160, 75);
            g.setFont(new Font("Calibri", 1, 25));
            g.setColor(Color.BLACK);
            g.drawString("Score: " + score + " | " + highScore, App.getWIDTH() / 2 - 158, 152);
            g.setColor(Color.WHITE);
            g.drawString("Score: " + score + " | " + highScore, App.getWIDTH() / 2 - 160, 150);
        }
    }

    private void menu() {
        JButton easy = new JButton("Easy");
        easy.setBounds(App.getWIDTH() / 2 - 250, App.getHEIGHT() / 2, 150, 50);
        JButton medium = new JButton("Medium");
        medium.setBounds(App.getWIDTH() / 2 - 75, App.getHEIGHT() / 2, 150, 50);
        JButton hard = new JButton("Hard");
        hard.setBounds(App.getWIDTH() / 2 + 100, App.getHEIGHT() / 2, 150, 50);
        this.add(easy);
        this.add(medium);
        this.add(hard);
    }

    private void playAudio(Clip clip) {
        if (clip.getMicrosecondPosition() == clip.getMicrosecondLength()) {
            clip.setMicrosecondPosition(0);
        }
        
        clip.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g);
        drawSurface(g2d);
    }

    public void movePipes(List<Rectangle> pipes) {
        for (Rectangle rect : pipes) {
            rect.x = rect.x - pipeSpeed;
        }
    }

    private void gameOver() {
        gameOver = true;
        pipeSpeed = 0;
        saveHighScore();
    }

    private void grounded() {
        monkey.y = App.getHEIGHT() - monkeyHeight - 10;
        gameOver();
    }

    private void hitHead() {
        monkey.y = 0;
        fallspeed = 4;
        gameOver();
    }

    public void drawRectangle(Graphics g, Rectangle rect) {
        g.fillRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
    }

    public void drawRectangles(Graphics g, List<Rectangle> pipes) {
        g.setColor(myGreen);
        for (Rectangle rect : pipes) {
            drawRectangle(g, rect);
        }
    }
    // #region keyStuff & G&S's

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            jump();
            playAudio(jumpSound);
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
            playAudio(jumpSound);
        }
    }

    private void getHighScore() {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("src/main/resources/score.log"))) {
            highScore = reader.read();
            if (highScore < 0) {
                highScore = 0;
            }
        } catch (IOException e) {
            // TODO: handle exception
        }
    }

    private void saveHighScore() {
        try (FileWriter writer = new FileWriter("src/main/resources/score.log");
                BufferedWriter bw = new BufferedWriter(writer);) {
            if (gameOver) {
                bw.write(highScore);
            }

        } catch (IOException e) {
            // TODO: handle exception
        }
    }

    private void newMonkey() {
        monkey = new Rectangle((App.getWIDTH() / 2) - (monkeyWidth / 2), (App.getHEIGHT() / 2) - (monkeyHeight / 2),
                monkeyWidth, monkeyHeight);
    }

    public static void setDifficulty(int newDifficulty) {
        difficulty = newDifficulty;
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

    private void getNewMonkeyImage() {
        try {
            monkeyBufferedImages[0] = ImageIO.read(getClass().getResourceAsStream("apan_bak.png"));
            monkeyBufferedImages[1] = ImageIO.read(getClass().getResourceAsStream("apan.png"));
            monkeyBufferedImages[2] = ImageIO.read(getClass().getResourceAsStream("apan_fram.png"));
            monkeyBufferedImages[3] = monkeyBufferedImages[1];
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}