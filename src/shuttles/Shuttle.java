package shuttles;

import assets.Assets;
import main.Drawable;
import main.MainFrame;

import java.awt.*;

public class Shuttle extends Drawable {
    private final int speed = 10;
    private int coolDownBetweenFires;
    private double tirPower;
    private double heat;
    private int type;
    private Assets assets;
    private double temperature;
    private boolean overHeated;
    private long lastTimeFired;
    private int fireType, firePower;
    private MainFrame mainFrame;
    private int mainPanelWidth, mainPanelHeight;
    private int maxDegree = 100;
    private boolean isDead = false;
    private long deathTime;
    private int id;

    public void setUndead(boolean undead) {
        this.undead = undead;
    }

    public boolean isUndead() {
        return undead;
    }

    private boolean undead = false;

    @Override
    public String toString() {
        return super.toString() + " " + type;
    }

    private void setTirConsts() {
        if (fireType == 1) {
            coolDownBetweenFires = 200;
            tirPower = 1;
            heat = 8;
        } else if (fireType == 2) {
            coolDownBetweenFires = 200;
            tirPower = 1.1;
            heat = 9;
        } else if (fireType == 3) {
            coolDownBetweenFires = 300;
            tirPower = 1.5;
            heat = 15;
        }
    }

    public Shuttle(int type, Assets assets, MainFrame mainFrame, int fireType, int firePower, int id)
    {
        this.assets = assets;
        this.temperature = 0;
        this.overHeated = false;
        this.lastTimeFired = 0;
        this.fireType = fireType;
        setTirConsts();
        this.firePower = firePower;
        this.mainFrame = mainFrame;
        this.mainPanelWidth = mainFrame.getMainPanel().getWidth();
        this.mainPanelHeight = mainFrame.getMainPanel().getHeight();
        image = assets.getShuttle(type);
        setBeginingCoord();
        this.type = type;
        this.id = id;
    }

    public void setBeginingCoord() {
        x = mainFrame.getMainPanel().getSize().width / 2;
        y = mainFrame.getMainPanel().getSize().height - image.getHeight() / 2;
    }

    @Override
    public void update(double time) {
        if (isDead && System.currentTimeMillis() - deathTime >= 5000) {
            isDead = false;
        }
        if (temperature >= maxDegree)
            overHeated = true;
        temperature -= 25 * time;
        temperature = Math.max(temperature, 0);
        if (temperature == 0)
            overHeated = false;
//        System.out.println(temperature);
    }

    @Override
    public void draw(Graphics2D g) {
        if (!isDead) {
            g.drawImage(image, (int) (x - image.getWidth() / 2), (int) (y - image.getHeight() / 2), null);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Dialog", Font.BOLD, 25));
            g.drawString(String.valueOf(id), (int) (x - 10), (int) (y + image.getHeight() / 2 + 20));
        }
//        g.setColor(Color.BLACK);
//        g.fillOval(x, y, 10, 10);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
        image = assets.getShuttle(type);
    }

    public void setX(double x) {
        this.x = x;
        correct();
    }

    public void setY(double y) {
        this.y = y;
        correct();
    }

    private void correct() {
        this.x = Math.min(this.x, mainPanelWidth - image.getWidth() / 2);
        this.x = Math.max(this.x, image.getWidth() / 2);
        this.y = Math.min(this.y, mainPanelHeight - image.getHeight() / 2);
        this.y = Math.max(this.y, image.getHeight() / 2);
    }

    public void toRight() {
        setX(x + speed);
    }

    public void toLeft() {
        setX(x - speed);
    }

    public void toUp() {
        setY(y - speed);
    }

    public void toDown() {
        setY(y + speed);
    }

    public void fire() {
        //TODO
//        if(System.currentTimeMillis() % 1000 < 100)
//            System.out.println("temp : " + temperature);
        if (isDead || overHeated || System.currentTimeMillis() - lastTimeFired < coolDownBetweenFires) {
            return;
        }
        //Firing
        lastTimeFired = System.currentTimeMillis();
        temperature += heat;

//        if(fireType == 1 || fireType == 2){
////            System.out.println("fire power is " + firePower);
//            for(int i=1; i<=firePower; i++){
//                mainFrame.getItems().add(new Tir(x, y - height/2, -1 * (100 * i / firePower), -400, fireType, assets));
//                mainFrame.getItems().add(new Tir(x, y - height/2, 100 * i / firePower, -400, fireType, assets));
//            }
//            mainFrame.getItems().add(new Tir(x, y - height/2, 0, -400, fireType, assets));
//        }
        double pw = tirPower;
        if (firePower > 4)
            pw += tirPower * (firePower - 4) / 4;
        if (firePower == 1) {
            mainFrame.getItems().add(new Tir(x, y - image.getHeight() / 2, 0, -400, fireType, 0, pw, assets, id));
        } else if (firePower == 2) {
            mainFrame.getItems().add(new Tir(x + 10, y - image.getHeight() / 2, 0, -400, fireType, 0, pw, assets, id));
            mainFrame.getItems().add(new Tir(x - 10, y - image.getHeight() / 2, 0, -400, fireType, 0, pw, assets, id));
        } else if (firePower == 3) {
            mainFrame.getItems().add(new Tir(x, y - image.getHeight() / 2, 0, -400, fireType, 0, pw, assets, id));
            mainFrame.getItems().add(new Tir(x + 10, y - image.getHeight() / 2, 100, -400, fireType, 5, pw, assets, id));
            mainFrame.getItems().add(new Tir(x - 10, y - image.getHeight() / 2, -100, -400, fireType, -5, pw, assets, id));
        } else if (firePower >= 4) {
            mainFrame.getItems().add(new Tir(x + 10, y - image.getHeight() / 2, 0, -400, fireType, 0, pw, assets, id));
            mainFrame.getItems().add(new Tir(x - 10, y - image.getHeight() / 2, 0, -400, fireType, 0, pw, assets, id));
            mainFrame.getItems().add(new Tir(x + 20, y - image.getHeight() / 2, 100, -400, fireType, 10, pw, assets, id));
            mainFrame.getItems().add(new Tir(x - 20, y - image.getHeight() / 2, -100, -400, fireType, -10, pw, assets, id));
        }
    }

    public double getTemperature() {
        return temperature;
    }

    public void shootRocket() {
        if (mainFrame.getUser().getRockets() == 0)
            return;
        mainFrame.getUser().setRockets(mainFrame.getUser().getRockets() - 1);
        //TODO
        System.out.println("Shooting rocket");
        mainFrame.getItems().add(new Rocket(id, assets.getRocket(), (int) (800 - x) / 2, (int) (500 - y) / 2, (int) x, (int) y, mainFrame));
    }

    public int getMaxDegree() {
        return maxDegree;
    }

    public void setMaxDegree(int maxDegree) {
        this.maxDegree = maxDegree;
    }

    public void dead() {
        if(!undead) {
            isDead = true;
            deathTime = System.currentTimeMillis();
            mainFrame.getUser().setFirePower(1);
            firePower = 1;
        }
    }

    public boolean isDead() {
        return isDead;
    }

    public void setFirePower(int firePower) {
        this.firePower = firePower;
    }

    public int getFirePower() {
        return firePower;
    }

    public void changeTir(int type) {
        //TODO
//        System.out.println("Fire type changed to " + type);
        fireType = type;
        setTirConsts();
    }

    public void setFireType(int fireType) {
        this.fireType = fireType;
    }

    public int getFireType() {
        return fireType;
    }

    public int getId() {
        return id;
    }
}

