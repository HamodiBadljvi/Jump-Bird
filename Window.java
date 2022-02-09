import javax.swing.JFrame;



public class Window extends JFrame {
    
    
    public Window() {
        setTitle("test");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new GamePanel( 1280, 720));
        pack();
        setLocation(null);
        setVisible(true);
    }
        
         

    }

