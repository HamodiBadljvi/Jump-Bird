
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class GameMenu extends JPanel {
    private JButton button1, button2, button3;

    public GameMenu() {
        //JPanel panel = new JPanel();
        //panel.setLayout(new BorderLayout());
        this.setLayout(new BorderLayout());
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                GameSurface.setDifficulty(0);
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                GameSurface.setDifficulty(1);
            }
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                GameSurface.setDifficulty(2);
            }
        });
this.add(button1,BorderLayout.WEST);
    }

}
