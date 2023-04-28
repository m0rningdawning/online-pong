import network.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.net.*;

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

    // Background Image with double-buffering
    BufferedImage bufferedImage;
    BufferedImage backgroundImage;

    // Objects
    Graphics gfx;
    Platform platform1;
    Platform platform2;
    Ball ball;
    InputListener listener;

    // Thread
    Thread thread;

    Client client;

    Game() throws IOException{
        newBall();
        newPlatforms();
        new Field(this);
        this.setFocusable(true);
        this.requestFocus();

        backgroundImage = ImageIO.read(new File("textures/background.png"));

        // Input listener
        listener = new InputListener();
        listener.InputListener(platform1, platform2);
        this.addKeyListener(listener);

        // Thread
        thread = new Thread(this);
        startThread();

        // UDP/IP Test
        testCanSendAndReceivePacket();

    }

    // UDP/IP Test

    public void setup() throws SocketException, UnknownHostException {
        new Server().start();
        client = new Client();
    }

    public void testCanSendAndReceivePacket() throws IOException {
        setup();
        String echo = client.sendEcho("hello server");
        System.out.println("Sent message: hello server");
        System.out.println("Received echo: " + echo);
        if(!"hello server".equals(echo)) {
            System.out.println("Test failed: Echo did not match sent message");
            throw new AssertionError();
        }
        echo = client.sendEcho("server is working");
        System.out.println("Sent message: server is working");
        System.out.println("Received echo: " + echo);
        if(echo.equals("hello server")) {
            System.out.println("Test failed: Echo matched previous message");
            throw new AssertionError();
        }
        tearDown();
    }

    public void tearDown() throws IOException {
        client.sendEcho("end");
        client.close();
    }

    public void startThread(){
        thread.start();
    }

    public void stopThread(){
        thread.interrupt();
    }

    public void newBall(){
        ball = new Ball();
    }

    public void newPlatforms(){
        platform1 = new Platform(true);
        platform2 = new Platform(false);
    }

    public void paint(Graphics g){
        bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        gfx = bufferedImage.getGraphics();
        display(gfx);
        g.drawImage(bufferedImage, 0, 0,this);
    }

    public void display(Graphics g){
        g.drawImage(backgroundImage, 0, 0, this);
        ball.display(g);
        platform1.display(g,true);
        platform2.display(g,false);
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

