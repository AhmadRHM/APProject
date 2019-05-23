package chickenGroups;

import main.Drawable;

import java.awt.*;
import java.util.ArrayList;

public abstract class ChickenGroup implements Drawable {
    protected ArrayList<Chicken> chickens = new ArrayList<>();

//    protected abstract Point getPositionOfChicken(int id);
    public ArrayList<Chicken> getChickens(){return  chickens;}

}
