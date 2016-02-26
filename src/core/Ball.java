package core;

import java.awt.*;
import java.util.Random;

public class Ball implements Sprite {
    public Player owner;
    private int xPosition;
    private int yPosition;
    private int xSpeed = 2;
    private int ySpeed = 2;
    private int height = 15;
    private int diameter = 15;
    private Breakout game;
    private Block collidedBlock;
    private boolean stickToPaddle = true;

    public Ball(Breakout game, Player owner) {
        this.game = game;
        this.owner = owner;
        resetPosition();
    }

    public Ball(Player owner) {//for tests
        this.owner = owner;
        resetPosition();
    }

    public void resetPosition() {
        this.xPosition = owner.getPaddle().getxPosition() + owner.getPaddle().getWidth() / 2;
        if (owner.topPlayer) {
            this.yPosition = owner.getPaddle().getBottomY();
        } else {
            this.yPosition = owner.getPaddle().getTopY() - diameter;
        }
    }

    public void move() {

        if (stickToPaddle) {
            resetPosition();
            return;
        }

        if (xPosition + xSpeed < 0) //collide with left border
        {
            xSpeed *= -1;
            return;
        }
        if (xPosition + xSpeed > game.dimension.getWidth() - diameter) //collide with right border
        {
            xSpeed *= -1;
            return;
        }
        if (yPosition + ySpeed < 0) //collide with top border (no paddle)
        {
            ySpeed *= -1;
            if (game.twoPlayerMode) {
                //region play sound
                //Sound.playSound(getClass().getClassLoader().getResource("miss.wav").getFile());
                //endregion
                game.ballMiss();
            }
            return;
        }
        if (yPosition + ySpeed > game.dimension.getHeight() - diameter) //collide with bottom (no paddle)
        {
            ySpeed *= -1;
            //region play sound
            //Sound.playSound(getClass().getClassLoader().getResource("miss.wav").getFile());
            //endregion
            game.ballMiss();
            return;
        }
        if (collisionWithPaddle()) //collide with paddle
        {
            BounceOnBlock(collidedBlock.getBounds());

            //region play sound
            Sound.playSound(getClass().getClassLoader().getResource("paddle.wav").getPath());
            //endregion

            ChangeSpeedOnPaddle();

            for (Player p : game.players) {
                if (p.getPaddle().getBounds().intersects(getBounds())) {
                    if (p.topPlayer) {
                        yPosition = collidedBlock.getBounds().y + collidedBlock.getBounds().height;
                        System.out.println("Collision with top paddle");
                    } else {
                        yPosition = collidedBlock.getBounds().y - diameter;
                        System.out.println("Collision with bottom paddle");
                    }
                }
            }
        }
        if (CollisionWithBlocks()) //collide with a target block
        {
            BounceOnBlock(collidedBlock.getBounds());
        }

        //		handig om te debuggen
        if (false) {
            xPosition = MouseInfo.getPointerInfo().getLocation().x;
            yPosition = MouseInfo.getPointerInfo().getLocation().y;
        } else {
            xPosition += xSpeed;
            yPosition += ySpeed;
        }

    }

    private void ChangeSpeedOnPaddle() {

        //region paddle sections ophalen
        int[] paddleSections = null;
        for (Player p : game.players) {
            if (p.getPaddle().getBounds().intersects(getBounds())) {
                paddleSections = p.getPaddle().getSections();
            }
        }
        //endregion

        //region xSpeed aanpassen
        int multiplier = -3;
        int xPositionOnPaddle = xPosition - owner.getPaddle().getxPosition();
        for (int i : paddleSections) {
            if (xPositionOnPaddle <= i) {
                xSpeed = multiplier;
                return;
            }
            multiplier += 1;
            if (multiplier == 0) {
                multiplier += 1;
            }
        }
        //endregion
    }

    private boolean bottomCollision(Rectangle block, Rectangle bounds) {

        ExtendedRectangle ball = new ExtendedRectangle(bounds);

        return ((block.contains(ball.topLeft()) || block.contains(ball.topRight())) && !block.contains(ball.bottomLeft()) && !block.contains(ball.BottomRight()));
    }

    private boolean topCollision(Rectangle block, Rectangle bounds) {

        ExtendedRectangle ball = new ExtendedRectangle(bounds);

        return ((block.contains(ball.bottomLeft()) || block.contains(ball.BottomRight())) && !block.contains(ball.topLeft()) && !block.contains(ball.topRight()));
    }

    private boolean rightSideCollision(Rectangle block, Rectangle bounds) {

        ExtendedRectangle ball = new ExtendedRectangle(bounds);

        return ((block.contains(ball.topLeft()) || block.contains(ball.bottomLeft())) && !block.contains(ball.topRight()) && !block.contains(ball.BottomRight()));
    }

    private boolean leftSideCollision(Rectangle block, Rectangle bounds) {

        ExtendedRectangle ball = new ExtendedRectangle(bounds);

        return ((block.contains(ball.topRight()) || block.contains(ball.BottomRight())) && !block.contains(ball.topLeft()) && !block.contains(ball.bottomLeft()));
    }

    private void BounceOnBlock(Rectangle block) {    //find where collision happens

        Rectangle ball = getBounds();

        if (leftSideCollision(block, ball)) {
            System.out.println("left side collision");
            xSpeed *= -1;
            return;
        }

        if (rightSideCollision(block, ball)) {
            System.out.println("right side collision");
            xSpeed *= -1;
            return;
        }

        if (bottomCollision(block, ball)) {
            System.out.println("Bottom collision");
            ySpeed *= -1;
            return;
        }

        if (topCollision(block, ball)) {
            System.out.println("top collision");
            ySpeed *= -1;
            return;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(getxPosition(), yPosition, diameter, height);
    }

    private boolean collisionWithPaddle() {
        for (Player p : game.players) {
            if (p.getPaddle().getBounds().intersects(getBounds())) {
                collidedBlock = p.getPaddle().getPaddleBlock();
                return true;
            }
        }
        return false;
    }

    private boolean CollisionWithBlocks() {

        if (game.getTargets().isEmpty()) {
            game.nextLevel();
            return false;
        }

        for (Block b : game.getTargets()) {
            if (b.collision(getBounds())) {
                collidedBlock = b;
                //region play sound
                Random rand = new Random();
                int soundNr = rand.nextInt((4 - 1) + 1) + 1;
                Sound.playSound(getClass().getClassLoader().getResource(soundNr + ".wav").getFile());
                //endregion
                if (collidedBlock.density > 1) {
                    collidedBlock.density--;
                    return true;

                } else {
                    collidedBlock.getPower().activatePower(owner);
                    owner.addScore(collidedBlock.getScore());
                    game.getTargets().remove(collidedBlock);
                    game.notifyObservers();
                    return true;
                }
            }
        }
        return false;
    }

    public int getxPosition() {
        return xPosition;
    }


    public void stickToPaddle(boolean stick) {
        this.stickToPaddle = stick;
    }

    @Override
    public void Paint(Graphics2D g) {
        g.setColor(owner.color);
        g.fillOval(getxPosition(), yPosition, diameter, height);
        g.setColor(Color.WHITE);
        g.drawOval(getxPosition(), yPosition, diameter, height);
        g.drawOval(getxPosition() + 1, yPosition + 1, diameter - 2, height - 2);
    }

    public void increaseSpeed() {
        if (xSpeed > 0) {
            xSpeed++;
        } else {
            xSpeed--;
        }
        if (ySpeed > 0) {
            ySpeed++;
        } else {
            ySpeed--;
        }
    }
}