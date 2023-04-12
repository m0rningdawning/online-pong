import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Platform{
    int posX, posY;
    int sPosX;
    static final int width = 20, height = 100;
    int score = 0;

    public Platform(boolean playerA){
        setPos(playerA);
    }

    public void display(Graphics gfx1, boolean playerA){
        if (playerA)
            gfx1.setColor(Color.blue);
        else
            gfx1.setColor(Color.yellow);
        gfx1.fillRect(posX, posY, width, height);
        drawScore(gfx1, playerA);
    }

    public void drawScore(Graphics gfx1, boolean playerA) {
        Font font = new Font("ROBOTO", Font.PLAIN, 50);
        setScorePos(gfx1, playerA, font);
        gfx1.setFont(font);
        gfx1.drawString(Integer.toString(score), sPosX, 50);
    }

    public void setPos(boolean playerA){
        if (playerA)
            posX = 0;
        else
            posX = Game.WIDTH - (int)(width * 1.5);
        posY = Game.HEIGHT/2 - height/2;
    }

    public void setScorePos(Graphics g, boolean playerA, Font font) {
        int strWidth = g.getFontMetrics(font).stringWidth(Integer.toString(score));
        if(playerA)
            sPosX = Game.WIDTH/2 - strWidth - Game.HEIGHT/8;
        else
            sPosX = Game.WIDTH/2 + Game.HEIGHT/8;
    }
}
