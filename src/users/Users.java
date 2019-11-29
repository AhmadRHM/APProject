package users;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.MainFrame;

import java.io.File;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

public class Users {
    private ArrayList<User> users;
    private int lastID = 0;

    public Users() {
        try {
            load();
            int mx = -1;
            for (User user : users)
                mx = Math.max(mx, user.getId());
            lastID = mx + 1;
        } catch (Exception e) {
            System.out.println("problem loading users");
            e.printStackTrace();
            users = new ArrayList<>();
        }
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void addUser(String user, MainFrame mainFrame) {
        users.add(new User(user, lastID, mainFrame));
        lastID++;
        save();
    }

    public void deleteUser(int index) {
        users.remove(index);
        save();
    }

    public void deleteUserWithID(int id) {
        for (User user : users)
            if (user.getId() == id) {
                users.remove(user);
                break;
            }
    }

    private void initFile() {
        try {
            File dataFile = new File("gamedata.data");
            if (!dataFile.exists())
                dataFile.createNewFile();
            PrintWriter printWriter = new PrintWriter(dataFile);
            printWriter.write("");
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final String URL = "jdbc:mysql://localhost:3306/chickeninvaders";
    private final String username = "root";
    private final String password = "";
    private Connection databaseConnection;

    public void save() {
        /*
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
        */

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        try {
            String deleteQuery = "TRUNCATE Users";
            String SpaceShipString = "";
            String insertQuery = "INSERT INTO Users values(?)";

            String selectQuery = "SELECT data from Users";
            Class.forName("com.mysql.cj.jdbc.Driver");
            try {
                databaseConnection = DriverManager.getConnection(URL, username, password);
            } catch (SQLException s) {
                System.out.println("DATABSE NOT ACCESSBLE");
            }
            Statement st = databaseConnection.createStatement();
            PreparedStatement pst = databaseConnection.prepareStatement(insertQuery);

            st.executeUpdate(deleteQuery);

            for (User user : users) {
                SpaceShipString = gson.toJson(user);
                pst.setString(1, SpaceShipString);
                pst.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load() {

        /*
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
        */
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String crateTableQuery = "CREATE TABLE Users(data varchar(10000))";
        String selectQuery = "SELECT data FROM Users";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        users = new ArrayList<User>();
        try {
            databaseConnection = DriverManager.getConnection(URL, username, password);
            Statement statement = databaseConnection.createStatement();
            try {
                statement.executeUpdate(crateTableQuery);
            } catch (Exception e) {
                System.out.println("problem creating database");
//                e.printStackTrace();
            }

            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                String st = resultSet.getString("data");
                User user = gson.fromJson(st, User.class);
                users.add(user);
            }
            statement.close();
            databaseConnection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
