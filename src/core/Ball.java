package core;

import data.Stats;

import java.io.IOException;
import java.util.*;
import java.awt.*;

public class Ball{
    static final int width = 20, height = 20, startingSpeed = 8;
    public double posX, posY;
    double dirX, dirY, currentSpeed = startingSpeed;
    double dirLength;
    Game pong;
    Stats stats;

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

    public void endRound(boolean playerAWon) throws IOException {
        pong.handleStats(new int[]{pong.platform1.score, pong.platform2.score}, false, false);
        setPos();
        initializeDirection();
        currentSpeed = startingSpeed;
        pong.platform1.score = pong.platform2.score = 0;
        pong.roundCount++;
        // Call end menu
    }

    public void endOnlineRound(boolean playerAWon, boolean initial) throws IOException {
        pong.handleStats(new int[]{pong.platform1.score, pong.platform2.score}, false, false);

        //if (playerAWon)
        // Send reset packet to server
        //else
        // Send reset packet to server

        // Call end menu
        setPos();
        initializeDirection();
        currentSpeed = startingSpeed;
        pong.platform1.score = pong.platform2.score = 0;
    }

    public void updateBall(Game pong) throws IOException {
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

        // Collision detection of the ball with the horizontal sides of the screen
        if (posY < 0 || posY > Game.HEIGHT - height * 3) {
            dirY = -dirY;
        }

        // Collision detection of the ball with platforms

        // Platform one
        if (posX < pong.platform1.posX + pong.platform1.width && posY + height > pong.platform1.posY && posY < pong.platform1.posY + pong.platform1.height) {
            currentSpeed += 0.2;
            setDir(true);
        }

        // Platform two
        if (posX + width > pong.platform2.posX && posY + height > pong.platform2.posY && posY < pong.platform2.posY + pong.platform2.height) {
            currentSpeed += 0.2;
            setDir(false);
        }

        // Score registration
        if (posX < pong.platform1.width - (double) width / 2) {
            resetBall(0.5 + (Math.random() * 0.5));
            pong.platform1.setPos(true);
            pong.platform2.setPos(false);
            pong.platform2.score++;
            if (pong.platform2.score == Platform.maxScore)
                if (pong.isOnline)
                    endOnlineRound(false, true);
                else
                    endRound(false);
            pong.player1Ready = pong.player2Ready = false;
        }

        if (posX > Game.WIDTH - width * 2) {
            resetBall(-1.0 + (Math.random() * 0.5));
            pong.platform1.setPos(true);
            pong.platform2.setPos(false);
            pong.platform1.score++;
            if (pong.platform1.score == Platform.maxScore)
                if (pong.isOnline)
                    endOnlineRound(true, true);
                else
                    endRound(true);
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
