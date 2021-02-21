package me.rainma22.split;


import java.io.IOException;
import java.util.ArrayList;

public class computeThread extends Thread {
    public boolean running = true;

    @Override
    public void run() {
        long current = System.currentTimeMillis();
        long prev = current, diff;
        int i = 0;
        while (running) {
            current = System.currentTimeMillis();
            if (Main.isStart()) {
                diff = current - prev;
                if (diff >= (long) 1000 / 60) {
                    prev += (long) 1000 / 60;
                    double speed = Main.difficulty * 4;
                    ArrayList<ball> balls = (ArrayList<ball>) Main.getBalls().clone();
                    if (Main.isState()) {
                        if (balls.size() == 1) balls.add(new ball(Main.spriteList, true));
                        else {
                            ball now = balls.get(0);
                            now.sety((int) (now.gety() + speed + 0.5));
                            now = balls.get(1);
                            now.sety((int) (now.gety() - speed + 0.5));
                            if (now.gety() <= 0) Main.setState(!Main.isState());
                        }

                    } else {
                        if (balls.size() == 2) {
                            if (balls.get(0).gety() <= balls.get(1).gety()) {
                                //balls.remove(1);
                                Main.setState(true);
                            } else {
                                ball now = balls.get(0);
                                now.sety((int) (now.gety() - speed + 0.5));
                                now = balls.get(1);
                                now.sety((int) (now.gety() + speed + 0.5));
                            }
                        }
                    }
                    ArrayList<Displayable> remove = new ArrayList<>(0);
                    ArrayList<Displayable> obstacles = (ArrayList<Displayable>) Main.getObstacles().clone();
                    ArrayList<backdrop> backdrops= (ArrayList<backdrop>) Main.getBackdrops().clone();
                    i += Main.difficulty + Main.difficulty * Math.random() * 10;
                    /*if (i >= 500) {
                        obstacles.add(new Obstacle());
                        i %= 500;
                    }
                    for (Displayable obstacle : obstacles) {
                        obstacle.setx((int) (obstacle.getx() - speed + 0.5));
                        if (obstacle.getx() <= 0) remove.add(obstacle);
                        for (Displayable ball : balls) {
                            if (ball.getx() + ball.getImage(0).getWidth() >= obstacle.getx() && ball.getx() <= obstacle.getx() + obstacle.getImage(0).getWidth()) {
                                if (ball.gety() + ball.getImage(0).getHeight() >= obstacle.gety() && ball.gety() <= obstacle.gety() + obstacle.getImage(0).getHeight()) {
                                    Main.setStart(false);
                                    Main.setFailed(true);
                                }
                            }
                        }
                    }*/
                    ArrayList<backdrop> tmp=new ArrayList<>(0);
                    for (backdrop bd:backdrops) {
                        bd.setx(bd.getx()-bd.getSpeed());
                        if (bd.getx()<=0)tmp.add(bd);
                    }
                    backdrops.remove(tmp);
                    Main.setBackdrops(backdrops);
                    Planet planet=Main.getPlanet();
                    planet.setx((int) (planet.getx()-speed));
                    if (planet.getx()+(planet.getImage(0).getWidth()* planet.getScale())<=0) {
                        try {
                            planet=new Planet(planet.getI()+1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Main.setPlanet(planet);
                    obstacles.removeAll(remove);
                    Main.setBalls((ArrayList<ball>) balls.clone());
                    Main.setObstacles((ArrayList<Displayable>) obstacles.clone());
                    Main.score += Main.difficulty;
                    backdropGenerator.generate(1,true);
                }
            } else {
                try {
                    prev = current;
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
