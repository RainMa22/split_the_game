package me.rainma22.split;

import java.awt.*;
import java.awt.image.BufferedImage;

public class backdrop implements Displayable {
    private int x, y, speed;
    private BufferedImage image;

    public backdrop(boolean b) {
        if (b) x = Main.gf.getWidth();
        else x=(int) (Math.random() * Main.gf.getWidth() + 0.5);
        y = (int) (Math.random() * Main.gf.getHeight() + 0.5);
        speed = (int) (Math.random() * 5 + 1);
        image = new BufferedImage(speed, speed, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillOval(0, 0, speed, speed);
        g2d.dispose();
    }

    @Override
    public BufferedImage getImage(int i) {
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
        this.x = x;
    }

    @Override
    public void sety(int y) {
        this.y = y;
    }
    public int getSpeed(){
        return speed;
    }
}
