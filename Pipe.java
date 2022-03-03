import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.Rectangle;

public class Pipe {
    List<Rectangle> pipes;
    int space = 150;
    int width = 50;
    int randHeight;

    public List<Rectangle> addPipe(boolean start) {
        pipes = new ArrayList<Rectangle>();
        randHeight = ThreadLocalRandom.current().nextInt(space + 20, App.HEIGHT - 19);

        if (start) {
            // Lower pipe
            pipes.add(new Rectangle(App.WIDTH + width, randHeight, width, App.HEIGHT - randHeight));
            // Upper pipe
            pipes.add(new Rectangle(App.WIDTH + width, 0, width, randHeight - space));
        } else {
            pipes.add(new Rectangle(pipes.get(pipes.size() - 1).x + 600, App.HEIGHT - randHeight - 120, width,
                    randHeight));
            pipes.add(new Rectangle(pipes.get(pipes.size() - 1).x, 0, width, App.HEIGHT - randHeight - space));
        }
        return pipes;
    }
}
