package main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import users.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
        mainFrame.startAnimation();
//        Random random = new Random(10);
//        while (true){
//            mainFrame.setBackgroundSpeed((double)(Math.abs( random.nextInt())%10 + 2)/3);
//            try {
//                Thread.sleep(3000);
//            }catch (Exception e){
//
//            }
//        }
        EsqFrame esqFrame = new EsqFrame(mainFrame);
        mainFrame.setEsqFrame(esqFrame);
    }
}
