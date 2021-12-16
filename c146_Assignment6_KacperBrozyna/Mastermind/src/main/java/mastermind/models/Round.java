package mastermind.models;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

public class Round {

    //creating private variables
    private int roundID;
    private int gameID;
    private int exact;
    private int partial;
    private String userGuess;
    private Date date = new Date();
    private Timestamp time = new Timestamp(date.getTime());

    //constructor
    public Round() {
        this.userGuess = "0,0,0,0";
        this.exact =0;
        this.partial =0;
    }

    /*

    getters and setters

     */

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public String getUserGuess() {
        return userGuess;
    }

    public void setUserGuess(String userGuess) {
        this.userGuess = userGuess;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public int getExact() {
        return exact;
    }

    public void setExact(int exact) {
        this.exact = exact;
    }

    public int getPartial() {
        return partial;
    }

    public void setPartial(int partial) {
        this.partial = partial;
    }

    public int getRoundID() {
        return roundID;
    }

    public void setRoundID(int roundID) {
        this.roundID = roundID;
    }

    /*

    Overridden functions

     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Round round = (Round) o;
        return gameID == round.gameID && exact == round.exact && partial == round.partial && Objects.equals(userGuess, round.userGuess) && Objects.equals(date, round.date) && Objects.equals(time, round.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameID, exact, partial, userGuess, date, time);
    }



    @Override
    public String toString() {
        return "Round{" +
                "roundID=" + roundID +
                ", gameID=" + gameID +
                ", exact=" + exact +
                ", partial=" + partial +
                ", userGuess='" + userGuess + '\'' +
                ", date=" + date +
                ", time=" + time +
                "} \n";
    }
}
