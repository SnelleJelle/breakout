package gui;

import core.Breakout;
import core.BreakoutObserver;
import core.Settings;
import core.Sprite;
import dal.ScoreSaver;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.TimerTask;

public class InnerGamePanel extends JPanel implements BreakoutObserver {

    private JFrame parentFrame;
    private Settings S = new Settings();

    private BufferedImage backgroundImage;
    private double bgPosition = 0;
    private double bgPositionToAdd = 0.2;
    private boolean timerRunning = false;
    private int elapsedMilliseconds = 0;

    private java.util.Timer timer;
    private FrameServer server;
    private Breakout game;

    private KeyListener kl;

    public InnerGamePanel(FrameServer server, JFrame parentFrame, Breakout game) {
        this.parentFrame = parentFrame;
        this.server = server;

        this.game = game;
        game.addObserver(this);
        setFocusable(true);
        createComponents();
        addComponents(this.parentFrame);

        UIManager UI = new UIManager();
        UIManager.put("OptionPane.background", new ColorUIResource(158, 143, 199));
        UIManager.put("Panel.background", new ColorUIResource(158, 143, 199));

        startGame();
    }

    private void startGame() {
        startTimer();
    }

    private void startTimer() {
        TimerTask taskGameLoop = new TimerTask() {
            @Override
            public void run() {
                gameLoop();
            }
        };

        timer = new java.util.Timer();
        timer.schedule(taskGameLoop, 0, 10);
        timerRunning = true;
    }

    private void stopTimer() {
        timer.cancel();
        timerRunning = false;
    }

    private void toggleTimer() {
        if (timerRunning) {
            stopTimer();
        } else {
            startGame();
        }
    }

    private void createComponents() {
        try {
            backgroundImage = ImageIO.read(getClass().getClassLoader().getResource("gameBackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addComponents(JFrame parent) {
        kl = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == S.getPause()) {
                    toggleTimer();
                }

                if (!timerRunning) {
                    return;
                    //ignore key input
                }

                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    quitGame();
                    return;
                }

                game.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                game.keyReleased(e);
            }
        };
        parent.addKeyListener(kl);
    }

    private void quitGame() {
        stopTimer();

        parentFrame.removeKeyListener(kl);

        server.changeContentPaneTo(FrameServer.MODE_MENU);
    }

    private void gameOverDialog() {

        int totalScore = game.getTotalScore();

        //region create dialog

        JPanel endLevelPanel = new JPanel();
        JLabel lblMessage;
        JTextField txtPlayer1Name = new JTextField(10);
        JTextField txtPlayer2Name = new JTextField(10);

        if (game.twoPlayerMode) {
            lblMessage = new JLabel("Game over! Player 1 scored " + game.players.get(0).getScore() + " and player 2 scored " + game.players.get(1).getScore() + ". Total score: " + totalScore);
            endLevelPanel.add(lblMessage);
            endLevelPanel.add(new JLabel("Player 1: "));
            endLevelPanel.add(txtPlayer1Name);
            endLevelPanel.add(new JLabel("Player 2: "));
            endLevelPanel.add(txtPlayer2Name);
        } else {
            lblMessage = new JLabel("Game over! You scored " + totalScore + ".");
            endLevelPanel.add(lblMessage);
            endLevelPanel.add(new JLabel("Player 1: "));
            endLevelPanel.add(txtPlayer1Name);
        }

        //endregion

        ScoreSaver scoreSaver = new ScoreSaver();

        int result = JOptionPane.showConfirmDialog(this, endLevelPanel, "Game ended", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION && game.twoPlayerMode)    //als er op OK geklikt is bij multiplayer
        {
            //zolang er nogmaals op OK geklikt wordt en er een naam te lang is
            while (result == JOptionPane.OK_OPTION && !inputNameCorrect(txtPlayer1Name.getText(), txtPlayer2Name.getText())) {
                lblMessage.setText("One or both names are too long. Max nickname length is 12 characters.");
                result = JOptionPane.showConfirmDialog(this, endLevelPanel, "Game ended", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            }
            if (result == JOptionPane.OK_OPTION)    //wanneer de namen niet te lang zijn en er op OK geklikt is -> save
            {
                scoreSaver.insertIntoHighscoresMulti(txtPlayer1Name.getText(), game.players.get(0).getScore(), txtPlayer2Name.getText(), game.players.get(1).getScore());
            }
//            else    //wanneer er op cancel gedrukt is -> niet saven en gewoon naar main menu gaan
//            {
//                server.changeContentPaneTo(FrameServer.MODE_MENU);
//            }
        } else if (result == JOptionPane.OK_OPTION) {
            //zolang er nogmaals op OK geklikt wordt en er een naam te lang is
            while (result == JOptionPane.OK_OPTION && !inputNameCorrect(txtPlayer1Name.getText())) {
                lblMessage.setText("Max nickname length is 12 characters. Please enter a shorter nickname.");
                result = JOptionPane.showConfirmDialog(this, endLevelPanel, "Game ended", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            }
            if (result == JOptionPane.OK_OPTION)    //wanneer de namen niet te lang zijn en er op OK geklikt is -> save
            {
                scoreSaver.insertIntoHighscoresSingle(txtPlayer1Name.getText(), totalScore);
            }
//            else    //wanneer er op cancel gedrukt is -> niet saven en gewoon naar main menu gaan
//            {
//                server.changeContentPaneTo(FrameServer.MODE_MENU);
//            }
        }
        quitGame();
    }

    private void MultiPlayerWonDialog() {

        int scoreWithoutTimerBonus = game.getTotalScore();
        int scoreMultiplier = 150000 - elapsedMilliseconds; // 150 seconds
        if (scoreMultiplier < 0){
            scoreMultiplier = 0;
        }
        int totalScore = scoreWithoutTimerBonus * (1 + scoreMultiplier / 100);
        int player1Score = game.players.get(0).getScore() * (1 + scoreMultiplier / 100);
        int player2Score = game.players.get(1).getScore() * (1 + scoreMultiplier / 100);

        //region create panel
        JPanel endLevelPanel = new JPanel();
        JLabel lblMessage = new JLabel("You won! Player 1 scored " + game.players.get(0).getScore() + " and player 2 scored " + game.players.get(1).getScore() + ". Total score: " + totalScore);
        JTextField txtPlayer1Name = new JTextField(10);
        JTextField txtPlayer2Name = new JTextField(10);

        endLevelPanel.add(lblMessage);
        endLevelPanel.add(new JLabel("Player 1: "));
        endLevelPanel.add(txtPlayer1Name);
        endLevelPanel.add(Box.createHorizontalStrut(15));
        endLevelPanel.add(new JLabel("Player 2: "));
        endLevelPanel.add(txtPlayer2Name);
        //endregion

        ScoreSaver scoreSaver = new ScoreSaver();

        int result = JOptionPane.showConfirmDialog(this, endLevelPanel, "Game ended", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION)    //als er op OK geklikt is
        {
            //zolang er nogmaals op OK geklikt wordt en de naam te lang is
            while (result == JOptionPane.OK_OPTION && !inputNameCorrect(txtPlayer1Name.getText(), txtPlayer2Name.getText())) {
                lblMessage.setText("Max nickname length is 12 characters. Please enter a shorter nickname.");
                result = JOptionPane.showConfirmDialog(this, endLevelPanel, "Game ended", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            }
            if (result == JOptionPane.OK_OPTION)    //wanneer de naam niet te lang is en er op OK geklikt is -> save
            {
                scoreSaver.insertIntoHighscoresMulti(txtPlayer1Name.getText(), player1Score, txtPlayer2Name.getText(), player2Score);
            }
//            else    //wanneer er op cancel gedrukt is -> niet saven en gewoon naar main menu gaan
//            {
//                server.changeContentPaneTo(FrameServer.MODE_MENU);
//            }
        }
        quitGame();
    }

    private void SinglePlayerWonDialog() {

        int scoreWithoutTimerBonus = game.getTotalScore();
        int scoreMultiplier = 150000 - elapsedMilliseconds; // 150 seconds
        if (scoreMultiplier < 0){
            scoreMultiplier = 0;
        }
        int totalScore = scoreWithoutTimerBonus * (1 + scoreMultiplier / 100);

        //region create panel
        JPanel endLevelPanel = new JPanel();
        JLabel lblMessage = new JLabel("You won! You scored " + totalScore + ".");
        JTextField txtPlayerName = new JTextField(10);

        endLevelPanel.add(lblMessage);
        endLevelPanel.add(new JLabel("Player name: "));
        endLevelPanel.add(txtPlayerName);
        //endregion

        ScoreSaver scoreSaver = new ScoreSaver();

        int result = JOptionPane.showConfirmDialog(this, endLevelPanel, "Game ended", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION)    //als er op OK geklikt is
        {
            //zolang er nogmaals op OK geklikt wordt en de naam te lang is
            while (result == JOptionPane.OK_OPTION && !inputNameCorrect(txtPlayerName.getText())) {
                lblMessage.setText("Max nickname length is 12 characters. Please enter a shorter nickname.");
                result = JOptionPane.showConfirmDialog(this, endLevelPanel, "Game ended", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            }
            if (result == JOptionPane.OK_OPTION)    //wanneer de naam niet te lang is en er op OK geklikt is -> save
            {
                scoreSaver.insertIntoHighscoresSingle(txtPlayerName.getText(), totalScore);
            }
//            else    //wanneer er op cancel gedrukt is -> niet saven en gewoon naar main menu gaan
//            {
//                server.changeContentPaneTo(FrameServer.MODE_MENU);
//            }
        }
        quitGame();
    }

    private boolean inputNameCorrect(String nickname1) {
        if (nickname1.length() > 12) {
            return false;
        } else {
            return true;
        }
    }

    private boolean inputNameCorrect(String nickname1, String nickname2) {
        if (nickname1.length() > 12 || nickname2.length() > 12) {
            return false;
        } else {
            return true;
        }

        //return inputNameCorrect(nickname1) && inputNameCorrect(nickname2);
    }

    @Override
    public void paint(Graphics g) {
        //region setup graphics
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        //endregion

        //region move background
        g.drawImage(backgroundImage, (int) bgPosition * -1, 0, 5760, getHeight(), this);
        if ((int) bgPosition == (5760 - 1280)) {
            bgPositionToAdd = -0.2;
        }
        if ((int) bgPosition == 0) {
            bgPositionToAdd = 0.2;
        }
        bgPosition += bgPositionToAdd;
        //endregion

        //region paint game sprites
        for (Sprite sprite : game.getSprites()) {
            sprite.Paint(g2d);
        }
        //endregion
    }

    public void gameLoop() {
        game.move();
        repaint();
        elapsedMilliseconds += 10;
    }

    @Override
    public void update() {

        if (game.getLivesLeft() == 0) {  //single of multi, verloren
            gameOverDialog();
        } else if (game.twoPlayerMode && game.getTargets().isEmpty()) {  //multi, gewonnen
            MultiPlayerWonDialog();
        } else if (game.getCurrentLevel() == 10 && game.getTargets().isEmpty()) {    //single, gewonnen
            SinglePlayerWonDialog();
        }
    }
}