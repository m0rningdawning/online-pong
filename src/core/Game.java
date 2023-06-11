package core;

/*
TODO
- Finish the end menu: final score and readiness
- Fix json
- Find bugs to fix
 */

import data.Stats;
import menus.*;
import network.*;
import resources.AudioEffects;
import resources.FontCreator;

import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.net.*;
import java.io.File;
import java.io.FileWriter;

public class Game extends JPanel implements Runnable {

    // Game dimensions
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    static final Dimension FIELD_SIZE = new Dimension(WIDTH, HEIGHT);

    // Game state
    static boolean playStatus = false;
    public boolean isRoundEnd = false;

    // Round count
    public int roundCount = 1;

    // Player status
    public boolean player1Ready = false;
    public boolean player2Ready = false;
    public boolean isPlayerAWon = false;
    public boolean isPlayerBWon = false;

    // Online status
    public boolean isOnline = false;
    public boolean isServer = false;

    // Background Image with double-buffering
    BufferedImage bufferedImage;
    BufferedImage backgroundImage;

    // Objects
    Graphics gfx;
    public Stats stats;
    public Platform platform1;
    public Platform platform2;
    public Ball ball;
    public KeyboardInput listener;
    public MouseInput mouseListener;
    public MainMenu mMenu;
    public EndMenu eMenu;
    public AudioEffects audioEffects;

    // Thread
    Thread thread;

    // Server and client
    Server server;
    Client client;

    public Game() throws IOException, UnsupportedAudioFileException {
        // Menus
        mMenu = new MainMenu();
        eMenu = new EndMenu(this);

        // Game objects
        newBall();
        newPlatforms();
        new Field(this);
        this.setFocusable(true);
        this.requestFocus();

        // Background image
        backgroundImage = ImageIO.read(new File("resources/textures/background.png"));

        // Sfx
        audioEffects = new AudioEffects();

        // Input listeners
        listener = new KeyboardInput(platform1, platform2, this);
        mouseListener = new MouseInput(mMenu, eMenu,this);

        this.addKeyListener(listener);
        this.addMouseListener(mouseListener);

        // Thread
        thread = new Thread(this);
        startThread();
    }
    // Network and stats setup
    public void initNetwork() throws IOException {
            clearStats(true);
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
    }

    public void controlsExplanation(){
        if (isOnline){
            JOptionPane.showMessageDialog(this, "Controls: W - up, S - down\nReady: Space");
        }
        else
            JOptionPane.showMessageDialog(this, "Player 1 controls: W - up, S - down\nPlayer 2 controls: UP - up, DOWN - down\nReady: Space");
    }

    public synchronized void setupServerAndClient(int port, boolean global) throws IOException {
        server = new Server(this, port, global);
        server.start();
        isServer = true;
        client = new Client(InetAddress.getLoopbackAddress().getHostName(), port, this);
        client.start();
        sendData(0);
    }

    public synchronized void setupClient(String ip, String port) {
        client = new Client(ip, Integer.parseInt(port), this);
        client.start();
        sendData(0);
    }

    // Data sending hub
    public void sendData(int type){
        switch (type) {
            case 0 -> client.sendData("connect".getBytes());
            case 1 -> client.sendData("move1up".getBytes());
            case 2 -> client.sendData("move1down".getBytes());
            case 3 -> client.sendData("p1ready".getBytes());
            case 4 -> client.sendData("move2up".getBytes());
            case 5 -> client.sendData("move2down".getBytes());
            case 6 -> client.sendData("p2ready".getBytes());
            case 7 -> client.sendData("disconnect".getBytes());
        }
    }

    public void sendBallData(double posX, double posY){
        client.sendData(("ball:" + posX + ":" + posY).getBytes());
    }

    // Stats handling
    public void handleStats(int [] playerScores, boolean eof) {
        if(isOnline){
            stats = new Stats(this, null, playerScores, roundCount, client.port);
            stats.prepareStats(false);
            stats.handleOnlineStats();
        } else {
            stats = new Stats(this, null, playerScores, roundCount, -1);
            stats.prepareStats(false);
            stats.handleStats();
        }
        if (eof) {
            stats.prepareStats(true);
        }
    }

    public void clearStats(boolean isOnline) throws IOException {
        FileWriter writer;
        if (isOnline)
            writer = new FileWriter("stats/onlineStats.json", false);
        else
            writer = new FileWriter("stats/offlineStats.json", false);
        writer.write("");
        writer.close();
    }

    public void startThread(){
        thread.start();
    }

    public void stopThread(){
        thread.interrupt();
    }

    public void newBall() throws IOException {
        ball = new Ball(this);
    }

    public void newPlatforms() throws IOException {
        platform1 = new Platform(true);
        platform2 = new Platform(false);
    }

    public void paint(Graphics g){
        bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        gfx = bufferedImage.getGraphics();
        try {
            display(gfx);
        } catch (UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
        g.drawImage(bufferedImage, 0, 0,this);
    }

    public void display(Graphics g) throws UnsupportedAudioFileException, IOException {
        g.drawImage(backgroundImage, 0, 0, this);
        if (playStatus && !isRoundEnd){
            ball.display(g);
            platform1.display(g,true);
            platform2.display(g,false);
        }
        else if (isRoundEnd) {
            eMenu.display(g);
            if (player1Ready && player2Ready)
                isRoundEnd = false;
        }
        else if (!playStatus)
            mMenu.display(g);
    }

    // Main game loop
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double maxFPS = 60.0;
        double frameTime = 1000000000 / maxFPS;
        double delta = 0;
        long now;
        while (!playStatus){
            repaint();
        }
        while (playStatus) {
            while (!player1Ready || !player2Ready) {
                now = System.nanoTime();
                delta += (now - lastTime) / frameTime;
                lastTime = now;
                repaint();
                if (delta >= 1) {
                    repaint();
                    listener.keyPressed(new KeyEvent(this, 0, 0, 0, 0, ' '));
                    delta--;
                }
            }
            while (player1Ready && player2Ready) {
                now = System.nanoTime();
                delta += (now - lastTime) / frameTime;
                lastTime = now;
                if (delta >= 1) {
                    try {
                        ball.updateBall(this);
                    } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
                        throw new RuntimeException(e);
                    }
                    listener.updatePlatforms();
                    repaint();
                    delta--;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException, UnsupportedAudioFileException {
        Game pong = new Game();
    }
}

