package users;

import main.MainFrame;
import shuttles.Shuttle;

public class User {
    private String username;
    private int id;
    private boolean hasSavedGame;
    private int score;
    private int lastLevel;
    private int lives;
    private int rockets;
    private int money;
    private int shuttleType, fireType, firePower;
    private double timePlayed;
//    private Shuttle shuttle;
    public User(String username, int id, MainFrame mainFrame){
        this.id = id;
        this.username = username;
        hasSavedGame = false;
        shuttleType = 1;
        fireType = 1;
        firePower = 1;
//        shuttle = new Shuttle(2, mainFrame.getAssets(), mainFrame);

    }
    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public boolean isHasSavedGame() {
        return hasSavedGame;
    }

    public void setHasSavedGame(boolean hasSavedGame) {
        this.hasSavedGame = hasSavedGame;
    }

    public void initForNewGame(){
        score = 0;
        lastLevel = -1;
        lives = 5;
        rockets = 3;
        money = 0;
        timePlayed = 0;
        fireType = 1;
        firePower = 1;
    }

    public void startNewGame(){
        initForNewGame();
        hasSavedGame = true;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLastLevel() {
        return lastLevel;
    }

    public void setLastLevel(int lastLevel) {
        this.lastLevel = lastLevel;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getRockets() {
        return rockets;
    }

    public void setRockets(int rockets) {
        this.rockets = rockets;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getShuttleType() {
        return shuttleType;
    }

    public void setShuttleType(int shuttleType) {
        this.shuttleType = shuttleType;
    }

    public int getFireType() {
        return fireType;
    }

    public void setFireType(int fireType) {
        this.fireType = fireType;
    }

    public int getFirePower() {
        return firePower;
    }

    public void setFirePower(int firePower) {
        this.firePower = firePower;
    }

    public double getTimePlayed() {
        return timePlayed;
    }

    public void setTimePlayed(double timePlayed) {
        this.timePlayed = timePlayed;
    }
}
