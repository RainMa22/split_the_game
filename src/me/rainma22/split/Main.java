package me.rainma22.split;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Main {
    public static boolean state=false;
    public static boolean start=false;
    public static GameFrame gf=new GameFrame();
    public static ArrayList<ball> balls=new ArrayList<>(2);
    public static ArrayList<Displayable> obstacles=new ArrayList<>(2);
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
                for (Displayable displayable: balls) {
                    g.drawImage(displayable.getImage(),displayable.getx(),displayable.gety(),gf);
                    //System.out.println("repainted");
                }
                for (Displayable displayable:obstacles) {
                    g.drawImage(displayable.getImage(),displayable.getx(),displayable.gety(),gf);
                }
            }
        });
        gf.setUndecorated(true);
        gf.setVisible(true);
        new ObstacleGenerator().start();
        new computeThread().start();
        new DisplayThread().start();
        new DifficultyThread().start();
    }
}
