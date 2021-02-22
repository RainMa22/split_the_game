package me.rainma22.split;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Obstacle implements Displayable {
    private BufferedImage image;
    private int x, y,height,id,scale;
    boolean clockwise;
    public Obstacle() throws IOException {
        Toolkit tk = Toolkit.getDefaultToolkit();
        image = ImageIO.read(new File("Astroid.png"));
        x = tk.getScreenSize().width;
        double d = Math.random();
        height=image.getHeight();
        id= (int) (d*20+0.5);
        clockwise=d>=.5;
        scale=32/height;
        d=Math.random();
        y = (int) ((tk.getScreenSize().getHeight() * d - 32 / 2) + .5);
        /*if (d<=.3333) y=50-image.getHeight()/2;
        else if (d<=.6666)y=tk.getScreenSize().height/2-image.getHeight()/2;
        else y=tk.getScreenSize().height-50-image.getHeight();*/
    }

    @Override
    public BufferedImage getImage(int i) {
        i-=id;
        i%=360;
        BufferedImage tmp=new BufferedImage(height*scale,height*scale,image.getType());
        Graphics2D g2d=tmp.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2d.drawImage(image,-height*scale*(i/6),0,image.getWidth()*scale,height*scale, null);
        g2d.dispose();
        if (Main.performanceMode) return tmp;
        BufferedImage tmp2=new BufferedImage(height*scale,height*scale,image.getType());
        g2d= tmp2.createGraphics();
        if (clockwise)g2d.rotate(Math.toRadians(i),tmp.getWidth()/2,tmp.getWidth()/2);
        else g2d.rotate(-Math.toRadians(i),tmp.getWidth()/2,tmp.getWidth()/2);
        g2d.drawImage(tmp,0,0,null);
        g2d.dispose();
        return tmp2;
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
}
