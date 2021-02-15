package me.rainma22.split;

public class ObstacleGenerator extends Thread{
    @Override
    public void run() {
        long current = System.currentTimeMillis();
        long prev = current, diff;
        while (true) {
            current = System.currentTimeMillis();
            if (Main.start) {
                diff = current - prev;
                if (diff >= (long) 1000 / 24) {
                    prev += (long) 1000 / 24;
                    double rand = Math.random();
                    System.out.println(rand);
                    if (rand >= 0.7) {
                        Main.obstacles.add(new Obstacle());
                    }
                }
            }
        }
    }
}