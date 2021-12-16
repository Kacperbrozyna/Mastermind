package mastermind.models;

import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

public class Game {

    //creating variables
    private int id;
    private Date date = new Date();
    private Timestamp time = new Timestamp(date.getTime());
    private String generatedAnswer;
    private boolean gameFinished;

    //constructor
    public Game() {
        this.generatedAnswer = "0,0,0,0";
    }

    /*

    Getters and Setters

     */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getGeneratedAnswer() {
        return generatedAnswer;
    }

    public void setGeneratedAnswer(String generatedAnswer) {
        this.generatedAnswer = generatedAnswer;
    }

    public boolean isGameFinished() {
        return gameFinished;
    }

    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }
}