package chickenGroups;

import main.Drawable;

import java.awt.image.BufferedImage;

public class MaxTempBooster extends Drawable {
    public MaxTempBooster(double x, double y, double vx, double vy, BufferedImage image){
        this.x = x;
        this.y = y;

        this.vx = vx;
        this.vy = vy;

        this.image = image;
    }
}
