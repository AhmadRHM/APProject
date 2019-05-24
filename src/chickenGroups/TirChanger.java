package chickenGroups;

import main.Drawable;

import java.awt.image.BufferedImage;

public class TirChanger extends Drawable {
    private int type;
    public TirChanger(int type, double x, double y, double vx, double vy, BufferedImage image){
        this.type = type;

        this.x = x;
        this.y = y;

        this.vx = vx;
        this.vy = vy;

        this.image = image;
    }

    public int getType() {
        return type;
    }
}
