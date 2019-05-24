package chickenGroups;

import main.Drawable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TirBooster extends Drawable {
    public TirBooster(double x, double y, double vx, double vy, BufferedImage image){
        this.x = x;
        this.y = y;

        this.vx = vx;
        this.vy = vy;

        this.image = image;
    }
}
