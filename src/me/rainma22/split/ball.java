package me.rainma22.split;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ball implements Displayable {
    private BufferedImage image;
    private final BufferedImage spriteList;
    private int x;
    private int y;
    private final boolean red;
    public boolean exploded=false;
    private BufferedImage explosion;

    public ball(BufferedImage bi, boolean red) {
        this.red = red;
        spriteList = bi;
        Toolkit tk = Toolkit.getDefaultToolkit();
        x = tk.getScreenSize().width / 30 - 50 / 2;
        y = tk.getScreenSize().height / 2 - 50 / 2;
        try {
            explosion = ImageIO.read(new File("explosion.png"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public BufferedImage getImage(int i) {
        if (exploded){
            image = new BufferedImage(100, 100, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g2d = image.createGraphics();
            g2d.drawImage(explosion,0,0,100,100,null);
            g2d.dispose();
            return image;
        }
        i=i%60;
        image = new BufferedImage(50, 50, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2d = image.createGraphics();
        int height = spriteList.getHeight() * (50 / spriteList.getWidth());
        if (red) {
            if (i < 20) g2d.drawImage(spriteList, 0, 0, 50, height, null);
            else if (i < 40) g2d.drawImage(spriteList, 0, -height / 6, 50, height, null);
            else g2d.drawImage(spriteList, 0, -height / 6 * 2, 50, height, null);
        } else {
            if (i < 20) g2d.drawImage(spriteList, 0, 0 - height / 2, 50, height, null);
            else if (i < 40) g2d.drawImage(spriteList, 0, -height / 6 - height / 2, 50, height, null);
            else g2d.drawImage(spriteList, 0, -height / 6 * 2 - height / 2, 50, height, null);
        }
        g2d.dispose();
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
}
