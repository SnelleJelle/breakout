package gui;

import core.Highscore;
import dal.DatabaseConnection;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class HighscoresPanel extends JPanel {

    DatabaseConnection dbc;
    FrameServer server;
    private JLabel lblPanelName, lblHighscoresSingle, lblHighscoresMulti;
    private JButton btnMenu;
    private BufferedImage backgroundImage;
    private Font fontComponents, fontTitle, fontHighscoreNames;
    private JPanel centerPanel;
    private Color fg = new Color(103, 194, 255);

    public HighscoresPanel(FrameServer server) {
        super();
        this.server = server;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        createComponents();
        addComponents();
        addActionListeners();
        CompleteDatabaseConnection();
    }

    private void CompleteDatabaseConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                connectToDatabase();
                fillHighscores();
            }
        }).start();
    }

    public void createComponents() {
        fontComponents = new Font("Miriam", Font.BOLD, 18);
        fontTitle = new Font("Miriam", Font.BOLD, 38);
        fontHighscoreNames = new Font("Miram", Font.BOLD, 20);

        centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        centerPanel.setOpaque(false);

        lblPanelName = new JLabel("Highscores");
        lblPanelName.setForeground(fg);
        lblPanelName.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblPanelName.setFont(fontTitle);

        lblHighscoresSingle = new JLabel("Loading SinglePlayer highscores...");
        lblHighscoresSingle.setForeground(fg);
        lblHighscoresSingle.setHorizontalAlignment(JLabel.CENTER);
        lblHighscoresSingle.setFont(fontHighscoreNames);

        lblHighscoresMulti = new JLabel("Loading MultiPlayer highscores...");
        lblHighscoresMulti.setForeground(fg);
        lblHighscoresMulti.setHorizontalAlignment(JLabel.CENTER);
        lblHighscoresMulti.setFont(fontHighscoreNames);

        btnMenu = new JButton("Menu");
        btnMenu.setMaximumSize(new Dimension(150, 35));
        btnMenu.setForeground(new Color(103, 194, 255));
        btnMenu.setBackground(Color.BLACK);
        btnMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnMenu.setFont(fontComponents);

        try {

            backgroundImage = ImageIO.read(getClass().getClassLoader().getResource("menuBackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addComponents() {
        centerPanel.add(lblHighscoresSingle);
        centerPanel.add(lblHighscoresMulti);

        add(lblPanelName);
        add(Box.createRigidArea(new Dimension(0, 0)));
        add(centerPanel);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(btnMenu);
        add(Box.createRigidArea(new Dimension(0, 100)));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRoundRect(190, 10, 900, getHeight() - 20, 100, 100);
    }

    public void addActionListeners() {
        btnMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.changeContentPaneTo(FrameServer.MODE_MENU);
            }
        });
    }

    private void connectToDatabase() {
        dbc = new DatabaseConnection();

        try {
            dbc.run();
        } catch (Exception ex) {
            System.out.println("Connect to database failed");
        }
    }

    public void fillHighscores() {
        List<Highscore> singleHighscores = new ArrayList<Highscore>();
        List<Highscore> multiHighscores = new ArrayList<Highscore>();
        int rank = 1;

        try {
            singleHighscores = dbc.getSinglePlayerHighscores();
            multiHighscores = dbc.getMultiplayerHighscores();
        } catch (Exception ex) {
            System.out.println("Loading highscores failed.");
        }

        StringBuilder sbSingle = new StringBuilder();
        StringBuilder sbMulti = new StringBuilder();

        sbSingle.append("<html><center><h1>SinglePlayer</h1></center><table>");
        for (Highscore H : singleHighscores) {
            sbSingle.append("<tr><td> #" + rank + "</td><td width = '140px'>" + H.getNickname1() + " - " + H.getScore1() + "</td></tr>");
            rank++;
        }
        sbSingle.append("</table></html>");

        rank = 1;

        sbMulti.append("<html><center><h1>MultiPlayer</h1></center><table>");
        for (Highscore H : multiHighscores) {
            sbMulti.append("<tr><td># " + rank + "</td><td width='140px'>" + H.getNickname1() + " - " + H.getScore1() + "</td><td width='140px'>" + H.getNickname2() + " - " + H.getScore2() + "</td><td width='140px'>Total score: " + H.getTotalScore() + "</td></tr>");
            rank++;
        }
        sbMulti.append("<table></html>");

        lblHighscoresSingle.setText(sbSingle.toString());
        lblHighscoresMulti.setText(sbMulti.toString());
    }
}
