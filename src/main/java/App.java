import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;

public class App extends JFrame {
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static int screenWidth = 600;
    private static int screenHeight = 400;
    private static int difficulty;
    private static boolean fullscreen = false;
    private static JFrame frame;
    private static GameMenu menu;
    private static GameSurface gameSurface;

    public static void main(String[] args) {
        frame = new JFrame("Jumpy-Bird");
        if (fullscreen) {
            screenWidth = (int) screenSize.getWidth();
            screenHeight = (int) screenSize.getHeight();
            frame.setExtendedState(MAXIMIZED_BOTH);
            frame.setUndecorated(true);
        }

        menu = new GameMenu();

        frame.setUndecorated(true);
        frame.setSize(screenWidth, screenHeight);
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
        return screenWidth;
    }

    public static int getHEIGHT() {
        return screenHeight;
    }

    public static void setDifficulty(int newDifficulty) {
        difficulty = newDifficulty;
    }
    // #endregion
}
