package chickenGroups;

import main.MainFrame;

import java.util.Random;

public class SuicideGroup extends ChickenGroup {
    private MainFrame mainFrame;
    private Random random;
    private double[] timesMod10;
    private double timeToSuicicde=0;
    public SuicideGroup(int numberOfChickens, int type, MainFrame mainFrame){
        this.mainFrame = mainFrame;

        random = new Random(System.currentTimeMillis());
        timesMod10 = new double[numberOfChickens];

        for(int i=0; i<numberOfChickens; i++){
            timesMod10[i] = Math.abs(random.nextDouble());
            while(timesMod10[i] > 10)
                timesMod10[i] -= 10;

            int x;
            if(i<numberOfChickens/2)
                x = -200;
            else
                x = 1800;
            Chicken chicken = new Chicken(x, 0, type, Math.abs(random.nextInt()), Math.abs(random.nextInt()) % 7, mainFrame);
            chicken.setNext(Math.abs(random.nextInt()) % 1400 + 100, Math.abs(random.nextInt())%800 + 100);
            chickens.add(chicken);
        }
    }

    @Override
    public void update(double time){
        for(int i=0; i<chickens.size(); i++) {
            timesMod10[i] += time;
            if(timesMod10[i] > 10){
                timesMod10[i] -= 10;
                chickens.get(i).setSpeed(Chicken.defaultSpeed);
                chickens.get(i).setNext(Math.abs(random.nextInt()) % 1400 + 100, Math.abs(random.nextInt())%800 + 100);
            }
        }
        timeToSuicicde += time;
        if(timeToSuicicde > 10) {
            timeToSuicicde -= 10;
            int id = Math.abs(random.nextInt()) % chickens.size();
            timesMod10[id] = 0;
            Chicken suicideChicken = chickens.get(id);
            suicideChicken.setSpeed(2 * Chicken.defaultSpeed);
            suicideChicken.setNext(mainFrame.getMainPanel().getShuttle().getX(), mainFrame.getMainPanel().getShuttle().getY());
        }
        for(int i=0; i<chickens.size(); i++)
            chickens.get(i).update(time);
    }
}
