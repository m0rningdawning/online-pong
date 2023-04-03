import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Platform{
    int posX, posY;
    int width = 30, height = 120;
    Platform(boolean playerA){
        if (playerA)
            posX = 0;
        else
            posX = Game.WIDTH - width;
        posY = Game.HEIGHT/2 - height/2;
    }
    public void display(Graphics gfx1, boolean playerA){
        if (playerA)
            gfx1.setColor(Color.blue);
        else
            gfx1.setColor(Color.yellow);
        gfx1.fillRect(posX, posY, width, height);
    }
    public void setPos(){

    }
}
