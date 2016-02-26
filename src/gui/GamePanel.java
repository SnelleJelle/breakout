package gui;

import core.Breakout;
import core.Settings;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private JFrame parentFrame;
    private FrameServer server;

    private InnerGamePanel innerGame;
    private GameStatusPanel gameStatusPanel;

    private Dimension gameDimension = new Dimension(1274, 661);
    private Settings S = new Settings();

    public GamePanel(FrameServer server, JFrame parentFrame) {
        super();
        this.server = server;
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());
        createComponents();
        addComponents();
    }

    public void createComponents() {
        boolean twoPlayerMode;
        if (S.getGameMode().equals("Singleplayer")) {
            twoPlayerMode = false;
        } else {
            twoPlayerMode = true;
        }

        Breakout breakoutGame = new Breakout(gameDimension, twoPlayerMode);
        innerGame = new InnerGamePanel(server, parentFrame, breakoutGame);
        innerGame.setSize((int) gameDimension.getWidth(), (int) gameDimension.getHeight());
        gameStatusPanel = new GameStatusPanel(breakoutGame);
    }

    public void addComponents() {
        add(gameStatusPanel, BorderLayout.NORTH);
        add(innerGame, BorderLayout.CENTER);
    }
}
