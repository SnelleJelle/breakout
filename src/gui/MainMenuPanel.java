package gui;

import dal.DatabaseConnection;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

class MainMenuPanel extends JPanel {

    DatabaseConnection dbc = new DatabaseConnection();
    boolean threadRunning = true;
    //declare components
    private JLabel lblPanelTitle, lblDBConnection;
    private JButton btnGame, btnHighscores, btnSettings;
    private BufferedImage backgroundImage;
    private Font fontButtonMiriam, fontLabelMiriam;
    private Color fg = new Color(103, 194, 255);
    private Color bg = Color.BLACK;
    private FrameServer server;
    private Thread t;

    public MainMenuPanel(FrameServer server) {
        super();
        this.server = server;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        createComponents();
        addComponents();
        addActionListeners();
        addDatabaseConnectionThread();
    }

    private void addDatabaseConnectionThread() {
        t = new Thread() {
            @Override
            public void run() {
                while (!interrupted() && threadRunning) {

                    if (dbc.connectionWorks()) {
                        lblDBConnection.setText("");
                    } else {
                        lblDBConnection.setText("database connection failed");
                        lblDBConnection.setFont(new Font("Miriam", Font.PLAIN, 12));
                    }

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
    }

    private void stopThread() {
        threadRunning = false;
    }

    private void createComponents() {

        //create components
        //fonts
        fontButtonMiriam = new Font("Miriam", Font.BOLD, 18);
        fontLabelMiriam = new Font("Miriam", Font.BOLD, 38);

        //MouseAdapter for resizing at mouse hover
        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                JButton b = (JButton) e.getSource();
                b.setLocation(b.getLocation().x - 25, b.getLocation().y - 10);
                b.setSize(b.getWidth() + 50, b.getHeight() + 20);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                JButton b = (JButton) e.getSource();
                b.setLocation(b.getLocation().x + 25, b.getLocation().y + 10);
                b.setSize(b.getWidth() - 50, b.getHeight() - 20);
            }
        };

        //labels
        lblPanelTitle = new JLabel("MAIN MENU");
        lblPanelTitle.setForeground(fg);
        lblPanelTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblPanelTitle.setFont(fontLabelMiriam);

        lblDBConnection = new JLabel("testing database connection");
        lblDBConnection.setForeground(Color.WHITE);
        lblDBConnection.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblDBConnection.setFont(new Font("Miriam", Font.PLAIN, 12));

        //buttons
        btnGame = new JButton("Start game");
        btnGame.setMaximumSize(new Dimension(250, 40));
        btnGame.setFont(fontButtonMiriam);
        btnGame.setForeground(fg);
        btnGame.setBackground(bg);
        btnGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnGame.addMouseListener(ma);

        btnHighscores = new JButton("Highscores");
        btnHighscores.setMaximumSize(new Dimension(250, 40));
        btnHighscores.setFont(fontButtonMiriam);
        btnHighscores.setForeground(fg);
        btnHighscores.setBackground(bg);
        btnHighscores.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnHighscores.addMouseListener(ma);

        btnSettings = new JButton("Settings");
        btnSettings.setMaximumSize(new Dimension(250, 40));
        btnSettings.setFont(fontButtonMiriam);
        btnSettings.setForeground(fg);
        btnSettings.setBackground(bg);
        btnSettings.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSettings.addMouseListener(ma);

        //images
        try {
            backgroundImage = ImageIO.read(getClass().getClassLoader().getResource("menuBackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    private void addComponents() {
        add(lblPanelTitle);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(lblDBConnection);
        add(Box.createRigidArea(new Dimension(0, 100)));
        add(btnGame);
        add(Box.createRigidArea(new Dimension(0, 60)));
        add(btnHighscores);
        add(Box.createRigidArea(new Dimension(0, 60)));
        add(btnSettings);
        add(Box.createRigidArea(new Dimension(0, 60)));
    }

    private void addActionListeners() {
        btnGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (dbc.connectionWorks()) {
                    server.changeContentPaneTo(FrameServer.MODE_GAME);
                    stopThread();
                } else {
                    lblDBConnection.setFont(new Font("Arial", Font.PLAIN, 20));
                }
            }
        });
        btnHighscores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (dbc.connectionWorks()) {
                    server.changeContentPaneTo(FrameServer.MODE_HIGHSCORES);
                    stopThread();
                } else {
                    lblDBConnection.setFont(new Font("Arial", Font.PLAIN, 20));
                }
            }
        });
        btnSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.changeContentPaneTo(FrameServer.MODE_SETTINGS);
            }
        });
    }
}
