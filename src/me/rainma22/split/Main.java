package me.rainma22.split;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Main {
    private static String[] menu=new String[]{"Continue","restart","Exit"};
    private static int selected=0;
    private static boolean state=false;
    private static computeThread ct= new computeThread();
    private static DisplayThread dt= new DisplayThread();
    private static DifficultyThread dft=new DifficultyThread();
    private static boolean failed=false;

    public static synchronized boolean isFailed() {
        return failed;
    }

    public static synchronized void setFailed(boolean failed) {
        Main.failed = failed;
    }

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
                if (!start){
                    if (e.getKeyCode()==KeyEvent.VK_UP) selected-=1;
                    else if (e.getKeyCode()==KeyEvent.VK_DOWN) selected+=1;
                    else if (e.getKeyCode()==KeyEvent.VK_ENTER){
                        switch (selected){
                            case 0:
                                start=true;
                                if (!isFailed()) break;
                            case 1:
                                start=true;
                                score=0;
                                ct.running=false;
                                dt.running=false;
                                dft.running=false;
                                ct=new computeThread();
                                dt=new DisplayThread();
                                dft=new DifficultyThread();
                                ct.start();
                                dt.start();
                                dft.start();
                                balls=new ArrayList<>(0);
                                balls.add(new ball());
                                obstacles=new ArrayList<>(0);
                                state=false;
                                break;
                            case 2:
                                Runtime.getRuntime().exit(0);
                        }
                    }
                    selected=selected%3;
                }
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
                FontMetrics fm=g.getFontMetrics();
                g.drawString(String.valueOf(score),tk.getScreenSize().width/2-(fm.stringWidth(String.valueOf(score))/2),tk.getScreenSize().height/10-g.getFont().getSize()/2);

                for (Displayable displayable: (ArrayList<Displayable>)getBalls().clone()) {
                    g.drawImage(displayable.getImage(),displayable.getx(),displayable.gety(),gf);
                    //System.out.println("repainted");
                }
                for (Displayable displayable:(ArrayList<Displayable>)getObstacles().clone()) {
                    g.drawImage(displayable.getImage(),displayable.getx(),displayable.gety(),gf);
                }
                if (!start){
                    g.setColor(new Color(0, 0, 0, 150));
                    g.fillRect(0,0,gf.getWidth(),gf.getHeight());
                    for (int i = 0; i < menu.length; i++) {
                        if (selected==i){
                            g.setColor(new Color(0, 164, 253,255));
                        }else{
                            g.setColor(Color.WHITE);
                        }
                        g.drawString(menu[i], (gf.getWidth()-(fm.stringWidth(menu[i])))/2,tk.getScreenSize().height/5*(i+2)-fm.getHeight()/2);
                    }
                }
            }
        });
        gf.setUndecorated(true);
        gf.setVisible(true);
        ct.start();
        dt.start();
        dft.start();
    }
}
