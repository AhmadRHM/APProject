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
    private Random random = new Random(System.currentTimeMillis());
    private double x, y, vx, vy;
    private int type;
    private double nextX, nextY;
    BufferedImage image;
    private double percentOfHatching;
    private double life;
    private double eggSpeed;
    private MainFrame mainFrame;
    private double timeMod1 = 0;

    private void fillTypeNumbers(){
        if(type == 1){
            percentOfHatching = 0.05;
            life = 2;
            eggSpeed = 50;
        }else if(type == 2){
            percentOfHatching = 0.05;
            life = 3;
            eggSpeed = 50;
        }else if(type == 3){
            percentOfHatching = 0.1;
            life = 5;
            eggSpeed = 100;
        }else if(type == 4){
            percentOfHatching = 0.2;
            life = 8;
            eggSpeed = 100;
        }
    }

    public Chicken(double x, double y, double vx, double vy, int type, BufferedImage image, MainFrame mainFrame){
        this.x = x;
        this.y = y;

        this.type = type;

        this.vx = vx;
        this.vy = vy;

        this.image = image;
        fillTypeNumbers();

        this.mainFrame = mainFrame;
    }
    public Chicken(double x, double y, int type, BufferedImage image, MainFrame mainFrame){
        this.x = x;
        this.y = y;

        this.vx = 0;
        this.vy = 0;

        this.type = type;

        this.image = image;
        fillTypeNumbers();

        this.mainFrame = mainFrame;
    }
    public Chicken(double x, double y, double vx, double vy, int type, double degree, BufferedImage image, MainFrame mainFrame){
        this.x = x;
        this.y = y;

        this.vx = vx;
        this.vy = vy;

        this.type = type;

        this.image = image;
        rotateImage(degree);
        fillTypeNumbers();

        this.mainFrame = mainFrame;
    }
    public void rotateImage(double degree){
        AffineTransform tx = new AffineTransform();
        double deg = Math.toRadians(degree);
        tx.rotate(deg , image.getWidth() / 2, image.getHeight() / 2);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        this.image  = op.filter(image, null);
    }
    void setNext(double nextX, double nextY){
        this.nextX = nextX;
        this.nextY = nextY;

        double dx = nextX - x;
        double dy = nextY - y;
        double d = Math.sqrt(dx*dx + dy*dy);

        this.vx = dx / d * 100;
        this.vy = dy / d * 100;
    }

    @Override
    public void update(double time) {
        x += vx * time;
        y += vy * time;
////        System.out.println(m);
//        if (Math.abs(random.nextInt()) % 20000 <= 10)
//            this.throwEgg();
        this.timeMod1 += time;
        if(timeMod1 > 1){
            timeMod1 -= 1;
            double mod = (double)1 / percentOfHatching;
            int m = (int) Math.floor(mod);
            System.out.println(m);
            if(Math.abs(random.nextInt()) % m == 0){
                this.throwEgg();
            }
        }
    }

    private void throwEgg(){
        //TODO
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
    public void reduceLives(int damage){
        life -= damage;
    }
    public double getLife(){
        return life;
    }
}
