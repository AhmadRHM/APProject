package shuttles;

import assets.Assets;
import main.Drawable;
import main.Main;
import main.MainFrame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Shuttle implements Drawable {
    private final int speed = 10;
    private final int coolDownBetweenFires = 200;
    private int x, y;
    private int type;
    private int width, height;
    private Assets assets;
    private double temperature;
    private boolean overHeated;
    private long lastTimeFired;
    private int fireType, firePower;
    private MainFrame mainFrame;
    private int mainPanelWidth, mainPanelHeight;
    private BufferedImage shuttleImage;
    private int maxDegree = 100;
    private boolean isDead = false;
    private long deathTime;

    public Shuttle(int type, Assets assets, MainFrame mainFrame, int fireType, int firePower){
        this.assets = assets;
        this.x = mainFrame.getMainPanel().getSize().width / 2;
        this.y = mainFrame.getMainPanel().getSize().height - height;
        this.temperature = 0;
        this.overHeated = false;
        this.lastTimeFired = 0;
        this.fireType = fireType;
        this.firePower = firePower;
        this.mainFrame = mainFrame;
        this.mainPanelWidth = mainFrame.getMainPanel().getWidth();
        this.mainPanelHeight = mainFrame.getMainPanel().getHeight();
        shuttleImage = assets.getShuttle(type);
        width = shuttleImage.getWidth();
        height = shuttleImage.getHeight();
        x = mainFrame.getMainPanel().getSize().width / 2;
        y = mainFrame.getMainPanel().getSize().height - height/2;
        this.type = type;
    }
    @Override
    public void update(double time) {
        if(isDead && System.currentTimeMillis() - deathTime >= 5000){
            isDead = false;
        }
        if(temperature >= maxDegree)
            overHeated = true;
        temperature -= 25 * time;
        temperature = Math.max(temperature, 0);
        if(temperature == 0)
            overHeated = false;
//        System.out.println(temperature);
    }

    @Override
    public void draw(Graphics2D g) {
        if(!isDead)
            g.drawImage(shuttleImage, x - width/2, y - height/2 , null);
//        g.setColor(Color.BLACK);
//        g.fillOval(x, y, 10, 10);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
        shuttleImage = assets.getShuttle(type);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
        correct();
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        correct();
    }

    private void correct(){
        this.x = Math.min(this.x, mainPanelWidth - width/2);
        this.x = Math.max(this.x, width/2);
        this.y = Math.min(this.y, mainPanelHeight - height/2);
        this.y = Math.max(this.y, height/2);
    }
    public void toRight(){
        setX(x+speed);
    }
    public void toLeft(){
        setX(x-speed);
    }
    public void toUp(){
        setY(y-speed);
    }
    public void toDown(){
        setY(y+speed);
    }
    public void fire(){
        //TODO
//        if(System.currentTimeMillis() % 1000 < 100)
//            System.out.println("temp : " + temperature);
        if(isDead || overHeated || System.currentTimeMillis() - lastTimeFired < coolDownBetweenFires) {
            return;
        }
        //Firing
        lastTimeFired = System.currentTimeMillis();
        temperature += 8;

//        if(fireType == 1 || fireType == 2){
////            System.out.println("fire power is " + firePower);
//            for(int i=1; i<=firePower; i++){
//                mainFrame.getItems().add(new Tir(x, y - height/2, -1 * (100 * i / firePower), -400, fireType, assets));
//                mainFrame.getItems().add(new Tir(x, y - height/2, 100 * i / firePower, -400, fireType, assets));
//            }
//            mainFrame.getItems().add(new Tir(x, y - height/2, 0, -400, fireType, assets));
//        }
        if(firePower == 1){
            mainFrame.getItems().add(new Tir(x, y-height/2, 0, -400, fireType, 0, assets));
        }else if(firePower == 2){
            mainFrame.getItems().add(new Tir(x+10, y-height/2, 0, -400, fireType, 0, assets));
            mainFrame.getItems().add(new Tir(x-10, y-height/2, 0, -400, fireType, 0, assets));
        }else if(firePower == 3){
            mainFrame.getItems().add(new Tir(x, y-height/2, 0, -400, fireType, 0, assets));
            mainFrame.getItems().add(new Tir(x+10, y-height/2, 100, -400, fireType, 5, assets));
            mainFrame.getItems().add(new Tir(x-10, y-height/2, -100, -400, fireType, -5, assets));
        }else if(firePower == 4){
            mainFrame.getItems().add(new Tir(x+10, y-height/2, 0, -400, fireType, 0, assets));
            mainFrame.getItems().add(new Tir(x-10, y-height/2, 0, -400, fireType, 0, assets));
            mainFrame.getItems().add(new Tir(x+20, y-height/2, 100, -400, fireType, 10, assets));
            mainFrame.getItems().add(new Tir(x-20, y-height/2, -100, -400, fireType, -10, assets));
        }
    }
    public double getTemperature(){
        return temperature;
    }
    public void shootRocket(){
        if(mainFrame.getUser().getRockets() == 0)
            return;
        mainFrame.getUser().setRockets(mainFrame.getUser().getRockets()-1);
        //TODO
        System.out.println("Shooting rocket");
        mainFrame.getItems().add(new Rocket(assets.getRocket(), (800 - x)/2, (500 - y)/2, x, y, mainFrame));
    }

    public int getMaxDegree() {
        return maxDegree;
    }

    public void setMaxDegree(int maxDegree) {
        this.maxDegree = maxDegree;
    }

    public void dead(){
        isDead = true;
        deathTime = System.currentTimeMillis();
    }

    public Dimension getSize(){return new Dimension(shuttleImage.getWidth(), shuttleImage.getHeight());}

    public boolean isDead(){return  isDead;}
}

