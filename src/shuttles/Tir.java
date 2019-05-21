package shuttles;

import assets.Assets;
import main.Drawable;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Tir implements Drawable {
    private  double x, y, vx, vy;
    private int type;
    private BufferedImage tir;
    private Assets assets;
    public Tir(double x, double y, double vx, double vy, int type, Assets assets){
        this.x = x;
        this.y = y;

        this.vx = vx;
        this.vy = vy;

        this.type = type;
        this.assets = assets;

        tir = assets.getFire(type);

        AffineTransform tx = new AffineTransform();
        tx.rotate(Math.atan2(vy, vx) + Math.PI/2, tir.getWidth() / 2, tir.getHeight() / 2);

        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

        tir  = op.filter(tir, null);
    }

    @Override
    public void update(double time) {
        x += vx * time;
        y += vy * time;
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(tir, (int)x - tir.getWidth()/2, (int)y - tir.getHeight()/2, null);
    }

    public double getX(){ return x; }
    public double getY() {
        return y;
    }
}
