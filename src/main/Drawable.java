package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Drawable {
    protected double x, y, vx, vy;
    protected BufferedImage image;

    public void update(double time) {
        x += vx * time;
        y += vy * time;
    }
    public void draw(Graphics2D g){
        g.drawImage(image, (int)(x - image.getWidth()/2), (int)(y - image.getHeight()/2), null);
    }
    public double getX(){return x;}

    public double getY(){return y;}

    public Dimension getSize(){return new Dimension(image.getWidth(), image.getHeight());}

    public boolean hasImage(){return image!=null;}

    public boolean isOutOfPage(){
        return (x + image.getWidth()/2 < 0 || x - image.getWidth()/2 > 1600 || y + image.getHeight()/2 < 0 || y - image.getHeight()/2 > 1000);
    }
    @Override
    public String toString(){
        return (int)x + " " + (int)y;
    }
}
