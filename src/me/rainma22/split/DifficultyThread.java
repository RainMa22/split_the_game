package me.rainma22.split;

public class DifficultyThread extends Thread{
    public boolean running=true;
    @Override
    public void run() {
        double d=1;
        while (running){
            if (Main.isStart()){
            try {
                Thread.sleep(60);
                d+=0.0001;
                Main.difficulty= 2.5*(Math.pow(d,2));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else{
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
