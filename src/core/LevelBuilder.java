package core;

import dal.DatabaseConnection;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class LevelBuilder {

    Breakout game;
    DatabaseConnection dbc = new DatabaseConnection();
    Random rand = new Random();
    double difficulty;

    public LevelBuilder(Breakout game, double difficulty) {
        this.game = game;
        this.difficulty = difficulty;
        connectToDatabase();
    }

    private void connectToDatabase() {
        dbc = new DatabaseConnection();

        try {
            dbc.run();
        } catch (Exception ex) {
            System.out.println("Connect to database failed");
        }
    }

    public List<Block> getlevel(int levelNr) {

        int lastBlockYPosition = 0;

        List<Block> blocks = null;
        try {
            blocks = dbc.getLevelBlocks(levelNr, game);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Block b : blocks) {
            double randomNr = rand.nextDouble();
            if (randomNr <= 0.10) {
                if (rand.nextDouble() > difficulty) {
                    b.setPower(getPowerUp());
                } else {
                    b.setPower(getPowerDown());
                }
            }
            lastBlockYPosition = b.getBounds().y + b.getBounds().height;
        }

        if (game.twoPlayerMode) {
            int down = ((int) game.dimension.getHeight() - lastBlockYPosition) / 2;

            for (Block b : blocks) {
                b.moveDown(down);
            }
        }

        return blocks;
    }

    private Power getPowerUp() {

        return new Power(getRandomPowerNr(1, 3), game);
    }

    private Power getPowerDown() {

        return new Power(getRandomPowerNr(4, 6), game);
    }

    private int getRandomPowerNr(int start, int end) {
        return 1 + (int) (Math.random() * ((end - start) + start));
    }

}
