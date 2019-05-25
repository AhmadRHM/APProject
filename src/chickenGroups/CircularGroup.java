package chickenGroups;

import main.MainFrame;

import java.util.ArrayList;
import java.util.Random;

public class CircularGroup extends ChickenGroup {
    private MainFrame mainFrame;
    private double nextX, nextY;
    private double timeMod5, thetha0 = 0, radius0 = 100, angleSpeed = 20;
    private final int radiusForEachCircle = 100;
    private int numberOfCircles;
    private Random random;
    private ArrayList<Chicken>[] chickensInCircle;

    public CircularGroup(int numberOfChickens, int type, MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        if (numberOfChickens <= 8)
            numberOfCircles = 1;
        else if (numberOfChickens <= 20)
            numberOfCircles = 2;
        else if (numberOfChickens <= 36)
            numberOfCircles = 3;
        else
            numberOfCircles = 4;

        chickensInCircle = new ArrayList[numberOfCircles];
        for (int i = 0; i < numberOfCircles; i++)
            chickensInCircle[i] = new ArrayList<>();

        random = new Random(System.currentTimeMillis());
        for (int i = 0; i < numberOfChickens; i++) {
            Chicken chicken;
            if (i < numberOfChickens / 2)
                chicken = new Chicken(-1000, 0, type, i * Math.abs(random.nextInt()), (Math.abs(random.nextInt())) % 7, mainFrame);
            else
                chicken = new Chicken(2000, 0, type, i * Math.abs(random.nextInt()), Math.abs(random.nextInt()) % 7, mainFrame);
            chickens.add(chicken);
            if (i < 8)
                chickensInCircle[0].add(chicken);
            else if (i < 20)
                chickensInCircle[1].add(chicken);
            else if (i < 36)
                chickensInCircle[2].add(chicken);
            else
                chickensInCircle[3].add(chicken);
        }

        this.x = -1000;
        this.y = -1000;
        double mxRadius = radius0 + radiusForEachCircle * numberOfCircles;
        goTo(Math.abs(random.nextInt()) % (mainFrame.getMainPanel().getWidth() - (int) (2 * mxRadius)) + mxRadius,
                Math.abs(random.nextInt()) % (mainFrame.getMainPanel().getHeight() - (int) (2 * mxRadius)) + mxRadius);
    }

    public void goTo(double nextX, double nextY) {
        this.nextX = nextX;
        this.nextY = nextY;
        this.vx = (nextX - x) / 5;
        this.vy = (nextY - y) / 5;
    }

    @Override
    public void update(double time) {
        super.update(time);
        thetha0 += angleSpeed * time;
        if (thetha0 > 360)
            thetha0 -= 360;

        timeMod5 += time;
        if (timeMod5 > 5) {
            timeMod5 -= 5;
            double mxRadius = radius0 + radiusForEachCircle * numberOfCircles;
            goTo(Math.abs(random.nextInt()) % (mainFrame.getMainPanel().getWidth() - (int) (2 * mxRadius)) + mxRadius,
                    Math.abs(random.nextInt()) % (mainFrame.getMainPanel().getHeight() - (int) (2 * mxRadius)) + mxRadius);
        }

        for (int circle = 0; circle < numberOfCircles; circle++) {
            for (int i = 0; i < chickensInCircle[circle].size(); i++) {
                Chicken chicken = chickensInCircle[circle].get(i);
                double theta = thetha0 + (360 / chickensInCircle[circle].size()) * i;
                double radius = radius0 + circle * radiusForEachCircle + radiusForEachCircle / 2;
                chicken.setNext(x + radius * Math.cos(Math.toRadians(theta)), y - radius * Math.sin(Math.toRadians(theta)));
                chicken.update(time);
            }
        }
    }

    @Override
    public void removeChicken(Chicken chicken) {
        synchronized (chickens) {
            chickens.remove(chicken);
            for (int circle = 0; circle < numberOfCircles; circle++)
                if (chickensInCircle[circle].contains(chicken))
                    chickensInCircle[circle].remove(chicken);
        }
    }
}
