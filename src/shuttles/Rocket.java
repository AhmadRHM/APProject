package shuttles;

import main.Drawable;
import main.MainFrame;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Rocket implements Drawable {
    private double x, y, width = 40, height = 40, vx, vy;
    private BufferedImage img;
    private MainFrame mainFrame;
    public Rocket(BufferedImage img, int vx, int vy, int x, int y, MainFrame mainFrame){
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.mainFrame = mainFrame;

        AffineTransform tx = new AffineTransform();
        tx.rotate(Math.atan2(vy, vx) + Math.PI/4, img.getWidth() / 2, img.getHeight() / 2);

        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

        this.img  = op.filter(img, null);
    }
    @Override
    public void update(double time) {
        x += vx * time;
        y += vy * time;
        if(Math.abs( x-800) < 1 || Math.abs(y-500) < 1){
            mainFrame.rocketBoomed();
            mainFrame.getItems().remove(this);
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(img, null, (int)(x-width/2), (int)(y-height/2));
    }
}
