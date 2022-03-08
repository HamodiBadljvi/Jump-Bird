import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;

public class App extends JFrame {
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static int WIDTH = 600;
    static int HEIGHT = 400;
    private static boolean fullscreen = false;

    public static void main(String[] args) {
        JFrame gameFrame = new JFrame("Jumpy-Bird");

        if (fullscreen) {
            WIDTH = (int) screenSize.getWidth();
            HEIGHT = (int) screenSize.getHeight();
            gameFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            gameFrame.setUndecorated(true);
        }

        GameSurface gs = new GameSurface(); // This is a Jlabel

        gameFrame.setUndecorated(true);
        gameFrame.setSize(WIDTH, HEIGHT);
        gameFrame.setResizable(false);
        gameFrame.add(gs);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
        gs.requestFocus();
    }
}
