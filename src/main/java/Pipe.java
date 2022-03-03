import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.Rectangle;

public class Pipe {
    List<Rectangle> pipes;
    int space = 150;
    int width = 50;
    int randHeight;

    public List<Rectangle> addPipe() {
        pipes = new ArrayList<Rectangle>();
        randHeight = ThreadLocalRandom.current().nextInt(space + 20, App.HEIGHT - 19);

        // Lower pipe
        pipes.add(new Rectangle(App.WIDTH + width, randHeight, width, App.HEIGHT - randHeight));
        // Upper pipe
        pipes.add(new Rectangle(App.WIDTH + width, 0, width, randHeight - space));

        return pipes;
    }
}
