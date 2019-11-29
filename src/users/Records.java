package users;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Records {
    private ArrayList<Record> records;
    public Records(){
        records = new ArrayList<>();
        load();
    }
    public void addRecord(Record record){
        records.add(record);
        save();
    }
    public ArrayList<Record> getRecords(){
        sort();
        return records;
    }

    private void sort() {
//        int n = records.size();
//        for(int i=0; i<n; i++)
//            for(int j=1; j<n; j++)
//                if(Record.compare(records.get(j-1), records.get(j))){
//                    Record r1 = records.get(j-1), r2 = records.get(j);
//                    records.
//                }
        records.sort(new Cmp());
    }

    private void initFile() {
        try {
            File dataFile = new File("records.data");
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
            File dataFile = new File("records.data");
            PrintStream printStream = new PrintStream(dataFile);
            printStream.println(gson.toJson(records));
            printStream.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
    private final String URL="jdbc:mysql://localhost:3306/chickeninvaders";
    private final String username = "root";
    private final String password = "";
    private Connection databaseConnection;

    public void load(){
        /*
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try {
            File dataFile = new File("records.data");
            Scanner scanner = new Scanner(dataFile);
            String data = "";
            while (scanner.hasNext())
                data += scanner.nextLine();
            records = gson.fromJson(data,new TypeToken<List<Record>>(){}.getType());
            scanner.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
         */
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String createTableQuery = "CREATE TABLE Records(data varchar(10000))";
        String selectQuery = "SELECT data FROM Records";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        records = new ArrayList<Record>();
        try{
            databaseConnection = DriverManager.getConnection(URL, username, password);
            Statement statement = databaseConnection.createStatement();
            try {
                statement.executeUpdate(createTableQuery);
            } catch (Exception e){
                System.out.println("problem creating database");
            }

            ResultSet resultSet = statement.executeQuery(selectQuery);
            while(resultSet.next()){
                String st = resultSet.getString("data");
                Record record = gson.fromJson(st, Record.class);
                records.add(record);
            }
            statement.close();
            databaseConnection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
class Cmp implements Comparator<Record>{

    @Override
    public int compare(Record o1, Record o2) {
        if(o1.getTimePlayed() == o2.getTimePlayed() &&
        o1.getScore() == o2.getScore() &&
        o1.getLastLevelPassed() == o2.getLastLevelPassed() )
            return 0;
        if(Record.compare(o1, o2))
            return 1;
        return -1;
    }
}