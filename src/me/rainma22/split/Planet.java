package me.rainma22.split;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Planet implements Displayable{
    private int x,y;
    private BufferedImage image;
    public Planet(int i) throws IOException {
        i%=1;
        image=ImageIO.read(new File("planet" + i + ".png"));
        x=Main.gf.getWidth();
        y=Main.gf.getHeight()/2;

    }
    @Override
    public BufferedImage getImage(int i) {
        i%=600;
        BufferedImage tmp=new BufferedImage(500,500,image.getType());
        Graphics2D g2d=tmp.createGraphics();
        g2d.scale(5,5);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2d.drawImage(image,-image.getWidth()/60*(i/10),0,6000,100,null);
        g2d.dispose();
        return tmp;
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
