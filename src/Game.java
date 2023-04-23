import java.awt.image.BufferedImage;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;

public class Game extends JPanel implements Runnable {

    // Game dimensions
    static final int WIDTH = 800;
    static final int HEIGHT = 600;
    static final Dimension FIELD_SIZE = new Dimension(WIDTH, HEIGHT);

    // Game state
    static boolean playStatus = true;

    // Player readiness
    static boolean player1Ready = false;
    static boolean player2Ready = false;

    // Objects
    BufferedImage backgroundImage;
    Platform platform1;
    Platform platform2;
    Ball ball;
    InputListener listener;
    Image image;

    // Thread
    Thread thread;

    Game() throws IOException {
        newBall();
        newPlatforms();
        new Field(this);
        this.setFocusable(true);

        backgroundImage = ImageIO.read(new File("textures/background.png"));

        // Input listener
        listener = new InputListener();
        listener.InputListener(platform1, platform2);
        this.addKeyListener(listener);

        // Thread
        thread = new Thread(this);
        startThread();
    }

    public void startThread() {
        thread.start();
    }

    public void stopThread() {
        thread.interrupt();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this);
        ball.display(g);
        platform1.display(g, true);
        platform2.display(g, false);
    }

    public void newBall() {
        ball = new Ball();
    }

    public void newPlatforms() {
        platform1 = new Platform(true);
        platform2 = new Platform(false);
    }

    // Main game loop
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double maxFPS = 60.0;
        double frameTime = 1000000000 / maxFPS;
        double delta = 0;
        long now;
        while (playStatus) {
            while (!player1Ready || !player2Ready) {
                now = System.nanoTime();
                delta += (now - lastTime) / frameTime;
                lastTime = now;
                repaint();
                if (delta >= 1) {
                    //repaint();
                    listener.keyPressed(new KeyEvent(this, 0, 0, 0, 0, ' '));
                    delta--;
                }
            }
            while (player1Ready && player2Ready) {
                now = System.nanoTime();
                delta += (now - lastTime) / frameTime;
                lastTime = now;
                if (delta >= 1) {
                    ball.updateBall(platform1, platform2);
                    listener.updatePlatforms();
                    repaint();
                    delta--;
                }
            }
        }
    }

    public static void main(String args[]) throws IOException {
        Game pong = new Game();
    }
}

