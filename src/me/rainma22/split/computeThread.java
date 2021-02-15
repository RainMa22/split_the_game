package me.rainma22.split;


import java.awt.*;
import java.util.ArrayList;

public class computeThread extends Thread{

    @Override
    public void run() {
        long current=System.currentTimeMillis();
        long prev=current,diff;
        while (true){
            current=System.currentTimeMillis();
            if (Main.start){
            diff=current-prev;
            if (diff>=(long)1000/60){
                prev+=(long)1000/60;
                double speed=Main.difficulty*4;
                ArrayList<ball> balls= (ArrayList<ball>) Main.balls.clone();
                if (Main.state){
                    if (balls.size()==1) balls.add(new ball());
                    else{
                        ball now=balls.get(0);
                        now.sety((int)(now.gety()+speed+0.5));
                        now=balls.get(1);
                        now.sety((int)(now.gety()-speed+0.5));
                        if (now.gety()<=0) Main.state=!Main.state;
                    }

                }else{
                    if (balls.size()==2){
                        if (balls.get(0).gety()<=balls.get(1).gety()) balls.remove(1);
                        else{
                            ball now=balls.get(0);
                            now.sety((int)(now.gety()-speed+0.5));
                            now=balls.get(1);
                            now.sety((int)(now.gety()+speed+0.5));
                        }
                    }
                }
                for (Displayable obstacle: Main.obstacles) {
                    //if (obstacle.gety()<=balls.get(0).gety()+balls.get(0).getImage().getHeight()) Main.start=false;//&&obstacle.gety()+obstacle.getImage().getHeight())
                    obstacle.sety((int) (obstacle.getx()+speed+0.5));
                }
                Main.balls= (ArrayList<ball>) balls.clone();
                Main.score+=Main.difficulty;
            }
            }else{
                try {
                    prev=current;
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
