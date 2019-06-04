package users;

public class Record {
    private String username;
    private double timePlayed;
    private int lastLevelPassed, score;
    public Record(String username, double timePlayed, int lastLevelPassed, int score){
        this.username = username;
        this.timePlayed = timePlayed;
        this.lastLevelPassed = lastLevelPassed;
        this.score = score;
    }
    public static boolean compare(Record r1, Record r2){ // returns true if r1 < r2
        if(r1.lastLevelPassed == r2.lastLevelPassed){
            if(r1.score == r2.score){
                return r1.timePlayed < r2.timePlayed;
            }else
                return r1.score < r2.score;
        }else
            return r1.lastLevelPassed < r2.lastLevelPassed;
    }

    public String getUsername() {
        return username;
    }
    public double getTimePlayed() {
        return timePlayed;
    }

    public int getLastLevelPassed() {
        return lastLevelPassed;
    }

    public int getScore() {
        return score;
    }
}
