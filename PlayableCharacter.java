import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class PlayableCharacter {

    private transient BufferedImage img;
    Rectangle r;

    //Creates character with rectangle that has position (0,0) and size (0,0)
    public PlayableCharacter(){
        this.r = new Rectangle();
    }


    //New constructor, that adds image to rectangle when created
    public PlayableCharacter(int width, int height, int xPos, int yPos, Graphics2D g){
        try {
            this.img = ImageIO.read(new File("Apan200x200.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.r = new Rectangle(xPos, yPos, width, height);
        int offset = 46; //Ta reda på vad detta är
        g.drawImage(img, r.x, r.y, r.x + r.width, r.y + r.height, offset, 0, offset + 46, 20, null);

    }



}

