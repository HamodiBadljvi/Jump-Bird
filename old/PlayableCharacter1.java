package old;
import java.util.Arrays;

//THIS CLASS IS NOT USED
//WILL PROBABLY NOT BE USED

public class PlayableCharacter1 {
    // position[0] is x (horisontal) and position[1] is y (vertical)
    //Maybe x axis is of no use
    private int[] position = new int[2];
    private int[] size = new int[2];
    

    public PlayableCharacter1() {
        this.position[0] = 10;
        this.position[1] = 10;
        this.size[0] = 10;
        this.size[1] = 10;

    }

    public PlayableCharacter1(int posx, int posy, int height, int width) {
        this.position[0] = posx;
        this.position[1] = posy;
        this.size[0] = height;
        this.size[1] = width;

    }

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "PlayableCharacter [position x =" + position[0] + " position y " + position[1]
                + ", size=" + size[0] + " x " + size[1] + "]";
    }

    public void yMovement() {
        // TODO
        // Method for moving character on y-axis
    }
}
