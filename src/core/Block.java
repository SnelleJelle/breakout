package core;

import java.awt.*;

public class Block implements Sprite {

    public int density;
    private int xPosition;
    private int yPosition;
    private int width = 80;
    private int height = 35;
    private int score;
    private Color color;
    private Breakout game;
    private Power power;

    public Block(int x, int y, Color c, Breakout game, Power power, int dense) {
        this.xPosition = x;
        this.yPosition = y;
        this.color = c;
        this.game = game;
        this.setPower(power);
        this.density = dense;
    }

    public Block(int x, int y, int width, int height, Color color, int score, int density, Breakout game) {
        this.xPosition = x;
        this.yPosition = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.setScore(score);
        this.density = density;
        this.game = game;
        this.setPower(Power.NONE);
    }

    public Block(int x, int y, int width, int height) {
        this.xPosition = x;
        this.yPosition = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle getBounds() {
        return new Rectangle(xPosition, yPosition, width, height);
    }

    public boolean collision(Rectangle ball) {
        return ball.getBounds().intersects(getBounds());
    }

    public Color getColor() {
        return color;
    }

    public String toString() {
        return "x=" + xPosition + ", y=" + yPosition + ", width=" + width + ", height=" + height + ", color=R" + color.getRed() + " G" + color.getGreen() + " B" + color.getBlue() + ", score=" + getScore();
    }

    public Power getPower() {
        return power;
    }

    public void setPower(Power power) {
        this.power = power;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void moveDown(int down) {
        this.yPosition += down;
    }

    @Override
    public void Paint(Graphics2D g) {
        int colorDensity = density * 100;
        if (colorDensity > 255) {
            colorDensity = 255;
        }
        g.setColor(new Color(getColor().getRed(), getColor().getGreen(), getColor().getBlue(), colorDensity));
        g.fillRoundRect(xPosition, yPosition, width, height, 20, 20);
        if (getPower().getPowerNr() > 0) {
            g.setColor(new Color(255, 215, 0));
        } else {
            g.setColor(Color.WHITE);
        }
        g.drawRoundRect(xPosition, yPosition, width, height, 20, 20);
    }
}