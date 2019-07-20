package Network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.MainFrame;
import shuttles.Shuttle;
import users.User;

import java.io.*;
import java.util.Scanner;

public class ClientHandler extends Thread {
    private InputStream inputStream;
    private OutputStream outputStream;
    private int id;
    private User user;
    private boolean isSpectator;
    private MainFrame mainFrame;

    public ClientHandler(InputStream inputStream, OutputStream outputStream, int id, MainFrame mainFrame){
        this.inputStream = new BufferedInputStream(inputStream);
        this.outputStream = new BufferedOutputStream(outputStream);
        this.id = id;
        this.mainFrame = mainFrame;
    }
    public void run(){
        Scanner scanner = new Scanner(inputStream);
        PrintStream printer = new PrintStream(outputStream);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        user = gson.fromJson(scanner.nextLine(), User.class);
        System.out.println("client sent user data");
        if(scanner.next().equals("true"))
            isSpectator = true;
        else
            isSpectator = false;
        if(isSpectator)
            mainFrame.getSpectatingUsers().add(user);
        else{
            //TODO if in mid of game
            mainFrame.getPlayingUsers().add(user);
            Shuttle shuttle = new Shuttle(user.getShuttleType(), mainFrame.getAssets(), mainFrame, user.getFireType(), user.getFirePower(), id);
            mainFrame.getShuttles().add(shuttle);
            mainFrame.getItems().add(shuttle);
        }
        scanner.nextLine();
        printer.println(id);
        printer.flush();
        mainFrame.getServerDetalePanel().initLabels();
        System.out.println("number of users " + mainFrame.getPlayingUsers().size());
        while(scanner.hasNext()){
            String str = scanner.next();
            if(!str.equals("move"))
                scanner.nextLine();
            if(str.equals("get")){
                printer.println(mainFrame.getItems().getItemsInString(id, mainFrame));
                printer.flush();
            }else if(str.equals("fire")){
                mainFrame.fireShuttle(id);
            }else if(str.equals("move")){
                String str1 = scanner.next(), str2 = scanner.next();scanner.nextLine();
                System.out.println(str1);
                System.out.println(str2);
//                int x = scanner.nextInt();//scanner.nextLine();
//                int y = scanner.nextInt();scanner.nextLine();
                int x = Integer.valueOf(str1), y = Integer.valueOf(str2);
                mainFrame.moveShuttleTo(id, x, y);
                System.out.println("moving shuttle " + id + " to " + x + " " + y);
            }else if(str.equals("rocket")){
                mainFrame.shootRocket(id);
            }else if(str.equals("gamestarted")){
                if(mainFrame.isServerGameStarted())
                    printer.println("yes");
                else{
                    printer.println("no");
                    printer.println(mainFrame.getPlayingUsers().size());
//                    System.out.println(mainFrame.getPlayingUsers().size());
                    for(User user : mainFrame.getPlayingUsers()) {
                        printer.println(gson.toJson(user));
//                        System.out.println(gson.toJson(user));
                    }
                    printer.println(mainFrame.getSpectatingUsers().size());
//                    System.out.println(mainFrame.getSpectatingUsers().size());
                    for(User user : mainFrame.getSpectatingUsers()) {
                        printer.println(gson.toJson(user));
//                        System.out.println(gson.toJson(user));
                    }
                }
                printer.flush();
            }
        }
    }
}
