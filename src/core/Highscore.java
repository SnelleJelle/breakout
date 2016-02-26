package core;

public class Highscore {
    private String nickname1 = "";
    private String nickname2 = "";
    private int score1;
    private int score2;

    public String getNickname1() {
        return nickname1;
    }

    public void setNickname1(String nickname) {
        if (nickname != null) {
            nickname1 = nickname;
        }
    }

    public String getNickname2() {
        return nickname2;
    }

    public void setNickname2(String nickname) {
        if (nickname != null) {
            nickname2 = nickname;
        }
    }

    public int getScore1() {
        return score1;
    }

    public void setScore1(int score) {
        score1 = score;
    }

    public int getScore2() {
        return score2;
    }

    public void setScore2(int score) {
        score2 = score;
    }

    public int getTotalScore() {
        return score1 + score2;
    }
}
