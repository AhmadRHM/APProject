package chickenGroups;

import main.MainFrame;
import shuttles.Tir;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class BigEgg extends Chicken {
    private double timeModNim = 0;
    private MainFrame mainFrame;
    public BigEgg(double x, double y, int lives, MainFrame mainFrame){
        super(x, y, 1, (int)(System.currentTimeMillis()%(Integer.MAX_VALUE)), 0, mainFrame);
        this.mainFrame = mainFrame;
        this.image = mainFrame.getAssets().getBigEgg();
        this.life = lives;
        this.life *= (int)Math.sqrt(mainFrame.getPlayingUsers().size());
        this.setSpeed(50);
    }
    @Override
    protected void throwEgg(){
        for(int i=0; i<8; i++){
            int t = Math.abs(random.nextInt())%100;
            if(t>25)
                continue;
            int r = image.getWidth()-100;
            double teta = 90 - 45 * i;
            double rad = Math.toRadians(teta);
            double degree = Math.toRadians(-teta + 90);

            BufferedImage image = mainFrame.getAssets().getFire(4);

            AffineTransform tx = new AffineTransform();
            tx.rotate(degree , image.getWidth() / 2, image.getHeight() / 2);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

            image = op.filter(image, null);

            mainFrame.getItems().add(new Egg(x + r * Math.cos(rad), y - r * Math.sin(rad), 400 * Math.cos(rad), -400 * Math.sin(rad), image));
        }
    }

    @Override
    public void update(double time){
        super.update(time);
        this.image = mainFrame.getAssets().getBigEgg();
        timeModNim += time;
        if(timeModNim > 0.5){
            timeModNim -= 0.5;
                throwEgg();        }
    }
    @Override
    public void killed(){
        for(int i=0; i<5; i++){
            int t = Math.abs(random.nextInt()) % 5;
            if(t == 0){
                mainFrame.getItems().add(new MaxTempBooster(x + 20 * i, y + 20 * i + image.getHeight() / 2, 0, 100, mainFrame.getAssets().getMaxTempBooster()));
            }else if(t == 4){
                mainFrame.getItems().add(new TirBooster(x + 20 * i, y + 20 * i + image.getHeight() / 2, 0, 100, mainFrame.getAssets().getTirBooster()));
            }else{
                mainFrame.getItems().add(new TirChanger(t, x+ 20 * i, y + 20 * i + image.getHeight() / 2, 0, 100, mainFrame.getAssets().getTirChanger(t)));
            }
        }
    }
}
