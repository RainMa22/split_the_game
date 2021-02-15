package me.rainma22.split;

public class DifficultyThread extends Thread{
    @Override
    public void run() {
        double d=1;
        while (true){
            if (Main.isStart()){
            try {
                Thread.sleep(60);
                d+=0.0001;
                Main.difficulty= (5*Math.pow(d,2));
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
