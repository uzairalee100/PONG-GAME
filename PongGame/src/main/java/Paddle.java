import java.awt.*;
import java.awt.event.*;

public class Paddle extends Rectangle {
    int speed = 5;
    int upKey, downKey;
    boolean upPressed, downPressed;

    Paddle(int x, int y, int upKey, int downKey) {
        super(x, y, 20, 100);
        this.upKey = upKey;
        this.downKey = downKey;
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == upKey) upPressed = true;
        if (e.getKeyCode() == downKey) downPressed = true;
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == upKey) upPressed = false;
        if (e.getKeyCode() == downKey) downPressed = false;
    }

    public void move() {
        if (upPressed && y > 0) y -= speed;
        if (downPressed && y < GamePanel.HEIGHT - height) y += speed;
    }

    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(x, y, width, height);
    }
}
