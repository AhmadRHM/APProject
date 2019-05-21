package users;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import main.MainFrame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Users {
    private ArrayList<User> users;
    private int lastID = 0;
    public Users(){
        try {
            load();
            int mx = -1;
            for (User user : users)
                mx = Math.max(mx, user.getId());
            lastID = mx + 1;
        }catch (Exception e){
            System.out.println("problem loading users");
            e.printStackTrace();
            users = new ArrayList<>();
        }
    }
    public ArrayList<User> getUsers() {
        return users;
    }
    public void addUser(String user, MainFrame mainFrame){
        users.add(new User(user, lastID, mainFrame));
        lastID++;
        save();
    }
    public void deleteUser(int index) {
        users.remove(index);
        save();
    }
    public void deleteUserWithID(int id){
        for(User user : users)
            if(user.getId() == id){
                users.remove(user);
                break;
            }
    }
    private void initFile() {
        try {
            File dataFile = new File("gamedata.data");
            if(!dataFile.exists())
                dataFile.createNewFile();
            PrintWriter printWriter = new PrintWriter(dataFile);
            printWriter.write("");
            printWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void save(){
        initFile();
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        try {
            File dataFile = new File("gamedata.data");
            PrintStream printStream = new PrintStream(dataFile);
            printStream.println(gson.toJson(users));
            printStream.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
    public void load(){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try {
            File dataFile = new File("gamedata.data");
            Scanner scanner = new Scanner(dataFile);
            String data = "";
            while (scanner.hasNext())
                data += scanner.nextLine();
            users = gson.fromJson(data,new TypeToken<List<User>>(){}.getType());
            scanner.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
}
