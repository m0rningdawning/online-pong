import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Game extends JPanel implements Runnable{
    // Game dimensions
    static final int WIDTH = 800;
    static final int HEIGHT = 600;
    static final Dimension FIELD_SIZE = new Dimension(WIDTH, HEIGHT);
    // Objects
    Graphics gfx;
    Image image;
    Platform platform1;
    Platform platform2;
    Ball ball;
    // Thread
    Thread gameThread;
    // Game state
    boolean playStatus = false;
    Game(){
        //newBall();
        //newPlatforms();
        new Field();
        this.setFocusable(true);
        //gameThread = new Thread(this);
    }
    public void drawOnScreen(){
        Graphics gfx = getGraphics();
        gfx.setColor(Color.black);
        gfx.fillRect(0, 0, WIDTH, HEIGHT);
    }
    public void newBall(){
        ball = new Ball();
    }
    public void newPlatforms(){
        platform1 = new Platform();
        platform2 = new Platform();
    }
    @Override
    public void run() {

    }

    public static void main (String args[]){
        // Window frame
        Game pong = new Game();
    }
}
