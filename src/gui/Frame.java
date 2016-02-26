package gui;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame implements FrameObserver {

    private FrameServer server;

    public Frame(FrameServer server) {
        super();

        this.server = server;
        this.server.addObserver(this);

        ImageIcon img = new ImageIcon(getClass().getClassLoader().getResource("icon.png"));
        setIconImage(img.getImage());

        setPreferredSize(new Dimension(1280, 720));
        setSize(1280, 720);
        setResizable(false);
        setVisible(true);
        setFocusable(true);
        setLocationRelativeTo(null);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        update();
    }

    @Override
    /*Wanneer de server notifyObservers() oproept wordt voor elk observer frame de functie update() opgeroepen*/
    public void update() {
        JPanel panel;
        switch (server.getCurrentstate()) {
            case 0:
                panel = new MainMenuPanel(server);
                setContentPane(panel);
                setTitle("Breakout - Menu");
                pack();
                break;
            case 1:
                panel = new GamePanel(server, this);
                setContentPane(panel);
                setTitle("Breakout - game");
                pack();
                break;
            case 2:
                panel = new HighscoresPanel(server);
                setContentPane(panel);
                setTitle("Breakout - Highscores");
                pack();
                break;
            case 3:
                panel = new SettingsPanel(server, this);
                setContentPane(panel);
                setTitle("Breakout - game.Settings");
                pack();
                break;
            default:
                panel = new MainMenuPanel(server);
                setContentPane(panel);
                setTitle("Breakout - Menu");
                pack();
                break;
        }
    }
}