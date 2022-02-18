import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/* w  ww.  j  a  va 2  s .  com*/
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class asdfasdfasdf extends JFrame {
    JFrame frame = new JFrame();

    public asdfasdfasdf() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Panel());
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new asdfasdfasdf();
    }
}





class Panel extends JPanel {
    Timer timer;

    Panel() {
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
        refreshScreen();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("arial", Font.PLAIN, 24));
        g.drawString("java2s.com", 200, 200);
    }

    public void refreshScreen() {
        timer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        timer.setRepeats(true);
        // Aprox. 60 FPS
        timer.setDelay(17);
        timer.start();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(650, 480);
    }
}