import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;

public class App extends JFrame {
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static int WIDTH = 600;
    private static int HEIGHT = 400;
    private static int difficulty;
    private static boolean fullscreen = false;
    private static JFrame frame;
    private static GameMenu menu;
    private static GameSurface gameSurface;

    public static void main(String[] args) {
        frame = new JFrame("Jumpy-Bird");
        if (fullscreen) {
            WIDTH = (int) screenSize.getWidth();
            HEIGHT = (int) screenSize.getHeight();
            frame.setExtendedState(MAXIMIZED_BOTH);
            frame.setUndecorated(true);
        }

        menu = new GameMenu();

        frame.setUndecorated(true);
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(menu);
        frame.setVisible(true);
        menu.requestFocus();

    }

    public static void startGame() {
        gameSurface = new GameSurface(difficulty);
        frame.remove(menu);
        frame.add(gameSurface);
        frame.repaint();
        frame.setVisible(true);
        gameSurface.requestFocus();
    }

    public static void startMenu() {
        frame.remove(gameSurface);
        frame.add(menu);
        frame.repaint();
        frame.setVisible(true);
        menu.requestFocus();
    }

    // #region G&S
    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static void setDifficulty(int newDifficulty) {
        difficulty = newDifficulty;
    }
    // #endregion
}
