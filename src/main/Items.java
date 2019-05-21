package main;

import javax.imageio.ImageIO;
import java.util.ArrayList;

public class Items {
    private ArrayList<Drawable> items;
//    private ArrayList<Drawable> removeList, addList;
//    private int using;
//    private boolean changing;
    Items(){
        items = new ArrayList<>();
//        removeList = new ArrayList<>();
//        addList = new ArrayList<>();
//        using = 0;
//        changing = false;
    }

//    public int getUsing() {
//        return using;
//    }
//
//    public void setUsing(int using) {
//        this.using = using;
//    }

    public void add(Drawable item){
//        if(using > 0)
//            addList.add(item);
//        else
//            items.add(item);
        ArrayList<Drawable> copy = (ArrayList<Drawable>) items.clone();
        copy.add(item);
        items = copy;
    }

    public void remove(Drawable item){
//        if(using > 0)
//            removeList.add(item);
//        else
//            items.remove(item);
        ArrayList<Drawable> copy = (ArrayList<Drawable>) items.clone();
        copy.remove(item);
        items = copy;
    }

//    public void addUsing(){
//        using++;
//    }
//
//    public void deleteUsing(){
//        using --;
//        if(using == 0){
//            changing = true;
//            items.removeAll(removeList);
//            items.addAll(addList);
//            changing = false;
//            removeList.clear();
//            addList.clear();
//
//        }
//    }
    public ArrayList<Drawable> getItems(){ return items; }

//    public boolean isChanging() {
//        return changing;
//    }
    public void clear(){
        items.clear();
    }
}
