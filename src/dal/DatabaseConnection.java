package dal;

import core.Block;
import core.Breakout;
import core.Highscore;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {

    private Connection connection;

    public void run() throws Exception {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        String host = "localhost";
        String database = "breakoutdb";
        String username = "root";
        String password = "bb";
        String connectionString = "jdbc:mysql://" + host + "/" + database + "?user=" + username + "&password=" + password;
        connection = DriverManager.getConnection(connectionString);
    }

    public boolean connectionWorks() {
        try {
            run();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public List<Highscore> getSinglePlayerHighscores() throws SQLException {

        List<Highscore> singleHighscores = new ArrayList<Highscore>();

        String selectSQL = "SELECT nickname, score FROM highscores ORDER BY Score DESC LIMIT 15";
        PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Highscore H = new Highscore();
            H.setNickname1(rs.getString("Nickname"));
            H.setScore1(rs.getInt("Score"));
            singleHighscores.add(H);
        }
        return singleHighscores;
    }

    public void addSinglePlayerScore(String nickname, int score) throws SQLException {
        String insertSQL = "INSERT INTO highscores (Nickname, Score) VALUES (?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
        preparedStatement.setString(1, nickname);
        preparedStatement.setInt(2, score);
        preparedStatement.executeUpdate();
        System.out.println(nickname + " with score " + score + " inserted into highscores");
    }

    public List<Highscore> getMultiplayerHighscores() throws SQLException {

        List<Highscore> multiHighscores = new ArrayList<Highscore>();

        String selectSQL = "SELECT *, ScorePlayer1 + ScorePlayer2 AS totalscore FROM highscoresmulti ORDER BY totalscore DESC LIMIT 15";
        PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Highscore H = new Highscore();
            H.setNickname1(rs.getString("NicknamePlayer1"));
            H.setScore1(rs.getInt("ScorePlayer1"));
            H.setNickname2(rs.getString("NicknamePlayer2"));
            H.setScore2(rs.getInt("ScorePlayer2"));
            multiHighscores.add(H);
        }
        return multiHighscores;
    }

    public void addMultiplayerHghscore(String nickname1, int score1, String nickname2, int score2) throws SQLException {
        String insertSQL = "INSERT INTO highscoresmulti (NicknamePlayer1, ScorePlayer1, NicknamePlayer2, ScorePlayer2) VALUES (?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
        preparedStatement.setString(1, nickname1);
        preparedStatement.setInt(2, score1);
        preparedStatement.setString(3, nickname2);
        preparedStatement.setInt(4, score2);
        preparedStatement.executeUpdate();
        System.out.println(nickname1 + " with score " + score1 + " and " + nickname2 + " with score " + score2 + " inserted into highscores");
    }

    public ResultSet getSettings(int difficulty) throws SQLException {
        String selectSQL = "SELECT * FROM difficulty WHERE ID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        preparedStatement.setInt(1, difficulty);
        ResultSet s = preparedStatement.executeQuery();
        return s;
    }

    public List<Block> getLevelBlocks(int levelID, Breakout game) throws SQLException {

        List<Block> blocks = new ArrayList<Block>();

        String selectSQL = "SELECT rows.*, nrOfBlocksPerRow, verticalPadding, Width ,Height, Red, Green, Blue, Score, Density FROM levels JOIN rows ON levels.rowid = rows.rowid JOIN blocks ON blocks.blockid = rows.blockid WHERE levels.levelid = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        preparedStatement.setInt(1, levelID);
        ResultSet rs = preparedStatement.executeQuery();

        int yPosition = 0;
        while (rs.next()) {
            Color color = new Color(rs.getInt("Red"), rs.getInt("Green"), rs.getInt("Blue"));
            int horizontalPadding = (1274 - rs.getInt("Width") * rs.getInt("nrOfBlocksPerRow")) / (rs.getInt("nrOfBlocksPerRow") + 1);
            int xPosition = horizontalPadding;
            yPosition += rs.getInt("verticalPadding");

            for (int i = 1; i <= rs.getInt("nrOfBlocksPerRow"); i++) {
                Block block = new Block(xPosition, yPosition, rs.getInt("Width"), rs.getInt("Height"), color, rs.getInt("Score"), rs.getInt("Density"), game);
                blocks.add(block);
                xPosition += rs.getInt("Width") + horizontalPadding;
            }
            yPosition += rs.getInt("Height") + rs.getInt("verticalPadding");
        }
        return blocks;
    }
}
