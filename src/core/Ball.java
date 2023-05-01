package core;

import java.util.*;
import java.awt.*;
import java.lang.Thread;
import java.awt.event.*;
import javax.swing.*;

public class Ball{
    static final int width = 20, height = 20, startingSpeed = 8;
    public double posX, posY;
    double dirX, dirY, currentSpeed = startingSpeed;
    double dirLength;
    Game pong;

    Ball(Game pong){
        this.pong = pong;
        setPos();
        initializeDirection();
    }

    public void initializeDirection(){
        do {
            dirX = -1 + (Math.random() * 2);
        } while (dirX == 0);
        do {
            dirY = -1 + (Math.random() * 2);
        } while (dirY == 0);
    }

    public void setDir(boolean playerA){
        if (playerA)
            dirX = 0.5 + (Math.random() * 0.5);
        else
            dirX = -1.0 + (Math.random() * 0.5);
    }

    public void updateBall(Game pong, Platform platform1, Platform platform2){
        dirLength = Math.sqrt(dirX * dirX + dirY * dirY);

        if (pong.isOnline){
            if (pong.isServer){
                posX += currentSpeed * (dirX / dirLength);
                posY += currentSpeed * (dirY / dirLength);
                pong.sendBallData(posX, posY);
            }
        }
        else{
            posX += currentSpeed * (dirX / dirLength);
            posY += currentSpeed * (dirY / dirLength);
        }


        //posX += currentSpeed * dirX;
        //posY += currentSpeed * dirY;

        //posX += Math.max(-currentSpeed, Math.min(currentSpeed, currentSpeed * dirX));
        //posY += Math.max(-currentSpeed, Math.min(currentSpeed, currentSpeed * dirY));

        // Collision detection of the ball with the horizontal sides of the screen
        if (posY < 0 || posY > Game.HEIGHT - height * 3) {
            dirY = -dirY;
        }

        // Collision detection of the ball with platforms

        // Platform one
        if (posX < platform1.posX + platform1.width && posY + height > platform1.posY && posY < platform1.posY + platform1.height) {
            currentSpeed += 0.2;
            setDir(true);
        }

        // Platform two
        if (posX + width > platform2.posX && posY + height > platform2.posY && posY < platform2.posY + platform2.height) {
            currentSpeed += 0.2;
            setDir(false);
        }

        // Score registration
        if (posX < platform1.width - (double) width / 2) {
            resetBall(0.5 + (Math.random() * 0.5));
            platform1.setPos(true);
            platform2.setPos(false);
            platform2.score++;
            pong.player1Ready = pong.player2Ready = false;
        }

        if (posX > Game.WIDTH - width * 2) {
            resetBall(-1.0 + (Math.random() * 0.5));
            platform1.setPos(true);
            platform2.setPos(false);
            platform1.score++;
            pong.player1Ready = pong.player2Ready = false;
        }
    }

    public void resetBall(double scoredDir){
        setPos();
        initializeDirection();
        currentSpeed = startingSpeed;
        Random rand = new Random();
        dirX = scoredDir;
        dirY = rand.nextInt(2);
        if (dirY == 0)
            dirY = -1;
    }

    public void display(Graphics gfx1){
        gfx1.setColor(Color.WHITE);
        gfx1.fillOval((int)posX, (int)posY, width, height);
    }

    public void setPos(){
        posX = (double) Game.WIDTH /2 - width;
        posY = (double) Game.HEIGHT /2 - height;
    }
}
