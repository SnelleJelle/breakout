package gui;

import core.Breakout;
import core.BreakoutObserver;

import javax.swing.*;
import java.awt.*;

public class GameStatusPanel extends JPanel implements BreakoutObserver {

    private JLabel lblTotalScore, lblLives, lblLatestPower, lblCurrentLevelNr;
    private Breakout game;

    public GameStatusPanel(Breakout game) {
        this.game = game;
        game.addObserver(this);
        setOpaque(true);
        setBackground(Color.BLACK);
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5)); //align, horizontal gap, vertical gap
        createComponents();
        addComponents();
    }

    private void createComponents() {
        lblLives = new JLabel("Lives left: " + game.getLivesLeft());
        lblLives.setForeground(Color.WHITE);

        lblTotalScore = new JLabel("Total score: 0");
        lblTotalScore.setForeground(Color.WHITE);

        lblLatestPower = new JLabel("Latest power: NONE");
        lblLatestPower.setForeground(Color.WHITE);

        lblCurrentLevelNr = new JLabel("Current Level: 1");
        lblCurrentLevelNr.setForeground(Color.WHITE);
    }

    private void addComponents() {
        add(lblLives);
        add(lblTotalScore);
        add(lblLatestPower);
        add(lblCurrentLevelNr);
    }

    private void updateMultiPlayerStatus() {

        updateSinglePlayerStatus();

        String tab = "      ";
        int[] scores = game.getScores();
        ;
        int totalScore = scores[1] + scores[2];

        String scoreString = tab + "Total score: " + totalScore + tab + "Player 1: " + scores[1] + tab + "Player 2: " + scores[2];

        lblTotalScore.setText(scoreString);
    }

    private void updateSinglePlayerStatus() {
        lblLives.setText("Lives left: " + game.getLivesLeft());
        int totalScore = game.players.get(0).getScore();
        lblTotalScore.setText("Total score: " + totalScore);
        lblLatestPower.setText("      Latest power: " + game.getLastUsedPowerName());
        lblCurrentLevelNr.setText("     Current Level: " + game.getCurrentLevel());
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.CYAN);
        g.drawLine(0,getHeight() - 1, getWidth(),getHeight() - 1);
    }

    @Override
    public void update() {
        if (game.twoPlayerMode) {
            updateMultiPlayerStatus();

        } else {
            updateSinglePlayerStatus();
        }
        repaint();
    }
}
