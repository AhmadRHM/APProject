package chickenGroups;

import main.Drawable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Egg implements Drawable {
    private double x, y, vx, vy;
    private BufferedImage image;

    public Egg(double x, double y, double vx, double vy, BufferedImage image){
        this.x = x;
        this.y = y;

        this.vx = vx;
        this.vy = vy;

        this.image = image;
    }

    @Override
    public void update(double time) {
        x += vx * time;
        y += vy * time;
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(image, (int)(x - image.getWidth()), (int)(y - image.getHeight()/2), null);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    public Dimension getSize(){
        return new Dimension(image.getWidth(), image.getHeight());
    }
    public boolean remove(){
        return ((int) (y - image.getHeight() / 2) > 1000);
    }
}
