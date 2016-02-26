package core;

import java.awt.event.KeyEvent;

public class Settings {
    private static String gameMode = "Singleplayer";
    private static String gameDifficulty = "";
    private static int gameDifficultyID = 2;
    private static int player1left = KeyEvent.VK_Q;
    private static int player1right = KeyEvent.VK_D;
    private static int player2left = KeyEvent.VK_NUMPAD1;
    private static int player2right = KeyEvent.VK_NUMPAD3;
    private static int pause = KeyEvent.VK_P;
    private static int launchBall = KeyEvent.VK_SPACE;

    public static int getGameDifficultyID() {
        if (gameDifficulty == "Easy") {
            gameDifficultyID = 1;
        }
        if (gameDifficulty == "Medium") {
            gameDifficultyID = 2;
        }
        if (gameDifficulty == "Hard") {
            gameDifficultyID = 3;
        }

        return gameDifficultyID;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String mode) {
        gameMode = mode;
    }

    public String getGameDifficulty() {
        return gameDifficulty;
    }

    public void setGameDifficulty(String difficulty) {
        gameDifficulty = difficulty;
    }

    public int getPlayer1left() {
        return player1left;
    }

    public void setPlayer1left(int keycode) {
        player1left = keycode;
    }

    public int getPlayer1right() {
        return player1right;
    }

    public void setPlayer1right(int keycode) {
        player1right = keycode;
    }

    public int getPlayer2left() {
        return player2left;
    }

    public void setPlayer2left(int keycode) {
        player2left = keycode;
    }

    public int getPlayer2right() {
        return player2right;
    }

    public void setPlayer2right(int keycode) {
        player2right = keycode;
    }

    public int getPause() {
        return pause;
    }

    public void setPause(int keycode) {
        pause = keycode;
    }

    public int getLaunchBall() {
        return launchBall;
    }

    public void setLaunchBall(int keycode) {
        launchBall = keycode;
    }
}
