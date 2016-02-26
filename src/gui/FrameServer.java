package gui;

import java.util.ArrayList;
import java.util.List;

public class FrameServer {

    public final static int MODE_MENU = 0;
    public final static int MODE_GAME = 1;
    public final static int MODE_HIGHSCORES = 2;
    public final static int MODE_SETTINGS = 3;

    private int currentState;
    private List<FrameObserver> observers;

    public FrameServer() {
        observers = new ArrayList<FrameObserver>();
        currentState = MODE_MENU;
    }

    public void addObserver(FrameObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(FrameObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (FrameObserver observer : observers) {
            observer.update();
        }
    }

    public void changeContentPaneTo(int state) {
        currentState = state;
        notifyObservers();
    }

    public int getCurrentstate() {
        return currentState;
    }
}
