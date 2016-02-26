package gui;

import core.Settings;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Enumeration;

class SettingsPanel extends JPanel {

    //region declare components
    private JLabel lblPanelName, lblGameMode, lblDifficulty, lblPlayer1Controls, lblPlayer2Controls, lblFireBallControl, lblPauseControl;
    private JLabel lblEmpty1, lblEmpty2, lblEmpty3, lblEmpty4, lblEmpty5;
    private JRadioButton btnGameModeSingle, btnGameModeMulti, btnGameDifficultyEasy, btnGameDifficultyMedium, btnGameDifficultyHard;
    private ButtonGroup gameModeGroup, gameDifficultyGroup;
    private JButton btnMenu, btnSave, btnPlayer1Left, btnPlayer1Right, btnPlayer2Left, btnPlayer2Right, btnFireBall, btnPause;
    private JPanel topPanel, midPanel, botPanel;
    private BufferedImage backgroundImage;
    private Font fontLabelMiriam, fontButtonMiriam;
    //endregion
    //region new controls
    private int newPlayer1left = KeyEvent.VK_Q;
    private int newPlayer1right = KeyEvent.VK_D;
    private int newPlayer2left = KeyEvent.VK_NUMPAD1;
    private int newPlayer2right = KeyEvent.VK_NUMPAD3;
    private int newPause = KeyEvent.VK_P;
    private int newLaunchBall = KeyEvent.VK_SPACE;
    //endregion

    private Boolean[] controlsToSet = new Boolean[6];
    private Settings S = new Settings();
    private FrameServer server;

    private Color fg = new Color(103, 194, 255);
    private Color bg = Color.BLACK;

    public SettingsPanel(FrameServer server, JFrame parent) {
        super();
        this.server = server;
        setLayout(new BorderLayout(0, 150));
        setBorder(new EmptyBorder(20, 20, 20, 20)); //top, left, bottom, right
        setFocusable(true);
        createComponents();
        addComponents();
        addActionListeners(parent);
    }

    public void createComponents() {
        //region create fonts
        fontButtonMiriam = new Font("Miriam", Font.BOLD, 18);
        fontLabelMiriam = new Font("Miriam", Font.BOLD, 38);
        //endregion
        //region create subpanels
        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.setOpaque(false);

        midPanel = new JPanel();
        midPanel.setLayout(new GridLayout(6, 4, 90, 20));  //(int rows, int columns, int horizontalPadding, int verticalPadding)
        midPanel.setBorder(new EmptyBorder(0, 100, 0, 0)); //top, left, bottom, right
        midPanel.setOpaque(false);

        botPanel = new JPanel();
        botPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
        botPanel.setBorder(new EmptyBorder(0, 20, 0, 20));
        botPanel.setOpaque(false);
        //endregion
        //region create labels
        lblPanelName = new JLabel("SETTINGS");
        lblPanelName.setFont(fontLabelMiriam);
        lblPanelName.setForeground(fg);

        lblGameMode = new JLabel("game mode:");
        lblGameMode.setFont(fontButtonMiriam);
        lblGameMode.setForeground(fg);

        lblDifficulty = new JLabel("Difficulty:");
        lblDifficulty.setFont(fontButtonMiriam);
        lblDifficulty.setForeground(fg);

        lblPlayer1Controls = new JLabel(("Controls player 1:"));
        lblPlayer1Controls.setFont(fontButtonMiriam);
        lblPlayer1Controls.setForeground(fg);

        lblPlayer2Controls = new JLabel(("Controls player 2:"));
        lblPlayer2Controls.setFont(fontButtonMiriam);
        lblPlayer2Controls.setForeground(fg);

        lblPauseControl = new JLabel(("Pause:"));
        lblPauseControl.setFont(fontButtonMiriam);
        lblPauseControl.setForeground(fg);

        lblFireBallControl = new JLabel(("Launch ball:"));
        lblFireBallControl.setFont(fontButtonMiriam);
        lblFireBallControl.setForeground(fg);

        lblEmpty1 = new JLabel("");
        lblEmpty2 = new JLabel("");
        lblEmpty3 = new JLabel("");
        lblEmpty4 = new JLabel("");
        lblEmpty5 = new JLabel("");
        //endregion
        //region create radiobuttons
        btnGameModeSingle = new JRadioButton("Singleplayer", false);
        btnGameModeSingle.setForeground(fg);
        btnGameModeSingle.setOpaque(false);
        btnGameModeSingle.setFont(fontButtonMiriam);

        btnGameModeMulti = new JRadioButton("Multiplayer", false);
        btnGameModeMulti.setForeground(fg);
        btnGameModeMulti.setOpaque(false);
        btnGameModeMulti.setFont(fontButtonMiriam);

        btnGameDifficultyEasy = new JRadioButton("Easy", false);
        btnGameDifficultyEasy.setForeground(fg);
        btnGameDifficultyEasy.setOpaque(false);
        btnGameDifficultyEasy.setFont(fontButtonMiriam);

        btnGameDifficultyMedium = new JRadioButton("Medium", false);
        btnGameDifficultyMedium.setForeground(fg);
        btnGameDifficultyMedium.setOpaque(false);
        btnGameDifficultyMedium.setFont(fontButtonMiriam);
        btnGameDifficultyHard = new JRadioButton("Hard", false);
        btnGameDifficultyHard.setFont(fontButtonMiriam);
        btnGameDifficultyHard.setForeground(fg);
        btnGameDifficultyHard.setOpaque(false);
        //endregion
        //region create buttons
        btnPlayer1Left = new JButton("Left: " + KeyCodeToText(S.getPlayer1left()));
        btnPlayer1Left.setForeground(fg);
        btnPlayer1Left.setBackground(bg);
        btnPlayer1Left.setFont(fontButtonMiriam);

        btnPlayer1Right = new JButton("Right: " + KeyCodeToText(S.getPlayer1right()));
        btnPlayer1Right.setForeground(fg);
        btnPlayer1Right.setBackground(bg);
        btnPlayer1Right.setFont(fontButtonMiriam);

        btnPlayer2Left = new JButton("Left: " + KeyCodeToText(S.getPlayer2left()));
        btnPlayer2Left.setForeground(fg);
        btnPlayer2Left.setBackground(bg);
        btnPlayer2Left.setFont(fontButtonMiriam);

        btnPlayer2Right = new JButton("Right: " + KeyCodeToText(S.getPlayer2right()));
        btnPlayer2Right.setForeground(fg);
        btnPlayer2Right.setBackground(bg);
        btnPlayer2Right.setFont(fontButtonMiriam);

        btnFireBall = new JButton("Space");
        btnFireBall.setForeground(fg);
        btnFireBall.setBackground(bg);
        btnFireBall.setFont(fontButtonMiriam);

        btnPause = new JButton("P");
        btnPause.setForeground(fg);
        btnPause.setBackground(bg);
        btnPause.setFont(fontButtonMiriam);

        btnSave = new JButton("Save");
        btnSave.setForeground(fg);
        btnSave.setBackground(bg);
        btnSave.setFont(fontButtonMiriam);
        btnSave.setPreferredSize(new Dimension(150, 35));

        btnMenu = new JButton("Discard");
        btnMenu.setForeground(fg);
        btnMenu.setBackground(bg);
        btnMenu.setFont(fontButtonMiriam);
        btnMenu.setPreferredSize(new Dimension(150, 35));
        //endregion
        //region add buttongroups
        gameModeGroup = new ButtonGroup();
        gameDifficultyGroup = new ButtonGroup();
        //endregion
        //region add radiobuttons to buttongroups
        gameModeGroup.add(btnGameModeSingle);
        gameModeGroup.add(btnGameModeMulti);
        gameDifficultyGroup.add(btnGameDifficultyEasy);
        gameDifficultyGroup.add(btnGameDifficultyMedium);
        gameDifficultyGroup.add(btnGameDifficultyHard);
        //endregion
        //region add images
        try {
            backgroundImage = ImageIO.read(getClass().getClassLoader().getResource("menuBackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //endregion
        //region set settings
        if (S.getGameMode().equals("Multiplayer")) {
            btnGameModeMulti.setSelected(true);
        } else {
            btnGameModeSingle.setSelected(true);
        }

        if (S.getGameDifficulty().equals("Easy")) {
            btnGameDifficultyEasy.setSelected(true);
        }
        if (S.getGameDifficulty().equals("Hard")) {
            btnGameDifficultyHard.setSelected(true);
        } else {
            btnGameDifficultyMedium.setSelected(true);
        }
        //endregion
    }

    public void addComponents() {
        //region add components to subpanels
        topPanel.add(lblPanelName);

        midPanel.add(lblGameMode);
        midPanel.add(btnGameModeSingle);
        midPanel.add(btnGameModeMulti);
        midPanel.add(lblEmpty1);

        midPanel.add(lblDifficulty);
        midPanel.add(btnGameDifficultyEasy);
        midPanel.add(btnGameDifficultyMedium);
        midPanel.add(btnGameDifficultyHard);

        midPanel.add(lblPlayer1Controls);
        midPanel.add(btnPlayer1Left);
        midPanel.add(btnPlayer1Right);
        midPanel.add(lblEmpty2);

        midPanel.add(lblPlayer2Controls);
        midPanel.add(btnPlayer2Left);
        midPanel.add(btnPlayer2Right);
        midPanel.add(lblEmpty3);

        midPanel.add(lblFireBallControl);
        midPanel.add(btnFireBall);
        midPanel.add(lblEmpty4);
        midPanel.add(lblEmpty5);

        midPanel.add(lblPauseControl);
        midPanel.add(btnPause);

        botPanel.add(btnSave);
        botPanel.add(btnMenu);
        //endregion
        //region add subpanels
        add(topPanel, BorderLayout.NORTH);
        add(midPanel, BorderLayout.CENTER);
        add(botPanel, BorderLayout.SOUTH);
        //endregion
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRoundRect(100, 150, getWidth() - 200, getHeight() - 300, 100, 100);
    }

    public void addActionListeners(JFrame parent) {
        final JFrame parentFrame = parent;

        //region keylistener
        final KeyListener kl = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                System.out.println("switch index " + IndexOfControlToSet());
                switch (IndexOfControlToSet()) {
                    case 0:
                        newPlayer1left = e.getKeyCode();
                        resetButtonColor();
                        btnPlayer1Left.setText("Left: " + KeyCodeToText(e.getKeyCode()));
                        break;
                    case 1:
                        newPlayer1right = e.getKeyCode();
                        resetButtonColor();
                        btnPlayer1Right.setText("Right: " + KeyCodeToText(e.getKeyCode()));
                        break;
                    case 2:
                        newPlayer2left = e.getKeyCode();
                        resetButtonColor();
                        btnPlayer2Left.setText("Left: " + KeyCodeToText(e.getKeyCode()));
                        break;
                    case 3:
                        newPlayer2right = e.getKeyCode();
                        resetButtonColor();
                        btnPlayer2Right.setText("Right: " + KeyCodeToText(e.getKeyCode()));
                        break;
                    case 4:
                        newLaunchBall = e.getKeyCode();
                        resetButtonColor();
                        btnFireBall.setText("" + KeyCodeToText(e.getKeyCode()));
                        break;
                    case 5:
                        newPause = e.getKeyCode();
                        resetButtonColor();
                        btnPause.setText("" + KeyCodeToText(e.getKeyCode()));
                        break;
                    default:
                        System.out.println("No control selected to be set");
                        break;
                }
                resetControlsToSet();
            }
        };
        //endregion keylistener
        //region add key listeners
        // parentFrame.addKeyListener(kl);
        btnPlayer1Left.addKeyListener(kl);
        btnPlayer1Right.addKeyListener(kl);
        btnPlayer2Left.addKeyListener(kl);
        btnPlayer2Right.addKeyListener(kl);
        btnFireBall.addKeyListener(kl);
        btnPause.addKeyListener(kl);
        //endregion
        //region control button action listeners
        btnPlayer1Left.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                resetControlsToSet();
                resetButtonColor();
                btnPlayer1Left.setBackground(Color.RED);
                controlsToSet[0] = true;
            }
        });

        btnPlayer1Right.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetControlsToSet();
                resetButtonColor();
                btnPlayer1Right.setBackground(Color.RED);
                controlsToSet[1] = true;
            }
        });

        btnPlayer2Left.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetControlsToSet();
                resetButtonColor();
                btnPlayer2Left.setBackground(Color.RED);
                controlsToSet[2] = true;
            }
        });

        btnPlayer2Right.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetControlsToSet();
                resetButtonColor();
                btnPlayer2Right.setBackground(Color.RED);
                controlsToSet[3] = true;
            }
        });

        btnFireBall.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetControlsToSet();
                resetButtonColor();
                btnFireBall.setBackground(Color.RED);
                controlsToSet[4] = true;
            }
        });

        btnPause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetControlsToSet();
                resetButtonColor();
                btnPause.setBackground(Color.RED);
                controlsToSet[5] = true;
            }
        });
        //endregion
        //region settings button action listeners
        btnMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //parentFrame.removeKeyListener(kl);
                server.changeContentPaneTo(FrameServer.MODE_MENU);
            }
        });

        btnSave.addActionListener(new ActionListener() {
            @Override
            //check for all radiobuttons in a buttongroup if the button is checked
            public void actionPerformed(ActionEvent e) {
                Enumeration gameModeGroupElements = gameModeGroup.getElements();
                while (gameModeGroupElements.hasMoreElements()) {
                    AbstractButton button = (AbstractButton) gameModeGroupElements.nextElement();
                    if (button.isSelected()) {
                        S.setGameMode(button.getText());
                    }
                }
                Enumeration gameDifficultyGroupElements = gameDifficultyGroup.getElements();//zet de difficulty
                while (gameDifficultyGroupElements.hasMoreElements()) {
                    AbstractButton button = (AbstractButton) gameDifficultyGroupElements.nextElement();
                    if (button.isSelected()) {
                        S.setGameDifficulty(button.getText());
                    }
                }

                applyNewControls();
                //      parentFrame.removeKeyListener(kl);
                server.changeContentPaneTo(FrameServer.MODE_MENU);
            }
        });
        //endregion
    }

    private void resetControlsToSet() {
        for (int i = 0; i < controlsToSet.length; i++) {
            controlsToSet[i] = false;
        }
    }

    private int IndexOfControlToSet() {
        int index = 0;
        for (int i = 0; i < controlsToSet.length; i++) {
            if (controlsToSet[i]) {
                index = i;
            }
        }
        return index;
    }

    private void resetButtonColor() {
        btnPlayer1Left.setBackground(bg);
        btnPlayer1Right.setBackground(bg);
        btnPlayer2Left.setBackground(bg);
        btnPlayer2Right.setBackground(bg);
        btnFireBall.setBackground(bg);
        btnPause.setBackground(bg);
    }

    private void applyNewControls() {
        S.setPlayer1left(newPlayer1left);
        S.setPlayer1right(newPlayer1right);
        S.setPlayer2left(newPlayer2left);
        S.setPlayer2right(newPlayer2right);
        S.setLaunchBall(newLaunchBall);
        S.setPause(newPause);
    }

    private String KeyCodeToText(int keycode) {
        String text = "Invalid key";
        switch (keycode) {
            case KeyEvent.VK_A:
                text = "A";
                break;
            case KeyEvent.VK_B:
                text = "B";
                break;
            case KeyEvent.VK_C:
                text = "C";
                break;
            case KeyEvent.VK_D:
                text = "D";
                break;
            case KeyEvent.VK_E:
                text = "E";
                break;
            case KeyEvent.VK_F:
                text = "F";
                break;
            case KeyEvent.VK_G:
                text = "G";
                break;
            case KeyEvent.VK_H:
                text = "H";
                break;
            case KeyEvent.VK_I:
                text = "I";
                break;
            case KeyEvent.VK_J:
                text = "J";
                break;
            case KeyEvent.VK_K:
                text = "K";
                break;
            case KeyEvent.VK_L:
                text = "L";
                break;
            case KeyEvent.VK_M:
                text = "M";
                break;
            case KeyEvent.VK_N:
                text = "N";
                break;
            case KeyEvent.VK_O:
                text = "O";
                break;
            case KeyEvent.VK_P:
                text = "P";
                break;
            case KeyEvent.VK_Q:
                text = "Q";
                break;
            case KeyEvent.VK_R:
                text = "R";
                break;
            case KeyEvent.VK_S:
                text = "S";
                break;
            case KeyEvent.VK_T:
                text = "T";
                break;
            case KeyEvent.VK_U:
                text = "U";
                break;
            case KeyEvent.VK_V:
                text = "V";
                break;
            case KeyEvent.VK_W:
                text = "W";
                break;
            case KeyEvent.VK_X:
                text = "X";
                break;
            case KeyEvent.VK_Y:
                text = "Y";
                break;
            case KeyEvent.VK_Z:
                text = "Z";
                break;

            case KeyEvent.VK_0:
                text = "0";
                break;
            case KeyEvent.VK_1:
                text = "1";
                break;
            case KeyEvent.VK_2:
                text = "2";
                break;
            case KeyEvent.VK_3:
                text = "3";
                break;
            case KeyEvent.VK_4:
                text = "4";
                break;
            case KeyEvent.VK_5:
                text = "5";
                break;
            case KeyEvent.VK_6:
                text = "6";
                break;
            case KeyEvent.VK_7:
                text = "7";
                break;
            case KeyEvent.VK_8:
                text = "8";
                break;
            case KeyEvent.VK_9:
                text = "9";
                break;

            case KeyEvent.VK_NUMPAD0:
                text = "0";
                break;
            case KeyEvent.VK_NUMPAD1:
                text = "1";
                break;
            case KeyEvent.VK_NUMPAD2:
                text = "2";
                break;
            case KeyEvent.VK_NUMPAD3:
                text = "3";
                break;
            case KeyEvent.VK_NUMPAD4:
                text = "4";
                break;
            case KeyEvent.VK_NUMPAD5:
                text = "5";
                break;
            case KeyEvent.VK_NUMPAD6:
                text = "6";
                break;
            case KeyEvent.VK_NUMPAD7:
                text = "7";
                break;
            case KeyEvent.VK_NUMPAD8:
                text = "8";
                break;
            case KeyEvent.VK_NUMPAD9:
                text = "9";
                break;

            case KeyEvent.VK_F1:
                text = "F1";
                break;
            case KeyEvent.VK_F2:
                text = "F2";
                break;
            case KeyEvent.VK_F3:
                text = "F3";
                break;
            case KeyEvent.VK_F4:
                text = "F4";
                break;
            case KeyEvent.VK_F5:
                text = "F5";
                break;
            case KeyEvent.VK_F6:
                text = "F6";
                break;
            case KeyEvent.VK_F7:
                text = "F7";
                break;
            case KeyEvent.VK_F8:
                text = "F8";
                break;
            case KeyEvent.VK_F9:
                text = "F9";
                break;
            case KeyEvent.VK_F10:
                text = "F10";
                break;
            case KeyEvent.VK_F11:
                text = "F11";
                break;
            case KeyEvent.VK_F12:
                text = "F12";
                break;

            case KeyEvent.VK_UP:
                text = "Up";
                break;
            case KeyEvent.VK_DOWN:
                text = "Down";
                break;
            case KeyEvent.VK_LEFT:
                text = "Left";
                break;
            case KeyEvent.VK_RIGHT:
                text = "Right";
                break;

            case KeyEvent.VK_TAB:
                text = "Tab";
                break;
            case KeyEvent.VK_SHIFT:
                text = "Shift";
                break;
            case KeyEvent.VK_CONTROL:
                text = "Ctrl";
                break;
            case KeyEvent.VK_ALT:
                text = "Alt";
                break;
            case KeyEvent.VK_ALT_GRAPH:
                text = "Alt Gr";
                break;
            case KeyEvent.VK_SPACE:
                text = "Space";
                break;
        }
        return text;
    }

}
