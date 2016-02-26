package core;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Paddle implements Sprite {

    Settings S = new Settings();
    private int xPosition, defaultPosition = 1274; //defaultposition to reset the paddle.
    private int xSpeed = 0;
    private int yPosition = 650;
    private int width = 90; // 15 * 6
    private int height = 20;
    private int speedMultiplier = 5;
    private Breakout game;
    private Player owner;


    public Paddle(Breakout game, Player owner, int yPosition) {
        this.game = game;
        setXPosition();
        this.yPosition = yPosition;
        this.owner = owner;
        defaultPosition = xPosition;
    }

    public Paddle(Player owner, int yPosition) {// for tests
        setXPosition();
        this.yPosition = yPosition;
        this.owner = owner;
    }

    public void move() {
        if (xPosition + xSpeed > 0 && xPosition + xSpeed < game.dimension.getWidth() - width) {
            xPosition += xSpeed;
        }
    }

    public void keyReleased(KeyEvent e) {
        if (owner.topPlayer) {
            if (e.getKeyCode() == S.getPlayer2left())
                xSpeed = 0;
            if (e.getKeyCode() == S.getPlayer2right())
                xSpeed = 0;
        } else {
            if (e.getKeyCode() == S.getPlayer1left())
                xSpeed = 0;
            if (e.getKeyCode() == S.getPlayer1right())
                xSpeed = 0;
        }
    }

    public void keyPressed(KeyEvent e) {
        if (owner.topPlayer) {
            if (e.getKeyCode() == S.getPlayer2left())
                xSpeed = -1 * speedMultiplier;
            if (e.getKeyCode() == S.getPlayer2right())
                xSpeed = 1 * speedMultiplier;
        } else {
            if (e.getKeyCode() == S.getPlayer1left())
                xSpeed = -1 * speedMultiplier;
            if (e.getKeyCode() == S.getPlayer1right())
                xSpeed = 1 * speedMultiplier;
        }
    }

    public void resetPosition() {
        this.xPosition = defaultPosition;
    }

    public Rectangle getBounds() {
        return new Rectangle(xPosition, yPosition, width, height);
    }

    public int getTopY() {
        return yPosition;
    }

    public int getBottomY() {
        return yPosition + height;
    }

    public int getxPosition() {
        return xPosition;
    }

    public void increaseWidth() {
        this.width += 30;
    }

    public void decreaseWidth() {
        this.width -= 30;
    }

    public void setXPosition() {
        this.xPosition = (1274 - width) / 2;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Block getPaddleBlock() {
        return new Block(xPosition, yPosition, width, height);
    }

    public int[] getSections() {
        int[] sections = new int[7];
        int sectionWidth = width / 7;
        for (int i = 1; i <= 6; i++) {
            sections[i] = sectionWidth * i;
        }
        return sections;
    }

    @Override
    public void Paint(Graphics2D g) {
        g.setColor(owner.color);
        g.fillRoundRect(xPosition, yPosition, width, height, 15, 15);
        g.setColor(Color.BLACK);
        g.drawRoundRect(xPosition, yPosition, width, height, 15, 15);
        g.drawRoundRect(xPosition + 1, yPosition + 1, width - 2, height - 2, 15, 15);
    }
}