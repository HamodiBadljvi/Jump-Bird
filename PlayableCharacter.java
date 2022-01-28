import java.util.Arrays;

public class PlayableCharacter {
    //position[0] is x (horisontal) and position[1] is y (vertical)
    private int[] position = new int[2];
    //Lägga till size

    //private int height;
    //private int width;

    public PlayableCharacter(){
        this.position[0] = 10;
        this.position[1] = 10;
    }
    public PlayableCharacter(int x, int y){
        this.position[0] = x;
        this.position[1] = y;

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
}
