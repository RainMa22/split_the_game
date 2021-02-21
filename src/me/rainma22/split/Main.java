package me.rainma22.split;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    private static boolean skipMusic=false;
    public static synchronized boolean SkipMusic(){
        return skipMusic;
    }
    public static synchronized void SkipMusic(boolean b){
        skipMusic=b;
    }
    private static double scale=1;
    public static AudioHandler audioHandler=new AudioHandler();
    public static GameFrame gf = new GameFrame();
    public static double difficulty = 1;
    public static int score = 0;
    public static BufferedImage spriteList;
    private static final String[] menu = new String[]{"Continue", "Restart", "Exit"};
    private static int selected = 0;
    private static boolean state = false;
    private static computeThread ct = new computeThread();
    private static DisplayThread dt = new DisplayThread();
    private static DifficultyThread dft = new DifficultyThread();
    private static boolean failed = false;
    private static boolean start = false;
    private static ArrayList<ball> balls = new ArrayList<>(2);
    private static ArrayList<Displayable> obstacles = new ArrayList<>(2);
    private static ArrayList<backdrop> backdrops=new ArrayList<>(512);
    private static Planet planet;

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

    public synchronized static ArrayList<ball> getBalls() {
        return balls;
    }

    public static synchronized void setBalls(ArrayList<ball> balls) {
        Main.balls = balls;
    }

    public static synchronized ArrayList<Displayable> getObstacles() {
        return obstacles;
    }

    public static synchronized void setObstacles(ArrayList<Displayable> obstacles) {
        Main.obstacles = obstacles;
    }

    public synchronized static Planet getPlanet() {
        return planet;
    }

    public synchronized static void setPlanet(Planet planet) {
        Main.planet = planet;
    }

    public synchronized static ArrayList<backdrop> getBackdrops() {
        return backdrops;
    }

    public static synchronized void setBackdrops(ArrayList<backdrop> backdrops) {
        Main.backdrops = backdrops;
    }

    private static synchronized void reset(boolean s){
        try{
        score = 0;
        ct.running = false;
        dt.running = false;
        dft.running = false;
        backdrops.clear();
        backdropGenerator.init();
        ct = new computeThread();
        dt = new DisplayThread();
        dft = new DifficultyThread();
        ct.start();
        dt.start();
        dft.start();
            planet=new Planet(0);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        balls = new ArrayList<>(0);
        balls.add(new ball(spriteList, false));
        obstacles = new ArrayList<>(0);
        state = true;
        start = s;
        failed = false;
    }

    public static void main(String[] args) throws IOException {
        Main.audioHandler.playMusic();
        backdropGenerator.init();
        Toolkit tk = Toolkit.getDefaultToolkit();
        planet=new Planet(0);
        spriteList = ImageIO.read(new File("sprite.png"));
        gf.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (!start) {
                    if (e.getKeyCode() == KeyEvent.VK_UP) selected -= 1;
                    else if (e.getKeyCode() == KeyEvent.VK_DOWN) selected += 1;
                    else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        switch (selected) {
                            case 0:
                                start = true;
                                if (!isFailed()) break;
                            case 1:
                                reset(true);
                                break;
                            case 2:
                                Runtime.getRuntime().exit(0);
                        }
                    }
                    if (selected < 0) selected = 1 - selected;
                    selected = selected % 3;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    start = !start;
                    if (isFailed()) reset(true);
                } else if (e.getKeyCode()==KeyEvent.VK_N){
                    SkipMusic(true);
                }else{
                    if (start)
                        state = !state;
                }
            }
        });
        balls.add(new ball(spriteList, false));
        gf.add(new JPanel() {
            private int i = 0;

            @Override
            public void paintComponent(Graphics g) {
                ((Graphics2D) g).setRenderingHint(
                        RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
                ((Graphics2D) g).scale(scale,scale);
                g.setColor(new Color(0, 0, 30, 255));
                g.fillRect(0, 0, gf.getWidth(), getHeight());
                g.setColor(Color.WHITE);
                g.setFont(g.getFont().deriveFont((float) (tk.getScreenSize().width / 40)));
                FontMetrics fm = g.getFontMetrics();
                g.drawString(String.valueOf(score), tk.getScreenSize().width / 2 - (fm.stringWidth(String.valueOf(score)) / 2), tk.getScreenSize().height / 10 - g.getFont().getSize() / 2);
                for (Displayable displayable : (ArrayList<Displayable>) getBackdrops().clone()) {
                    g.drawImage(displayable.getImage(i), displayable.getx(), displayable.gety(), gf);
                }
                BufferedImage planetImg=planet.getImage(i);
                g.drawImage(planetImg, planet.getx(),  planet.gety(),(int)(planetImg.getWidth()* planet.getScale()), (int) (planetImg.getHeight()*planet.getScale()),gf);
                for (ball displayable : (ArrayList<ball>) getBalls().clone()) {
                    if (displayable.exploded) {
                        g.drawImage(displayable.getImage(i), displayable.getx()-displayable.getImage(i).getWidth()/4, displayable.gety()-displayable.getImage(i).getHeight()/4, gf);
                    }else g.drawImage(displayable.getImage(i), displayable.getx(), displayable.gety(), gf);
                }
                for (Displayable displayable : (ArrayList<Displayable>) getObstacles().clone()) {
                    g.drawImage(displayable.getImage(i), displayable.getx(), displayable.gety(), gf);
                }

                if (!start) {
                    g.setColor(new Color(0, 0, 0, 150));
                    g.fillRect(0, 0, gf.getWidth(), gf.getHeight());
                    for (int i = 0; i < menu.length; i++) {
                        if (selected == i) {
                            g.setColor(new Color(0, 164, 253, 255));
                        } else {
                            g.setColor(Color.WHITE);
                        }
                        g.drawString(menu[i], (gf.getWidth() - (fm.stringWidth(menu[i]))) / 2, tk.getScreenSize().height / 5 * (i + 2) - fm.getHeight() / 2);
                    }
                } else {
                    i++;
                }
            }
        });
        gf.setUndecorated(true);
        gf.setVisible(true);
        reset(false);
    }



}
