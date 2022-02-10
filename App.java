import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;

public class App<Graphics2D> {
    private transient static BufferedImage apan;
    private int shipImageSpriteCount;
    final static int x = 400;
    final static int y = 400;

    public static void main(String[] args) {

        JFrame frame = new JFrame("test");
        // GameSurface surface = new GameSurFace(400);


        frame.setSize(x, y);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // @param g the graphics to paint on

    private void drawSurface(Graphics2D g) {
        final Dimension d = new Dimension(x, y);

                // fill the background
                ((Graphics) g).setColor(Color.RED);
        ((Graphics) g).fillRect(0, 0, d.width, d.height);

    }
}
