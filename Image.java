import javax.swing.ImageIcon;

public class Image {

    ImageIcon background;

    public void background(){
    try{
        background = new ImageIcon(getClass().getResource("background"));
        //display image on screen
        JLabel = new JLabel(background);
        frame.add(JLabel);
    } catch(Exception e){
        System.out.println("Image not found!");
    }
    //set size of background image(change x)
    //frame.pack(); better?
    frame.setSize(x, x);
    frame.setVisable(true);
}
}
