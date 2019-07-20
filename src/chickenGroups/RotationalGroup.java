package chickenGroups;

import main.MainFrame;

import java.util.ArrayList;
import java.util.Random;

public class RotationalGroup extends ChickenGroup {
    private MainFrame mainFrame;
    private double thetha0 = 0, radius0 = 200, angleSpeed = 40;
    private final double radiusForEachCircle = 100;
    private int numberOfCircles;
    private Random random;
    private ArrayList<Chicken>[] chickensInCircle;

    public RotationalGroup(int numberOfChickens, int type, MainFrame mainFrame){
        this.mainFrame = mainFrame;

        if(numberOfChickens <= 8)
            numberOfCircles = 1;
        else if(numberOfChickens <= 20)
            numberOfCircles = 2;
        else if(numberOfChickens <= 36)
            numberOfCircles = 3;
        else
            numberOfCircles = 4;

        chickensInCircle = new ArrayList[numberOfCircles];
        for(int i=0; i<numberOfCircles; i++)
            chickensInCircle[i] = new ArrayList<>();

        this.x = mainFrame.getMainPanel().getWidth()/2;
        this.y = mainFrame.getMainPanel().getHeight()/2;

        random = new Random(System.currentTimeMillis());
        for(int i=0; i<numberOfChickens; i++){
            double bigR = 1000, theta;
            if(i<8)
                 theta = thetha0 + (360/Math.min(8, numberOfChickens))*i;
            else if(i<20)
                theta = thetha0 + (360 / Math.min(12, numberOfChickens-8))*(i-8);
            else if(i<36)
                theta = thetha0 + (360 / Math.min(16, numberOfChickens-20))*(i-20);
            else theta = thetha0 + (360 / (numberOfChickens-36))*(i-36);
            Chicken chicken;
//            if(i<numberOfChickens/2)
//                chicken = new Chicken(-1000, 0, type, i*Math.abs(random.nextInt()), (Math.abs(random.nextInt()))%7, mainFrame);
//            else
            chicken = new Chicken(x + bigR*Math.cos(Math.toRadians(theta)) , y - bigR*Math.sin(Math.toRadians(theta)), type, i*Math.abs(random.nextInt()), Math.abs(random.nextInt())%7,mainFrame);
            chickens.add(chicken);
            if(i<8)
                chickensInCircle[0].add(chicken);
            else if(i<20)
                chickensInCircle[1].add(chicken);
            else if(i<36)
                chickensInCircle[2].add(chicken);
            else
                chickensInCircle[3].add(chicken);
        }
    }
    @Override
    public void update(double time){
        thetha0 += angleSpeed * time;
        if(thetha0 > 360)
            thetha0 -= 360;
        for(int circle=0; circle<numberOfCircles; circle++){
            double radius = radius0 + radiusForEachCircle * circle + radiusForEachCircle/2;
            for(int i=0; i<chickensInCircle[circle].size(); i++) {
                double theta = thetha0 + (360 / chickensInCircle[circle].size()) * i;
                Chicken chicken = chickensInCircle[circle].get(i);
                chicken.setNext(x + radius*Math.cos(Math.toRadians(theta)) , y - radius*Math.sin(Math.toRadians(theta)));
                chicken.update(time);
            }
        }
    }

    @Override
    public void removeChicken(Chicken chicken){
        synchronized (chickens) {
            chickens.remove(chicken);
            for (int circle = 0; circle < numberOfCircles; circle++)
                if (chickensInCircle[circle].contains(chicken))
                    chickensInCircle[circle].remove(chicken);
        }
    }
}
