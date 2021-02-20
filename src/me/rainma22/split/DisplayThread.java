package me.rainma22.split;

public class DisplayThread extends Thread {
    public boolean running = true;

    @Override
    public void run() {
        long current = System.currentTimeMillis();
        long prev = current, diff;
        while (running) {
            current = System.currentTimeMillis();
            diff = current - prev;
            if (diff >= (long) 1000 / 60) {
                prev += (long) 1000 / 60;
                Main.gf.repaint();
            }
        }
    }
}
