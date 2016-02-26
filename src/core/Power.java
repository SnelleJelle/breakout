package core;

public class Power {

    public static Power NONE = new Power(0);
    public static Power ADD_LIFE = new Power(1);
    public static Power INCREASE_PADDLE_LENGTH = new Power(2);
    public static Power STICKY_PADDLE = new Power(3);
    public static Power DECREASE_PADDLE_LENGTH = new Power(4);
    public static Power REMOVE_LIFE = new Power(5);
    public static Power FASTER_BALL = new Power(6);

    private Breakout game;
    private int powerNr;

    public Power(int powerNr, Breakout game) {
        this.game = game;
        this.powerNr = powerNr;
    }

    public Power(int power) {
        powerNr = power;
    }

    public void activatePower(Player player) {
        switch (powerNr) {//ups
            case 1:
                addLife();
                break;
            case 2:
                increasePaddleLength(player);
                break;
            case 3:
                StickyPaddle(player);
                break;
            case 4: //downs
                decreasePaddleLength(player);
                break;
            case 5:
                removeLife();
                break;
            case 6:
                fasterBall(player);
                break;
            default:
                break;
        }
    }

    public String toString() {
        String res;
        switch (powerNr) {//ups
            case 0:
                res = "no power";
            case 1:
                res = "addLife";
                break;
            case 2:
                res = "increasePaddleLength";
                break;
            case 3:
                res = "StickyPaddle";
                break;
            case 4: //downs
                res = "decreasePaddleLength";
                break;
            case 5:
                res = "removeLife";
                break;
            case 6:
                res = "fasterBall";
                break;
            default:
                res = "geen power";
                break;
        }
        return res;
    }

    private void addLife() {
        game.addLife();
        game.setLastUsedPowerName("Gained a life!");
    }

    private void increasePaddleLength(Player player) {
        player.getPaddle().increaseWidth();
        game.setLastUsedPowerName("Bigger paddle!");

    }

    private void StickyPaddle(Player player) {//WORKS
        player.getBall().stickToPaddle(true);
        game.setLastUsedPowerName("Sticky paddle activated!");
    }

    private void decreasePaddleLength(Player player) {
        player.getPaddle().decreaseWidth();
        game.setLastUsedPowerName("Smaller paddle!");
    }

    private void removeLife() {
        game.removeLife();
        game.setLastUsedPowerName("Lost a life!");
    }

    private void fasterBall(Player player) {
        player.getBall().increaseSpeed();
        game.setLastUsedPowerName("Faster ball!");
    }

    public int getPowerNr() {
        return powerNr;
    }
}
