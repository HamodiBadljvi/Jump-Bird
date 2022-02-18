import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;

public class GameSurface extends JPanel implements KeyListener {
    private List<Pipe> pipes;
    private transient FrameUpdater updater;
    private Rectangle monkey;
    private transient BufferedImage monkeySprite;
    private boolean gameOver;

    public GameSurface(final int width) {
        try {
            this.monkeySprite = ImageIO.read(new File("Apan200x200.png"));
        } catch (Exception e) {
            // TODO: handle exception
        }

        this.gameOver = false;
        // this.pipes = new ArrayList<>();
        this.monkey = new Rectangle(20, width / 2 - 15, 46, 20); // (xpos, ypos, width, height)

        // this.updater = new FrameUpdater(this, 60);
        // this.updater.setDaemon(true);
        // this.updater.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // TODO
        drawSurface(g2d);
    }

    private void drawSurface(Graphics2D g) {
        int offset = 100;
        if (gameOver) {  //gameover do this
        // TODO
    }
    //color during gameover?

    for (Pipe pipe : pipes){ //color and pos?
    }

    if (monkeySprite != null){
            g.drawImage(monkeySprite, monkey.x, monkey.y, monkey.x + monkey.width,
                    monkey.y + monkey.height, offset, 0, offset + 46, 20, null);
    } else{
        g.setColor(Color.red);
    }

    
    }

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
