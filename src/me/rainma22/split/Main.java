package me.rainma22.split;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Main {
    private static boolean state=false;

    public synchronized static boolean isState() {
        return state;
    }

    public synchronized static void setState(boolean state) {
        Main.state = state;
    }

    public synchronized static boolean isStart() {
        return start;
    }

    public synchronized static void setStart(boolean start) {
        Main.start = start;
    }

    private static boolean start=false;
    public static GameFrame gf=new GameFrame();
    private static ArrayList<ball> balls=new ArrayList<>(2);
    private static ArrayList<Displayable> obstacles=new ArrayList<>(2);

    public synchronized static ArrayList<ball> getBalls() {
        return balls;
    }

    public static synchronized void  setBalls(ArrayList<ball> balls) {
        Main.balls = balls;
    }

    public static synchronized ArrayList<Displayable> getObstacles() {
        return obstacles;
    }

    public static synchronized void setObstacles(ArrayList<Displayable> obstacles) {
        Main.obstacles = obstacles;
    }

    public static double difficulty=1;
    public static int score=0;
    public static void main(String[] args) {
        Toolkit tk=Toolkit.getDefaultToolkit();
        gf.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
                    start=!start;
                    System.out.println(start);
                }else {
                    if (start)
                    state = !state;
                }
            }
        });
        balls.add(new ball());
        gf.add(new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                g.setColor(new Color(97,97,97,255));
                g.fillRect(0,0,gf.getWidth(),getHeight());
                g.setColor(Color.WHITE);
                g.setFont(g.getFont().deriveFont((float) (tk.getScreenSize().width/40)));
                g.drawString(String.valueOf(score),tk.getScreenSize().width/2-((g.getFont().getSize()*String.valueOf(score).length())/2),tk.getScreenSize().height/10-g.getFont().getSize()/2);
                for (Displayable displayable: (ArrayList<Displayable>)getBalls().clone()) {
                    g.drawImage(displayable.getImage(),displayable.getx(),displayable.gety(),gf);
                    //System.out.println("repainted");
                }
                for (Displayable displayable:(ArrayList<Displayable>)getObstacles().clone()) {
                    g.drawImage(displayable.getImage(),displayable.getx(),displayable.gety(),gf);
                }
            }
        });
        gf.setUndecorated(true);
        gf.setVisible(true);
        new computeThread().start();
        new DisplayThread().start();
        new DifficultyThread().start();
    }
}
