package core;

import dal.DatabaseConnection;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Breakout {

    public List<Player> players = new ArrayList();
    public List<Sprite> sprites = new ArrayList<Sprite>();
    public boolean twoPlayerMode;
    public Dimension dimension; //[width=1274,height=661]

    private List<BreakoutObserver> servers = new ArrayList<BreakoutObserver>();
    private int livesLeft = 0;
    private String lastUsedPowerName = "NONE";
    private int currentLevel = 0;
    private int difficultyNr;
    private double difficultyProbabilityPowerUp;
    private Settings S = new Settings();
    private DatabaseConnection dbc;
    private List<Block> targets = new ArrayList();

    public Breakout(Dimension dimension, boolean twoPlayerMode) {

        connectToDatabase();
        this.twoPlayerMode = twoPlayerMode;
        this.dimension = dimension;

        //region create player 1
        Player player1 = new Player(new Color(255, 0, 0, 100), false);
        Paddle paddle = new Paddle(this, player1, 635);
        player1.setPaddle(paddle);
        Ball ball = new Ball(this, player1);
        player1.setBall(ball);
        players.add(player1);

        sprites.add(ball);
        sprites.add(paddle);
        //endregion

        //region create player 2
        if (twoPlayerMode) {
            Player player2 = new Player(new Color(0, 0, 255, 100), true);
            Paddle paddle2 = new Paddle(this, player2, 15);
            player2.setPaddle(paddle2);
            Ball ball2 = new Ball(this, player2);
            player2.setBall(ball2);
            players.add(player2);

            sprites.add(ball2);
            sprites.add(paddle2);
        }
        //endregion
        getSettingsFromDB();
        nextLevel();
    }

    private void getSettingsFromDB() {
        try {
            difficultyNr = Settings.getGameDifficultyID();
            ResultSet settings = dbc.getSettings(difficultyNr);

            while (settings.next()) {
                difficultyProbabilityPowerUp = (double) settings.getFloat("PowerUpChance");
                setLives(settings.getInt("StartLives"));
                for (Player p : players) {
                    p.getPaddle().setWidth(settings.getInt("PaddleLength"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void connectToDatabase() {
        dbc = new DatabaseConnection();

        try {
            dbc.run();
        } catch (Exception ex) {
            System.out.println("Connect to database failed");
        }
    }

    private void addTargets(int level) {
        LevelBuilder lb = new LevelBuilder(this, difficultyProbabilityPowerUp);

        targets = lb.getlevel(level);
    }

    public List<Sprite> getSprites() {
        List<Sprite> allSprites = new ArrayList<Sprite>();
        for (Sprite s : sprites) {
            allSprites.add(s);
        }
        for (Sprite s : targets) {
            allSprites.add(s);
        }
        return allSprites;
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == S.getLaunchBall()) {
            for (Player p : players) {
                p.getBall().stickToPaddle(false);
            }
        }

        for (Player p : players) {
            p.getPaddle().keyPressed(e);
        }
    }

    public void keyReleased(KeyEvent e) {
        for (Player p : players) {
            p.getPaddle().keyReleased(e);
        }
    }

    public void nextLevel() {
        currentLevel = getCurrentLevel() + 1;

        addTargets(getCurrentLevel());
        for (Player p : players) {
            p.getPaddle().resetPosition();
            p.getBall().resetPosition();
            p.getBall().stickToPaddle(true);
        }
        notifyObservers();
    }

    public void move() {
        for (Player p : players) {
            p.getBall().move();
            p.getPaddle().move();
        }
    }

    public void addObserver(BreakoutObserver observer) {
        servers.add(observer);
    }

    public void notifyObservers() {
        for (BreakoutObserver b : servers) {
            b.update();
        }
    }

    public void ballMiss() {
        if (livesLeft > 0) {
            livesLeft--;
            for (Player p : players) {
                p.getPaddle().resetPosition();
                p.getBall().resetPosition();
                p.getBall().stickToPaddle(true);
            }
        } else {
            //region play sound
            //Sound.playSound(getClass().getClassLoader().getResource("gameover.wav").getFile());
            //endregion
        }
        notifyObservers();
    }

    public List<Block> getTargets() {
        return targets;
    }

    public int[] getScores() {
        int[] scores = new int[3];
        int i = 1;
        for (Player p : players) {
            scores[i] = p.getScore();
            i++;
        }
        return scores;
    }

    public int getTotalScore() {
        int score = 0;
        score += getScores()[1] + getScores()[2];
        return score;
    }

    //region small functions
    public void addLife() {
        this.livesLeft++;
    }

    public void removeLife() {
        this.livesLeft--;
    }

    public void setLives(int lives) {
        this.livesLeft = lives;
    }

    public int getLivesLeft() {
        return livesLeft;
    }

    public String getLastUsedPowerName() {
        return lastUsedPowerName;
    }

    public void setLastUsedPowerName(String power) {
        lastUsedPowerName = power;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    //endregion
}
