package core;

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
    public boolean player1Ready = false;
    public boolean player2Ready = false;

    // Online status
    public boolean isOnline = false;
    public boolean isServer = false;

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

    // Server and client
    Server server;
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
        listener.InputListener(platform1, platform2, this);
        this.addKeyListener(listener);

        // Thread
        thread = new Thread(this);
        startThread();

        // UDP/IP Test
        initNetwork();
    }

    // UDP/IP Test

    public void initNetwork() throws IOException {
        if (JOptionPane.showConfirmDialog(this, "Do you want to play online?", "Choose", JOptionPane.YES_NO_OPTION) == 0) {
            if (JOptionPane.showConfirmDialog(this, "Do you want to run the server?", "Choose", JOptionPane.YES_NO_OPTION) == 0) {
                if (JOptionPane.showConfirmDialog(this, "Do you want to run a public server?", "Choose", JOptionPane.YES_NO_OPTION) == 0) {
                    String port = JOptionPane.showInputDialog(this, "Please enter the server port(49152 - 65535): ");
                    setupServerAndClient(Integer.parseInt(port), true);
                    JOptionPane.showMessageDialog(this, "Ip address(public): " + server.checkPubIp() + "\nPort: " + port);
                    isOnline = true;
                } else {
                    String port = JOptionPane.showInputDialog(this, "Please enter the server port(49152 - 65535): ");
                    setupServerAndClient(Integer.parseInt(port), false);
                    JOptionPane.showMessageDialog(this, "Ip address(private): " + server.checkIps() + "\nPort: " + port);
                    isOnline = true;
                }
            } else {
                String port = JOptionPane.showInputDialog(this, "Please enter the server port: ");
                String ip = JOptionPane.showInputDialog(this, "Please enter the server IP: ");
                setupClient(ip, port);
                isOnline = true;
            }
        } else {
            isOnline = false;
        }
    }

    public synchronized void setupServerAndClient(int port, boolean global) throws IOException {
        server = new Server(this, port, global);
        server.start();
        isServer = true;
        client = new Client(InetAddress.getLoopbackAddress().getHostName(), port);
        client.start();
        sendData(0);
    }

    public synchronized void setupClient(String ip, String port) throws IOException {
        client = new Client(ip, Integer.parseInt(port));
        client.start();
        sendData(0);
    }

    public void sendData(int type){
        switch (type){
            case 0:
                client.sendData("connect".getBytes());
                break;
            case 1:
                client.sendData("move1up".getBytes());
                break;
            case 2:
                client.sendData("move1down".getBytes());
                break;
            case 3:
                client.sendData("p1ready".getBytes());
                break;
            case 4:
                client.sendData("move2up".getBytes());
                break;
            case 5:
                client.sendData("move2down".getBytes());
                break;
            case 6:
                client.sendData("p2ready".getBytes());
                break;
            case 7:
                client.sendData("moveball".getBytes());
                break;
            case 8:
                client.sendData("disconnect".getBytes());
                break;
        }
    }
    /*
    public void dropServer() throws IOException {
        client.close();
    }
    */
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
                    ball.updateBall(this, platform1, platform2);
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
