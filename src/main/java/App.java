import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;

public class App extends JFrame {
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static int WIDTH = 600;
    static int HEIGHT = 400;
    private static String arg1;
    private static boolean fullscreen = false;

    public static void main(String[] args) {
        if (args.length >= 1) {
            arg1 = args[0];
            if (arg1.equals("fs")) {
                fullscreen = true;
            }
        } else {
            arg1 = "";
        }

        JFrame frame = new JFrame("Jumpy-Bird");

        if (fullscreen || arg1.equals("fs")) {
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
    }
}
