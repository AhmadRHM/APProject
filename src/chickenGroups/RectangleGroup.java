package chickenGroups;

import main.Drawable;
import main.MainFrame;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class RectangleGroup extends ChickenGroup {
    private MainFrame mainFrame;
    public RectangleGroup(int numberOfChickens, int type, MainFrame mainFrame){
        this.mainFrame = mainFrame;
        vx = 60;
        vy = 0;
        Random random = new Random(System.currentTimeMillis());
        synchronized (chickens) {
            for (int i = 0; i < numberOfChickens; i++) {
                Chicken c = new Chicken(0, 100, type, i, Math.abs(random.nextInt()) % 7, mainFrame);
//            System.out.println(Math.abs(random.nextInt())%7);
                chickens.add(c);
//            mainFrame.getItems().add(c);
            }
        }
    }
    private int getNumberOfCols(){
        synchronized (chickens) {
            int n = chickens.size();
            if (n > 40)
                return 9;
            if (n >= 30)
                return 8;
            return 7;
        }
    }
    @Override
    public void update(double time) {
        super.update(time);
        synchronized (chickens) {
            int cols = getNumberOfCols();
            if (x < 0 || x + cols * chickens.get(0).getSize().width > 1600)
                vx *= -1;
            for (int i = 0; i < chickens.size(); i++) {
                Chicken c = chickens.get(i);
                c.setNext(x + (i % cols + 0.5) * c.getSize().width, y + (i / cols + 0.5) * c.getSize().height);
                c.update(time);
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {
        synchronized (chickens) {
            for (Chicken chicken : chickens)
                chicken.draw(g);
        }
    }
}
