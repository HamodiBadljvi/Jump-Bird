import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class App extends JFrame {
    final static int x = 400;
    final static int y = 400;

    public static void main(String[] args) {

        JFrame frame = new JFrame("Jumpy-Bird");

        GameSurface gs = new GameSurface(400);

        frame.setSize(x, y);
        frame.setResizable(false);
        frame.add(new JLabel(new ImageIcon("background.png")));
        frame.add(gs);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        // frame.pack();
    }

}
