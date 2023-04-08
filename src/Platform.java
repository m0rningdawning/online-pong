import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Platform{
    int posX, posY;
    static final int width = 20, height = 100;

    public Platform(boolean playerA){
        setPos(playerA);
    }

    public void display(Graphics gfx1, boolean playerA){
        if (playerA)
            gfx1.setColor(Color.blue);
        else
            gfx1.setColor(Color.yellow);
        gfx1.fillRect(posX, posY, width, height);
    }

    public void setPos(boolean playerA){
        if (playerA)
            posX = 0;
        else
            posX = Game.WIDTH - (int)(width * 1.5);
        posY = Game.HEIGHT/2 - height/2;
    }
}
