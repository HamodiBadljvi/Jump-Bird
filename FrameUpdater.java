import javax.swing.SwingUtilities;

public class FrameUpdater extends Thread {
    private final long timeBetweenUpdates;
    private GameSurface surface;

    public FrameUpdater(GameSurface surface, int fps) {
        this.surface = surface;
        this.timeBetweenUpdates = Math.floorDiv(1_000_000_000, fps);
    }

    @Override
    public void run() {
        final long startTime = System.nanoTime();

        while (!isInterrupted()) {
            long currentTime = System.nanoTime();
            long timeAtNextUpdate = currentTime + timeBetweenUpdates;

            surface.update((int) ((currentTime - startTime) / 1_000_000));

            SwingUtilities.invokeLater(() -> surface.repaint());

            sleepRemaining(timeAtNextUpdate - 1_000_000);

            while (System.nanoTime() < timeAtNextUpdate) {
                yield();
            }
        }
    }

    public void sleepRemaining(long sleepToNanos) {
        long totalNanosLeft = sleepToNanos - System.nanoTime();
        long millisLeft = totalNanosLeft / 1_000_000;
        int nanosLeft = (int) (totalNanosLeft - millisLeft * 1_000_000);

        if (millisLeft > 0 || nanosLeft > 0) {
            try {
                Thread.sleep(millisLeft, nanosLeft);
            } catch (InterruptedException ex) {
                interrupt();
            }
        }
    }
}
