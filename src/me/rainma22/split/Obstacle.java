package me.rainma22.split;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Obstacle implements Displayable{
    BufferedImage image;
    int x,y;
    public Obstacle(){
        Toolkit tk=Toolkit.getDefaultToolkit();
        image=new BufferedImage(20, 20,BufferedImage.TYPE_INT_RGB);
        x=tk.getScreenSize().width;
        y=tk.getScreenSize().height/2-image.getHeight()/2;
        Graphics2D g2d= image.createGraphics();
        g2d.setColor(Color.RED);
        g2d.fillRect(0,0,20,20);
        g2d.dispose();
    }
    @Override
    public BufferedImage getImage() {
        return image;
    }

    @Override
    public int getx() {
        return x;
    }

    @Override
    public int gety() {
        return y;
    }

    @Override
    public void setx(int x) {
        this.x=x;
    }

    @Override
    public void sety(int y) {
        this.y=y;
    }
}
