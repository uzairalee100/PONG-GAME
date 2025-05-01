import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;
import javax.swing.border.Border;

public class GamePanel extends JPanel implements Runnable {

    static final int WIDTH = 800;
    static final int HEIGHT = 600;

    Thread gameThread;
    Paddle paddle1, paddle2;
    Ball ball;

    int player1Score = 0, player2Score = 0;
    int seconds = 0;
    Timer timer;
    boolean isPaused = false;

    JButton pauseButton = new JButton("Pause");
    JButton restartButton = new JButton("Restart");

    GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setFocusable(true);
        
        this.setLayout(null);

        // Place buttons at top-center
        pauseButton.setBounds(WIDTH / 2 - 100, 10, 90, 30);
        restartButton.setBounds(WIDTH / 2 + 10, 10, 90, 30);

        // Add buttons to panel
        this.add(pauseButton);
        this.add(restartButton);

        // Style the buttons
        pauseButton.setBackground(new Color(33, 150, 243));
        pauseButton.setForeground(Color.WHITE);
        pauseButton.setBorder((Border) new RoundedBorder(10));
        pauseButton.setFocusPainted(false);

        restartButton.setBackground(new Color(76, 175, 80));
        restartButton.setForeground(Color.WHITE);
        restartButton.setBorder((Border) new RoundedBorder(10));
        restartButton.setFocusPainted(false);

        
        
        this.setBackground(Color.black);

        paddle1 = new Paddle(0, HEIGHT / 2 - 50, KeyEvent.VK_W, KeyEvent.VK_S);
        paddle2 = new Paddle(WIDTH - 20, HEIGHT / 2 - 50, KeyEvent.VK_UP, KeyEvent.VK_DOWN);
        ball = new Ball(WIDTH / 2, HEIGHT / 2);

        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                paddle1.keyPressed(e);
                paddle2.keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                paddle1.keyReleased(e);
                paddle2.keyReleased(e);
            }
        });

        pauseButton.addActionListener(e -> isPaused = !isPaused);
        restartButton.addActionListener(e -> {
            player1Score = 0;
            player2Score = 0;
            seconds = 0;
            ball.resetPosition();
        });

        this.add(pauseButton);
        this.add(restartButton);

        timer = new Timer(1000, e -> seconds++);
        timer.start();

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);

        g.setColor(Color.white);
        g.setFont(new Font("Consolas", Font.BOLD, 40));
        Font scoreFont = new Font("Segoe UI", Font.BOLD, 36);
        g.setFont(scoreFont);
        g.setColor(Color.WHITE);
        g.drawString(player1Score + " : " + player2Score, WIDTH / 2 - 40, 90);

        g.setFont(new Font("Consolas", Font.PLAIN, 20));
        g.drawString("Time: " + seconds + "s", WIDTH - 150, 50);
    }

    public void move() {
        paddle1.move();
        paddle2.move();
        ball.move();
    }

    public void checkCollision() {
        if (ball.intersects(paddle1) || ball.intersects(paddle2)) {
            ball.reverseX();
            GameSound.playBounce();
        }

        if (ball.x <= 0) {
            player2Score++;
            ball.resetPosition();
        }

        if (ball.x >= WIDTH - ball.width) {
            player1Score++;
            ball.resetPosition();
        }
        
    }

    public void run() {
        while (true) {
            if (!isPaused) {
                move();
                checkCollision();
                repaint();
            }
            try {
                Thread.sleep(10);
            } catch (Exception e) {}
        }
    }
}
