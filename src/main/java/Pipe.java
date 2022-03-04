import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.Rectangle;

public class Pipe {
    List<Rectangle> pipes;
    int space = 200;
    int width = 50;
    int randHeight;

    public void addPipe(List<Rectangle> pipes) {
        randHeight = ThreadLocalRandom.current().nextInt(space + 20, App.HEIGHT - 19);

        // Lower pipe
        pipes.add(new Rectangle(App.WIDTH, randHeight, width, App.HEIGHT - randHeight));
        // Upper pipe
        pipes.add(new Rectangle(App.WIDTH, 0, width, randHeight - space));
    }

    // TODO: Use for difficulty
    public int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        this.space = space;
    }
}
