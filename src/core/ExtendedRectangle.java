package core;

import java.awt.*;

public class ExtendedRectangle extends Rectangle {

    Rectangle r;

    public ExtendedRectangle(Rectangle r) {
        this.r = r;
    }

    public Point topLeft() {
        return new Point(r.x, r.y);
    }

    public Point topRight() {
        return new Point(r.x + r.width, r.y);
    }

    public Point bottomLeft() {
        return new Point(r.x, r.y + r.height);
    }

    public Point BottomRight() {
        return new Point(r.x + r.width, r.y + r.height);
    }
}
