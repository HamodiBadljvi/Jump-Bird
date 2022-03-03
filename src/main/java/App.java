import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;

public class App extends JFrame {
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static int WIDTH = 600;
    static int HEIGHT = 400;

    public static void main(String[] args) {

        JFrame frame = new JFrame("Jumpy-Bird");

        boolean fullscreen = false;

        if (fullscreen) {
            WIDTH = (int) screenSize.getWidth();
            HEIGHT = (int) screenSize.getHeight();
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setUndecorated(true);
        }

        GameSurface gs = new GameSurface(WIDTH, HEIGHT); // This is a Jlabel

        frame.setUndecorated(true);
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        frame.add(gs);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        gs.requestFocus();
        // frame.pack();
    }

}
