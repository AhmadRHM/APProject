package shuttles;

import assets.Assets;
import main.Drawable;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Tir implements Drawable {
    private  double x, y, vx, vy, power = 1;
    private int type;
    private BufferedImage image;
    private Assets assets;

    public Tir(double x, double y, double vx, double vy, int type, int degree, double power, Assets assets){
        this(x,y,vx,vy,type,degree,assets);
        this.power = power;
    }
    public Tir(double x, double y, double vx, double vy, int type, int degree, Assets assets){
        this.x = x;
        this.y = y;

        this.vx = vx;
        this.vy = vy;


        this.type = type;
        this.assets = assets;

        image = assets.getFire(type);

        AffineTransform tx = new AffineTransform();
        double deg = Math.toRadians(degree);
        this.vx = Math.tan(deg) * 400;
        tx.rotate(deg , image.getWidth() / 2, image.getHeight() / 2);

        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

        image = op.filter(image, null);
    }

    @Override
    public void update(double time) {
        x += vx * time;
        y += vy * time;
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(image, (int)x - image.getWidth()/2, (int)y - image.getHeight()/2, null);
    }

    public double getX(){ return x; }
    public double getY() {
        return y;
    }
    public boolean remove(){
        return ((int)(y + image.getHeight()/2) < 0);
    }
    public Dimension getSize(){
        return new Dimension(image.getWidth(), image.getHeight());
    }

    public double getPower() {
        return power;
    }
}
