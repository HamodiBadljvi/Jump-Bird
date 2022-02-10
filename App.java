import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class App<Graphics2D> {
    private transient static BufferedImage apan;
    private int shipImageSpriteCount;
    public static void main(String[] args) {
        
        JFrame frame = new JFrame("test");
        //GameSurface surface = new GameSurFace(400);
        

        frame.setSize(400, 400);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        drawSurface(g2d);
    }
    
    g.setColor(Color.DARK_GRAY);
    g.fillRect(0, 0, d.width, d.height);

        
    }
}
