import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.FlowLayout;

public class App<Graphics2D> extends JFrame {
    final static int x = 400;
    final static int y = 400;

    public static void main(String[] args) {

        JFrame frame = new JFrame("Jumpy-Bird");

        frame.setSize(x, y);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.getContentPane().setBackground(Color.red);
    }

}
