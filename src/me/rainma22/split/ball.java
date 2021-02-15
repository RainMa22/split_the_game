package me.rainma22.split;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ball implements Displayable{
    private BufferedImage image;
    private int x; private int y;
    public ball(){
        image=new BufferedImage(50,50,BufferedImage.TYPE_4BYTE_ABGR);
        Toolkit tk=Toolkit.getDefaultToolkit();
        x=tk.getScreenSize().width/30-image.getWidth()/2;
        y=tk.getScreenSize().height/2-image.getHeight()/2;
        Graphics2D g2d=image.createGraphics();
        g2d.setColor(new Color(0,0,0,0));
        g2d.fillRect(0,0,50,50);
        g2d.setColor(Color.BLACK);
        g2d.fillOval(0,0,50,50);
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
