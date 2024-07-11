package chapter_three;

import java.text.SimpleDateFormat;
import java.util.Date;

class Clock extends Thread {
    private volatile boolean running = true;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");

    @Override
    public void run() {
        while (running) {
            System.out.println(sdf.format(new Date()));
            try {
                Thread.sleep(1000); // Update and display every second
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void stopClock() {
        running = false;
    }
}
