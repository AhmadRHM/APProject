package chickenGroups;

import main.Drawable;

import java.awt.image.BufferedImage;

public class Coin extends Drawable {
    public Coin(BufferedImage image , double x, double y, double vx, double vy){
        this.image = image;

        this.x = x;
        this.y = y;

        this.vx = vx;
        this.vy = vy;
    }
}
