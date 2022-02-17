public class HighScore {
    private int highScore;

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int score) {
        if (score > highScore) {
            this.highScore = score;
        }
    }

    @Override
    public String toString() {
        return "HighScore:\n" + highScore;
    }
}