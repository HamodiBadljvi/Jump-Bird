import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameSurface extends JPanel implements KeyListener {
    private transient BufferedImage monkeySprite;

    public GameSurface(final int width) {
        try {
            this.monkeySprite = ImageIO.read(new File("Apan200x200.png"));
        } catch (Exception e) {
            //TODO: handle exception
        }
    }

    //#region keySTuff
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }
    //#endregion
}
