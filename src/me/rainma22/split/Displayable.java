package me.rainma22.split;

import java.awt.image.BufferedImage;

public interface Displayable {
    BufferedImage getImage(int i);

    int getx();

    int gety();

    void setx(int x);

    void sety(int y);
}
