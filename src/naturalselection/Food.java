package naturalselection;

import java.awt.*;

public class Food implements Drawable {
    private final Vector position;
    public static final int FOOD_VALUE = 50;

    public Food(int x, int y) {
        position = new Vector(x, y);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillOval((int) position.x, (int) position.y, 2, 2);
    }

    @Override
    public Rectangle getHitbox() {
        return new Rectangle((int) position.x, (int) position.y, 5, 5);
    }

    @Override
    public Vector getPosition() {
        return position;
    }
}
