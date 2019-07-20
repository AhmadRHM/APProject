package main;

import chickenGroups.*;
import shuttles.Rocket;
import shuttles.Shuttle;
import shuttles.Tir;
import users.User;

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

    public String getItemsInString(int id, MainFrame mainFrame) {
        String ans = "", tmp = "";
        int n=0;

        for(Drawable drawable : items){
            if(drawable instanceof Tir){
                n++;
                tmp += drawable.toString() + "\n";
            }
        }
        ans += n + "\n";
        ans += tmp;

        n=0;
        tmp = "";
        for(Drawable drawable : items){
            if(drawable instanceof ChickenGroup){
                for(Chicken chicken:(ArrayList<Chicken>)((ChickenGroup)drawable).getChickens().clone()) {
                    n++;
                    tmp += chicken.toString() + "\n";
                }
            }
        }
        ans += n + "\n";
        ans += tmp;

        n=0;
        tmp = "";
        for(Drawable drawable : items){
            if(drawable instanceof BigEgg){
                n++;
                tmp += drawable.toString() + "\n";
            }
        }
        ans += n + "\n";
        ans += tmp;

        n=0;
        tmp = "";
        for(Drawable drawable : items){
            if(drawable instanceof Shuttle){
                n++;
                tmp += drawable.toString() + "\n";
            }
        }
        ans += n + "\n";
        ans += tmp;

        n=0;
        tmp = "";
        for(Drawable drawable : items){
            if(drawable instanceof Egg){
                n++;
                tmp += drawable.toString() + "\n";
            }
        }
        ans += n + "\n";
        ans += tmp;

        n=0;
        tmp = "";
        for(Drawable drawable : items){
            if(drawable instanceof MaxTempBooster){
                n++;
                tmp += drawable.toString() + "\n";
            }
        }
        ans += n + "\n";
        ans += tmp;

        n=0;
        tmp = "";
        for(Drawable drawable : items){
            if(drawable instanceof TirBooster){
                n++;
                tmp += drawable.toString() + "\n";
            }
        }
        ans += n + "\n";
        ans += tmp;

        n=0;
        tmp = "";
        for(Drawable drawable : items){
            if(drawable instanceof TirChanger){
                n++;
                tmp += drawable.toString() + "\n";
            }
        }
        ans += n + "\n";
        ans += tmp;

        n=0;
        tmp = "";
        for(Drawable drawable : items){
            if(drawable instanceof Coin){
                n++;
                tmp += drawable.toString() + "\n";
            }
        }
        ans += n + "\n";
        ans += tmp;

        n=0;
        tmp = "";
        for(Drawable drawable : items){
            if(drawable instanceof InGameText){
                n++;
                tmp += drawable.toString() + "\n";
            }
        }
        ans += n + "\n";
        ans += tmp;

        n=0;
        tmp = "";
        for(Drawable drawable : items){
            if(drawable instanceof Rocket){
                n++;
                tmp += drawable.toString() + "\n";
            }
        }
        ans += n + "\n";
        ans += tmp;

        User user = mainFrame.getUser(id);
        ans += user.getLives() + " " + user.getRockets() + " " + user.getMoney() + "\n";
        Shuttle shuttle = mainFrame.getShuttle(id);
        ans += ((int)(shuttle.getTemperature() * 40 / shuttle.getMaxDegree())) + " " + user.getScore() + "\n";
//        System.out.println("started sent data");
//        System.out.println(ans);
//        System.out.println("ended sent data");
        return ans;
    }
}
