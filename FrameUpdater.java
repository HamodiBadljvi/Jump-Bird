

public class FrameUpdater {
    private final long timeBetweenUpdates;
    private GameSurface surface;

    public FrameUpdater(GameSurface surface, int fps) {
        this.surface = surface;
        this.timeBetweenUpdates = Math.floorDiv(1_000_000_000, fps);
    }

    public void start() {
    }

    public void setDaemon(boolean b) {
    }

}
