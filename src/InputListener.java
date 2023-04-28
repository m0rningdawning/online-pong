import network.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;


public class InputListener extends KeyAdapter{
    private Platform platform1;
    private Platform platform2;
    private Game pong;
    static boolean isWPressed = false;
    static boolean isSPressed = false;
    static boolean isUpPressed = false;
    static boolean isDownPressed = false;


    public void InputListener(Platform platform1, Platform platform2, Game pong) throws SocketException, UnknownHostException {
        this.platform1 = platform1;
        this.platform2 = platform2;
        this.pong = pong;
        //pong.setupServer();
    }

    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ESCAPE){
            System.exit(0);
            try {
                pong.dropServer();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (key == KeyEvent.VK_SPACE) {
            Game.player1Ready = Game.player2Ready = true;
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
        if (isWPressed && platform1.posY > 0) {
            platform1.posY -= 10;
            try {
                pong.testCanSendAndReceivePacket();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (isSPressed && platform1.posY < Game.HEIGHT - platform1.height * 1.4) {
            platform1.posY += 10;
            try {
                pong.testCanSendAndReceivePacket();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (isUpPressed && platform2.posY > 0) {
            platform2.posY -= 10;
            try {
                pong.testCanSendAndReceivePacket();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (isDownPressed && platform2.posY < Game.HEIGHT - platform2.height * 1.4) {
            platform2.posY += 10;
            try {
                pong.testCanSendAndReceivePacket();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
