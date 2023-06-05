package naturalselection;

import java.awt.*;

public interface Drawable {
    void update();
    void draw(Graphics2D g);
    Rectangle getHitbox();

    Vector getPosition();
}
