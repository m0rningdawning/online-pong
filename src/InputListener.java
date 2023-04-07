import java.awt.event.*;

public class InputListener extends KeyAdapter{
    private final int moveSpeed = 10;
    private Platform platform1;
    private Platform platform2;
    private boolean isWPressed = false;
    private boolean isSPressed = false;
    private boolean isUpPressed = false;
    private boolean isDownPressed = false;

    public void InputListener(Platform platform1, Platform platform2){
        this.platform1 = platform1;
        this.platform2 = platform2;
    }

    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
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
        }
        if (isSPressed && platform1.posY < Game.HEIGHT - platform1.height * 1.4) {
            platform1.posY += 10;
        }
        if (isUpPressed && platform2.posY > 0) {
            platform2.posY -= 10;
        }
        if (isDownPressed && platform2.posY < Game.HEIGHT - platform2.height * 1.4) {
            platform2.posY += 10;
        }
    }
}
