import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class BackGround {
    static JFrame f;
    static JLabel l;

    
    ImageIcon background;

    public BackGround(){
    try{
        background = new ImageIcon(getClass().getResource("background"));
        //display image on screen
        l = new JLabel(background);
        f = new JFrame();
        f.add(l);
    } catch(Exception e){
        System.out.println("Image not found!");
    }
    
    
    

    }
}
