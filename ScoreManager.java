
public class ScoreManager {
    public static void main(String[] args) {

    }

    int score;

    public void increase() {
        score++;
        drawScore();
    }

    public void reset() {
        score = 0;
        drawScore();
    }

    private void drawScore() {// Har tillfällt valt att skriva ut poängen. Todo rita ut det på skärmen.

        System.out.println(score);
    }

}
