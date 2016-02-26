package dal;

public class ScoreSaver {

    private DatabaseConnection dbc;

    public ScoreSaver() {
        connectToDatabase();
    }

    private void connectToDatabase() {
        dbc = new DatabaseConnection();

        try {
            dbc.run();
        } catch (Exception ex) {
            System.out.println("Connecting to database failed");
        }
    }

    public void insertIntoHighscoresSingle(String nickname, int score) {
        if (nickname.equals("")) {
            nickname = "Anonymous";
        }
        try {
            dbc.addSinglePlayerScore(nickname, score);
        } catch (Exception ex) {
            System.out.println("Insert into database failed");
        }
    }

    public void insertIntoHighscoresMulti(String nickname1, int score1, String nickname2, int score2) {
        if (nickname1.equals("")) {
            nickname1 = "Anonymous";
        }
        if (nickname2.equals("")) {
            nickname2 = "Anonymous2";
        }
        try {
            dbc.addMultiplayerHghscore(nickname1, score1, nickname2, score2);
        } catch (Exception ex) {
            System.out.println("Insert into database failed");
        }
    }
}

