import java.util.*;
import java.awt.*;
import java.lang.Thread;
import java.awt.event.*;
import javax.swing.*;

public class Ball{
    int posX, posY;
    int dirX = 1, dirY = 1;
    static final int width = 20, height = 20, speed = 5;

    Ball(){
        setPos();
    }

    public void updateBall(Platform platform1, Platform platform2){
        posX += speed * dirX;
        posY += speed * dirY;

        // Collision detection of the ball with the platforms
        if (posX < platform1.posX + platform1.width && posY + height > platform1.posY && posY < platform1.posY + platform1.height) {
            dirX = -dirX;
        }
        if (posX + width > platform2.posX && posY + height > platform2.posY && posY < platform2.posY + platform2.height) {
            dirX = -dirX;
        }

        // Collision detection of the ball with the vertical walls
        if (posX < 0) {
            //Game.setStatus(false);
            resetBall(1);
            platform1.setPos(true);
            platform2.setPos(false);
            platform2.score++;
            /*
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Game.setStatus(true);
            */
        }
        if (posX > Game.WIDTH - width) {
            //Game.setStatus(false);
            resetBall(-1);
            platform1.setPos(true);
            platform2.setPos(false);
            platform1.score++;
            /*
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Game.setStatus(true);
            */
        }
        if (posY < 0 || posY > Game.HEIGHT - height * 3) {
            dirY = -dirY;
        }
    }

    public void resetBall(int scoredDir){
        setPos();
        Random rand = new Random();
        dirX = scoredDir;
        dirY = rand.nextInt(2);
        if (dirY == 0)
            dirY = -1;
    }

    public void display(Graphics gfx1){
        gfx1.setColor(Color.WHITE);
        gfx1.fillOval(posX, posY, width, height);
    }

    public void setPos(){
        posX = Game.WIDTH/2 - width;
        posY = Game.HEIGHT/2 - height;
    }
}
