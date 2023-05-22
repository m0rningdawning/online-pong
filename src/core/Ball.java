package core;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.awt.*;
import javax.imageio.*;

public class Ball{
    static final int width = 20, height = 20, startingSpeed = 8;
    public double posX, posY;
    public double dirX, dirY, currentSpeed = startingSpeed;
    double dirLength;
    Game pong;

    // Texture
    BufferedImage image;
    BufferedImage sprite;
    TexturePaint texture;


    public Ball(Game pong) throws IOException {
        this.pong = pong;
        image = ImageIO.read(new File("textures/sprites.png"));
        setTexture();
        setPos();
        initializeDirection();
    }

    public void setTexture(){
        sprite = image.getSubimage(115, 15, width, height);
        texture = new TexturePaint(sprite, new Rectangle(width, height));
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

    public void endRound(boolean playerAWon) {
        pong.handleStats(new int[]{pong.platform1.score, pong.platform2.score}, false);
        setPos();
        initializeDirection();
        currentSpeed = startingSpeed;
        pong.platform1.score = pong.platform2.score = 0;
        pong.roundCount++;
        pong.isRoundEnd = true;
        if (playerAWon) {
            pong.isPlayerAWon = true;
            pong.isPlayerBWon = false;
        }
        else {
            pong.isPlayerBWon = true;
            pong.isPlayerAWon = false;
        }
//        if (pong.isOnline)
//
//        else

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
        if (posY < 0 || posY > Game.HEIGHT - height) {
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
                endRound(false);
            pong.player1Ready = pong.player2Ready = false;
        }

        if (posX > Game.WIDTH - (2 * pong.platform2.width - (double) pong.platform2.width / 2)) {
            resetBall(-1.0 + (Math.random() * 0.5));
            pong.platform1.setPos(true);
            pong.platform2.setPos(false);
            pong.platform1.score++;
            if (pong.platform1.score == Platform.maxScore)
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
        Graphics2D gfx2 = (Graphics2D) gfx1;
        gfx2.setPaint(texture);

        Ellipse2D oval = new Ellipse2D.Double((int)posX, (int)posY, width, height);
        gfx2.fill(oval);
//        gfx1.setColor(Color.WHITE);
//        gfx1.fillOval((int)posX, (int)posY, width, height);
    }

    public void setPos(){
        posX = (double) Game.WIDTH / 2 - (double) width / 2;
        posY = (double) Game.HEIGHT /2 - (double) height / 2;
    }
}
