
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

public class GameMenu extends JPanel implements ActionListener {
    private JButton easy, medium, hard, close, play;
    private BorderLayout layout;
    private int difficulty = 1;

    public GameMenu() {
        layout = new BorderLayout();
        this.setLayout(layout);
        easy = new JButton("Easy");
        easy.setPreferredSize(new Dimension(150, 100));
        medium = new JButton("Medium");
        medium.setPreferredSize(new Dimension(150, 100));
        hard = new JButton("Hard");
        hard.setPreferredSize(new Dimension(150, 100));
        close = new JButton("Close");
        close.setPreferredSize(new Dimension(150, 100));
        play = new JButton("Play");
        play.setPreferredSize(new Dimension(150, 100));

        this.setBackground(Color.BLUE);
        addButtons();

    }

    public void addButtons() {
        easy.addActionListener(this);
        medium.addActionListener(this);
        hard.addActionListener(this);
        close.addActionListener(this);
        play.addActionListener(this);

        this.add(easy, BorderLayout.LINE_START);
        this.add(medium, BorderLayout.CENTER);
        this.add(hard, BorderLayout.LINE_END);
        this.add(close, BorderLayout.PAGE_END);
        this.add(play, BorderLayout.PAGE_START);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();
        if (source == easy) {
            difficulty = 0;

        } else if (source == medium) {
            difficulty = 1;

        } else if (source == hard) {
            difficulty = 2;

        } else if (source == close) {
            System.exit(0);

        } else if (source == play) {
            App.setDifficulty(difficulty);
            App.startGame();
        }
    }

}
