package chickenGroups;

import main.Drawable;

import java.awt.*;
import java.util.ArrayList;

public abstract class ChickenGroup extends Drawable {
    protected ArrayList<Chicken> chickens = new ArrayList<>();

//    protected abstract Point getPositionOfChicken(int id);
    public ArrayList<Chicken> getChickens(){return  chickens;}

    public void removeChicken(Chicken chicken){};

    public void draw(Graphics2D g){
        synchronized (chickens) {
            for (Chicken chicken : chickens)
                chicken.draw(g);
        }
    }
}
