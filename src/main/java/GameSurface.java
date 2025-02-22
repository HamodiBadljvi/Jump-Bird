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
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings({ "java:S106", "java:S1948", "java:S1659" })
public class GameSurface extends JPanel implements KeyListener, MouseListener, ActionListener {
    private static String SCORE_LOG;

    private static final String SCORE = "Score: ", CALIBRI = "Calibri";

    private final String[] scoreLogs = {"easyScore.log", "mediumScore.log", "hardScore.log"};
    private BufferedImage backImg;
    private List<Rectangle> pipes;
    private Rectangle monkey;
    private BufferedImage monkeySprite;
    private BufferedImage[] monkeyBufferedImages = new BufferedImage[4];
    private int currentMonkey;
    private int monkeyMovementTime;
    private boolean gameOver = false, started = false, grounded = false;
    private Timer fps;
    private Pipe pipeMaker;
    private int fallspeed, pipeSpeed, ticks, bounceSpeed;
    private int currentScore, highScore;
    private int monkeyHeight, monkeyWidth;
    private long timeOfDeath;
    private int deathDelay = 500;
    private int[] pipeSpace = { 200, 175, 150 };
    private Clip jumpSound, deathSound, scoreSound;
    private Color pipeColor;

    public GameSurface(int difficulty) {
        try {
            this.backImg = ImageIO.read(getClass().getResourceAsStream("jungle.png"));

            jumpSound = loadSound("jump.wav");
            scoreSound = loadSound("score.wav");
            deathSound = loadSound("death.wav");

            getNewMonkeyImage();
        } catch (Exception e) {
            System.err.println("Monkey problem");
            e.printStackTrace();
            // TODO: handle exception
        }
        SCORE_LOG = scoreLogs[difficulty];

        pipeColor = new Color(139,69,19);

        pipes = new ArrayList<>();
        pipeMaker = new Pipe();
        pipeMaker.setSpace(pipeSpace[difficulty]);

        monkeyWidth = (int) (monkeySprite.getWidth() * 0.5);
        monkeyHeight = (int) (monkeySprite.getHeight() * 0.5);

        newMonkey();

        addKeyListener(this);
        addMouseListener(this);

        // set fps/speed of the game.
        fps = new Timer(0, this);
        fps.setRepeats(true);
        fps.setDelay(26);
    }

    private void jump() {
        // if not gameover monkey can jump.
        if (gameOver) {
            resetLevel();
        }

        if (!started) {
            getHighScore();
            started = true;
            fps.start();
        }

        if (!gameOver) {
            pipeSpeed = 4;
            if (fallspeed > 0) {
                fallspeed = 0;
            }

            if (fallspeed > -9) {
                fallspeed -= 9;
            }
            playAudio(jumpSound);
        }
    }

    @SuppressWarnings("java:S3776")
    @Override
    public void actionPerformed(ActionEvent e) {
        ticks++;

        if (started) {
            if (monkey.y + monkey.height >= App.getHEIGHT()) {
                groundedMethod();
            }

            if (monkey.y <= 0) {
                hitHead();
            }

            for (int i = 0; i < pipes.size(); i++) {
                pipeIntersectAndScoreCheck(i);
            }
            // "fallspeed < X" where X = maximum fallspeed.
            if (ticks % 2 == 0 && fallspeed < 12) {
                fallspeed += 2;
            }

            if (bounceSpeed > 0) {
                monkey.x -= bounceSpeed;
                bounceSpeed--;
            }

            if (!grounded) {
                monkey.y += fallspeed;
            }

            if (ticks % (App.getWIDTH() / 12) == 0) {
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

        g.drawImage(backImg, 0, 0, App.getWIDTH(), App.getHEIGHT(), null);

        if (monkeySprite != null) {
            drawMonkey(g);
        } else {
            g.setColor(Color.red);
            g.fillRect(0, 0, d.width, d.height);
        }

        if (!started) {
            drawWelcome(g);
        }

        if (started && !gameOver) {
            drawScore(g);
        }

        if (gameOver) {
            drawGameOver(g);
        }
    }

    private Clip loadSound(String soundFile)
            throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        Clip sound = AudioSystem.getClip();
        InputStream resourceAsStream = getClass().getResourceAsStream(soundFile);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(resourceAsStream);
        sound.open(AudioSystem.getAudioInputStream(bufferedInputStream));
        return sound;
    }

    private void getNewMonkeyImage() {
        try {
            monkeyBufferedImages[0] = ImageIO.read(getClass().getResourceAsStream("apanbak.png"));
            monkeyBufferedImages[1] = ImageIO.read(getClass().getResourceAsStream("apanrak.png"));
            monkeyBufferedImages[2] = ImageIO.read(getClass().getResourceAsStream("apanfram.png"));
            monkeyBufferedImages[3] = monkeyBufferedImages[1];
            monkeySprite = monkeyBufferedImages[0];
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pipeIntersectAndScoreCheck(int i) {
        Rectangle currentRec = pipes.get(i);

        if (currentRec.intersects(monkey)) {
            bounceMethod();
        }

        if (i % 2 == 0 && monkey.x + (monkey.width / 2) > currentRec.x + (currentRec.width / 2) - 2
                && monkey.x + (monkey.width / 2) < currentRec.x + (currentRec.width / 2) + 2) {
            gainScore();
        }

        if (currentRec.x + currentRec.width < 0) {
            pipes.remove(currentRec);
        }
    }

    private void resetLevel() {
        if (System.currentTimeMillis() - timeOfDeath < deathDelay) {
            return;
        }
        timeOfDeath = 0;
        /*
         * när vi dör ska vi spara in timeofdeath
         * if (tiden nu - timeOfDeath < xxxx)
         * return
         */
        newMonkey();
        pipes.clear();
        pipeMaker.addPipe(pipes);
        ticks = 0;
        currentScore = 0;
        gameOver = false;
        grounded = false;
        bounceSpeed = 0;
        fallspeed = 0;
    }

    private void gainScore() {
        currentScore++;
        playAudio(scoreSound);
        if (currentScore > highScore) {
            highScore = currentScore;
        }
    }

    private void drawMonkey(Graphics2D g) {
        monkeySprite = monkeyBufferedImages[currentMonkey];
        g.drawImage(monkeySprite, (int) monkey.getX(), (int) monkey.getY(), (int) monkey.getWidth(),
                (int) monkey.getHeight(), null);
        monkeyMovementTime = (monkeyMovementTime + 1) % 10; // 10*26ms=260
        if (monkeyMovementTime == 0) {
            currentMonkey = (currentMonkey + 1) % monkeyBufferedImages.length;
        }
    }

    private void drawWelcome(Graphics2D g) {
        g.setFont(new Font(CALIBRI, 1, 50));
        g.setColor(Color.BLACK);
        g.drawString("Welcome", App.getWIDTH() / 2 - 155, 80);
        g.setColor(Color.WHITE);
        g.drawString("Welcome", App.getWIDTH() / 2 - 160, 75);
    }

    private void drawScore(Graphics2D g) {
        drawRectangles(g, pipes);

        g.setFont(new Font(CALIBRI, 1, 25));
        g.setColor(Color.BLACK);
        g.drawString(SCORE + currentScore + " | " + highScore, 52, 52);
        g.setColor(Color.WHITE);
        g.drawString(SCORE + currentScore + " | " + highScore, 50, 50);
    }

    private void drawGameOver(Graphics2D g) {
        drawRectangles(g, pipes);

        g.setFont(new Font(CALIBRI, 1, 50));
        g.setColor(Color.BLACK);
        g.drawString("You died", App.getWIDTH() / 2 - 155, 80);
        g.setColor(Color.WHITE);
        g.drawString("You died", App.getWIDTH() / 2 - 160, 75);
        g.setFont(new Font(CALIBRI, 1, 25));
        g.setColor(Color.BLACK);
        g.drawString(SCORE + currentScore + " | " + highScore, App.getWIDTH() / 2 - 158, 152);
        g.setColor(Color.WHITE);
        g.drawString(SCORE + currentScore + " | " + highScore, App.getWIDTH() / 2 - 160, 150);
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

    private void gameOverMethod() {
        timeOfDeath = System.currentTimeMillis();
        playAudio(deathSound);
        gameOver = true;
        pipeSpeed = 0;
        saveHighScore();
    }

    private void bounceMethod() {
        if (fallspeed < 0) {
            fallspeed = 0;
        }
        bounceSpeed = 9;
        gameOverMethod();
    }

    private void groundedMethod() {
        grounded = true;
        fallspeed = 0;
        monkey.y = App.getHEIGHT() - monkeyHeight;
        if (!gameOver) {
            gameOverMethod();
        }
    }

    private void hitHead() {
        monkey.y = 0;
        fallspeed = 4;
        gameOverMethod();
    }

    public void drawRectangle(Graphics g, Rectangle rect) {
        g.fillRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
    }

    public void drawRectangles(Graphics g, List<Rectangle> pipes) {
        g.setColor(pipeColor);
        for (Rectangle rect : pipes) {
            drawRectangle(g, rect);
        }
    }

    // #region keyStuff & G&S's
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            jump();
        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            App.startMenu();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            jump();
        }
    }

    private void getHighScore() {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(SCORE_LOG))) {
            highScore = reader.read();
            if (highScore < 0) {
                highScore = 0;
            }
        } catch (IOException e) {
            // TODO: handle exception
        }
    }

    private void saveHighScore() {
        try (FileWriter writer = new FileWriter(SCORE_LOG);
                BufferedWriter bw = new BufferedWriter(writer);) {
            if (gameOver) {
                bw.write(highScore);
            }

        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    private void newMonkey() {
        monkey = new Rectangle((App.getWIDTH() / 2) - (monkeyWidth / 2), (App.getHEIGHT() / 2) - (monkeyHeight / 2),
                monkeyWidth, monkeyHeight);
    }

    // #region Unused

    @Override
    public void keyPressed(KeyEvent e) {
        // Unused
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Unused
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Unused
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Unused
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Unused
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Unused
    }
    // #endregion
    // #endregion
}