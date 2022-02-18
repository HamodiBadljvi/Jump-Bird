import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class App extends JFrame {
    final static int x = 600;
    final static int y = 400;

    public static void main(String[] args) {

        JFrame frame = new JFrame("Jumpy-Bird");

        GameSurface gs = new GameSurface(400); // This is a Jlabel

        frame.setSize(x, y);
        frame.setResizable(false);
        frame.add(gs);
        // frame.add(new JLabel(new ImageIcon("background.png")));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // frame.pack();
    }

}
