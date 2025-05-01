import java.awt.*;

public class Ball extends Rectangle {
    int xVelocity = 3;
    int yVelocity = 3;

    Ball(int x, int y) {
        super(x, y, 20, 20);
    }

    public void move() {
        x += xVelocity;
        y += yVelocity;

        if (y <= 0 || y >= GamePanel.HEIGHT - height) {
            yVelocity = -yVelocity;
        }
    }

    public void reverseX() {
        xVelocity = -xVelocity; 
   }

    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.fillOval(x, y, width, height);
    }

    public void resetPosition() {
        x = GamePanel.WIDTH / 2;
        y = GamePanel.HEIGHT / 2;
    }
}
