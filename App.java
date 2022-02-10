import javax.swing.JFrame;

public class App {
    public static void main(String[] args) {
        
        JFrame frame = new JFrame("test");
        //GameSurface surface = new GameSurFace(400);

        frame.setSize(400, 400);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
