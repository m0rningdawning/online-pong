package core;

import java.awt.event.*;


public class InputListener extends KeyAdapter{
    private Platform platform1;
    private Platform platform2;
    private Game pong;
    static boolean isWPressed = false;
    static boolean isSPressed = false;
    static boolean isUpPressed = false;
    static boolean isDownPressed = false;


    public InputListener(Platform platform1, Platform platform2, Game pong) {
        this.platform1 = platform1;
        this.platform2 = platform2;
        this.pong = pong;
        //pong.setupServer();
    }

    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ESCAPE){
            if (pong.isOnline)
                pong.sendData(7);
            if (pong.stats != null)
                pong.stats.prepareStats(true);
            System.exit(0);
        }
        if (key == KeyEvent.VK_SPACE) {
            if (pong.isOnline) {
                if (pong.isServer) {
                    pong.player1Ready = true;
                    pong.sendData(3);
                }
                else {
                    pong.player2Ready = true;
                    pong.sendData(6);
                }
            }
            else
                pong.player1Ready = pong.player2Ready = true;
        }
        if (key == KeyEvent.VK_W){
            isWPressed = true;
        }
        if (key == KeyEvent.VK_S){
            isSPressed = true;
        }
        if (key == KeyEvent.VK_UP){
            isUpPressed = true;
        }
        if (key == KeyEvent.VK_DOWN){
            isDownPressed = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W) {
            isWPressed = false;
        }
        if (key == KeyEvent.VK_S) {
            isSPressed = false;
        }
        if (key == KeyEvent.VK_UP) {
            isUpPressed = false;
        }
        if (key == KeyEvent.VK_DOWN) {
            isDownPressed = false;
        }
    }

    public void updatePlatforms() {
        if (isWPressed) {
            if (pong.isOnline) {
                if (pong.isServer && platform1.posY > 0) {
                    platform1.posY -= 10;
                    pong.sendData(1);
                }
                else if (!pong.isServer && platform2.posY > 0){
                    platform2.posY -= 10;
                    pong.sendData(4);
                }
            } else if (platform1.posY > 0)
                platform1.posY -= 10;
        }
        if (isSPressed) {
            if (pong.isOnline) {
                if (pong.isServer && platform1.posY < Game.HEIGHT - platform1.height * 1.4){
                    platform1.posY += 10;
                    pong.sendData(2);
                }
                else if (!pong.isServer && platform2.posY < Game.HEIGHT - platform2.height * 1.4){
                    platform2.posY += 10;
                    pong.sendData(5);
                }
            } else if (platform1.posY < Game.HEIGHT - platform1.height * 1.4)
                platform1.posY += 10;
        }
        if (isUpPressed) {
            if (pong.isOnline) {
                if (pong.isServer && platform1.posY > 0) {
                    platform1.posY -= 10;
                    pong.sendData(1);
                }
                else if (!pong.isServer && platform2.posY > 0){
                    platform2.posY -= 10;
                    pong.sendData(4);
                }
            } else if (platform2.posY > 0)
                platform2.posY -= 10;
        }
        if (isDownPressed) {
            if (pong.isOnline) {
                if (pong.isServer && platform1.posY < Game.HEIGHT - platform1.height * 1.4){
                    platform1.posY += 10;
                    pong.sendData(2);
                }
                else if (!pong.isServer && platform2.posY < Game.HEIGHT - platform2.height * 1.4){
                    platform2.posY += 10;
                    pong.sendData(5);
                }
            } else if (platform2.posY < Game.HEIGHT - platform1.height * 1.4)
                platform2.posY += 10;
        }
    }
}
