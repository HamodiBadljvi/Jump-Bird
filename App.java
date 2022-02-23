import javax.swing.JFrame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class App extends JFrame {
    final static int WIDTH = 600;
    final static int HEIGHT = 400;

    public static void main(String[] args) {

        JFrame frame = new JFrame("Jumpy-Bird");

        GameSurface gs = new GameSurface(WIDTH, HEIGHT); // This is a Jlabel

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
