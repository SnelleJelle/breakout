package core;

import java.awt.*;

public class Player {
    //spelling guide: 1 life, 2 lives

    public boolean topPlayer;
    public Color color;
    //private String nickname;
    private String email;
    private int nrOflives;
    private int score;
    private int nrOfPowerups;
    private int nrOfPowerdowns;
    private Ball ball;
    private Paddle paddle;

    public Player(Color color, boolean topPlayer) {
        //this.nickname = nickname;
        this.nrOflives = 3;
        this.score = 0;
        this.nrOfPowerups = 0;
        this.nrOfPowerdowns = 0;
        this.topPlayer = topPlayer;
        this.color = color;
    }

    //nrOflives
    public int getNrOflives() {
        return nrOflives;
    }

    public void setNrOflives(int nrOflives) {
        this.nrOflives = nrOflives;
    }

    public void AddLifes(int nrOflives) {
        this.nrOflives += nrOflives;
    }

    public void RemoveLifes(int nrOflives) {
        this.nrOflives -= nrOflives;
    }

    //score
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    //nrOfPowerUps
    public int getNrOfPowerups() {
        return nrOfPowerups;
    }

    public void SetNrOfPowerups(int nrOfPOwerups) {
        this.nrOfPowerups = nrOfPOwerups;
    }

    //nrOFPowerDowns
    public int getNrOfPowerdowns() {
        return nrOfPowerdowns;
    }

    public void SetNrOfPowerdowns(int nrOfPowerdowns) {
        this.nrOfPowerdowns = nrOfPowerdowns;
    }

    public Ball getBall() {
        return ball;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }

//    public String ToString(){
//        return nickname;
//    }
}

