package me.rainma22.split;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Planet implements Displayable{
    private int x,y,i;
    private BufferedImage image;
    private int width,height,scale;
    public Planet(int i) throws IOException {
        i%=9;
        image=ImageIO.read(new File("planet" + i + ".png"));
        x=Main.gf.getWidth();
        y=Main.gf.getHeight()/2;
        if (i==0||i==6)y=0;
        this.i=i;
        scale=2;
        if (getI()==0)scale=10;
        else if (getI()<=2) scale=1;
        else if(getI()>=7) scale=3;
        else if(getI()>=5)scale=5;
        width=image.getWidth();
        height=image.getHeight();

    }
    @Override
    public BufferedImage getImage(int i) {
        i%=480;
        BufferedImage tmp=new BufferedImage(height,height,image.getType());
        Graphics2D g2d=tmp.createGraphics();
        g2d.drawImage(image,-width/60*(i/8),0,width,height,null);
        g2d.dispose();
        return tmp;
    }
    public int getI(){return i;}

    public double getScale() {
        return scale;
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
