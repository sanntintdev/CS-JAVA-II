package chapter_three;

public class Main {
    public static void main(String[] args) {
        Clock clock = new Clock();
        // Set high priority for the clock thread
        clock.setPriority(Thread.MAX_PRIORITY);

        clock.start();

        // Run for 10 seconds for the sake of the example
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        clock.stopClock();
    }
}