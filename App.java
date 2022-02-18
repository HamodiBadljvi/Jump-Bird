import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.*;


import java.awt.Color;

public class App<Graphics2D> extends JFrame {
    final static int x = 400;
    final static int y = 400;

    public static void main(String[] args) {
        // Graphics2D g2g = new (Graphics2D) g;
        PlayableCharacter monkey = new PlayableCharacter(50, 50, 0, 0, null);

        JFrame frame = new JFrame("Jumpy-Bird");

        frame.setSize(x, y);
        frame.add(new JLabel(new ImageIcon("background.png")));
        // frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        // frame.getContentPane().setBackground(Color.red);
    }

}
