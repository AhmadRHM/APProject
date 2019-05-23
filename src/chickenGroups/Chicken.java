package chickenGroups;

import main.Drawable;
import main.Main;
import main.MainFrame;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Chicken implements Drawable {
    private Random random;
    private double x, y, vx, vy;
    private int type;
    private double nextX, nextY;
    private BufferedImage image;
    private double percentOfHatching;
    private double life;
    private double eggSpeed;
    private MainFrame mainFrame;
    private double timeMod1 = 0;
    private int frame = 0, frame0;
    private double degree = 0;
    private double speed = 300;

    private void fillTypeNumbers(){
        if(type == 1){
            percentOfHatching = 0.05;
            life = 2;
            eggSpeed = 100;
        }else if(type == 2){
            percentOfHatching = 0.05;
            life = 3;
            eggSpeed = 100;
        }else if(type == 3){
            percentOfHatching = 0.1;
            life = 5;
            eggSpeed = 200;
        }else if(type == 4){
            percentOfHatching = 0.2;
            life = 8;
            eggSpeed = 200;
        }
    }

    public Chicken(double x, double y, double vx, double vy, int type, int seed, int frame, MainFrame mainFrame){
        this(x, y, type, seed, frame, mainFrame);
        this.vx = vx;
        this.vy = vy;
    }
    public Chicken(double x, double y, int type, int seed, int frame, MainFrame mainFrame){
        this.x = x;
        this.y = y;

        this.vx = 0;
        this.vy = 0;

        this.type = type;

        this.frame0 = frame;
        System.out.println(frame);

        image = mainFrame.getAssets().getChicken(type, frame);

        fillTypeNumbers();

        this.mainFrame = mainFrame;

        random = new Random(seed);
    }
    public Chicken(double x, double y, double vx, double vy, int type, int seed, double degree, int frame, MainFrame mainFrame){
        this(x, y, vx, vy, type, seed, frame, mainFrame);

        this.degree = degree;
        rotateImage(degree);
        fillTypeNumbers();

    }
    public void rotateImage(double degree){
        AffineTransform tx = new AffineTransform();
        double deg = Math.toRadians(degree);
        tx.rotate(deg , image.getWidth() / 2, image.getHeight() / 2);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        this.image  = op.filter(image, null);
    }
    public void setNext(double nextX, double nextY){
        this.nextX = nextX;
        this.nextY = nextY;

        double dx = nextX - x;
        double dy = nextY - y;
        double d = Math.sqrt(dx*dx + dy*dy);

        this.vx = dx / d * speed;
        this.vy = dy / d * speed;
    }

    @Override
    public void update(double time) {
        x += vx * time;
        y += vy * time;
        if(x == nextX)
            vx = 0;
        if(y == nextY)
            vy = 0;
////        System.out.println(m);
//        if (Math.abs(random.nextInt()) % 20000 <= 10)
//            this.throwEgg();
        this.timeMod1 += time;
        if(timeMod1 > 1){
            timeMod1 -= 1;
            //egg throwing
            double mod = (double)1 / percentOfHatching;
            int m = (int) Math.floor(mod);
//            System.out.println(m);
            if(Math.abs(random.nextInt()) % m == 0){
                this.throwEgg();
            }
        }
        //changing mode
//        frame++;
        int lastFrame = frame;
        frame = frame0 + (int)(timeMod1 * 26);
        frame %= 13;
        if(lastFrame != frame) {
            if (frame < 7)
                image = mainFrame.getAssets().getChicken(type, frame);
            else
                image = mainFrame.getAssets().getChicken(type, 12 - frame);
            rotateImage(degree);
        }
    }

    private void throwEgg(){
        mainFrame.getItems().add(new Egg(x, y, 0, eggSpeed, mainFrame.getAssets().getEgg()));
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(image, (int)(x - image.getWidth()/2), (int)(y - image.getHeight()/2), null);
    }

    public double getNextY() {
        return nextY;
    }

    public double getNextX() {
        return nextX;
    }
    public void reduceLives(double damage){
        life -= damage;
    }
    public double getLife(){
        return life;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
    public Dimension getSize(){
        return new Dimension(image.getWidth(), image.getHeight());
    }
    public double getX(){return x;}
    public double getY(){return y;}

    public void killed(){
        //TODO
    }
}
