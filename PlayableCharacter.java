import java.util.Arrays;

public class PlayableCharacter {
    //position[0] is x (horisontal) and position[1] is y (vertical)
    private int[] position = new int[2];
    private int[] size = new int[2];

    public PlayableCharacter(){
        this.position[0] = 10;
        this.position[1] = 10;
        this.size[0]     = 10;
        this.size[1]     = 10;
        
    }
    public PlayableCharacter(int posx, int posy,int height, int width){
        this.position[0] = posx;
        this.position[1] = posy;
        this.size[0]     = height;
        this.size[1]     = width;


    }

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    @Override
    public String toString() {
        //TODO fixa utskrift

        return "PlayableCharacter{" +
                "position=" + Arrays.toString(position) +
                '}';
    }
    public void yMovement(){
        //TODO
        //Method for moving character on y-axis
    }
}
