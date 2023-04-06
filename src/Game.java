import java.awt.image.BufferedImage;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;

public class Game extends JPanel implements Runnable{
    // Game dimensions
    static final int WIDTH = 800;
    static final int HEIGHT = 600;
    static final Dimension FIELD_SIZE = new Dimension(WIDTH, HEIGHT);
    // Objects
    Graphics gfx;
    Image image;
    BufferedImage backgroundImage;
    Platform platform1;
    Platform platform2;
    Ball ball;
    // Thread
    Thread thread;
    // Game state
    boolean playStatus = false;
    Game() throws IOException{
        //newBall();
        newPlatforms();
        new Field(this);
        this.setFocusable(true);
        backgroundImage = ImageIO.read(new File("/home/paul/GITProjects/online-pong/textures/background.png"));
        thread = new Thread(this);
        thread.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        super.paint(g);
        g.drawImage(backgroundImage, 0, 0, this);
    }
    public void newBall(){
        ball = new Ball();
    }
    public void newPlatforms(){
        platform1 = new Platform(true);
        platform2 = new Platform(false);
    }
    public void paint(Graphics gfx1){
        image = createImage(getWidth(),getHeight());
        gfx = image.getGraphics();
        display(gfx);
        gfx1.drawImage(image,0,0,this);
    }
    public void display(Graphics gfx1){
        //ball.display(gfx);
        platform1.display(gfx1,true);
        platform2.display(gfx1,false);
    ;
    }
    // Main game loop
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double maxFPS = 60.0;
        double frameTime = 1000000000/maxFPS;
        double delta = 0;
        while(true) {
            long now = System.nanoTime();
            delta += (now - lastTime)/frameTime;
            lastTime = now;
            if(delta >= 1){
                repaint();
                delta--;
                System.out.println("Running!");
            }
        }
    }

    public static void main (String args[]) throws IOException{
        // Window frame
            Game pong = new Game();
    }
}
