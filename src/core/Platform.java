package core;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Platform{
    public double posX, posY;
    public double sPosX;
    public final int width = 20, height = 100;
    public static final int maxScore = 5;
    public int score = 0;

    // Texture
    BufferedImage image;
    BufferedImage sprite;
    TexturePaint texture;
    TexturePaint texture2;

    public Platform(boolean playerA) throws IOException {
        setPos(playerA);
        image = ImageIO.read(new File("textures/sprites.png"));
        setTexture(playerA);
    }

    public void setTexture(boolean playerA){
        if (playerA) {
            sprite = image.getSubimage(15, 25, width, height);
            texture = new TexturePaint(sprite, new Rectangle(width, height));
        }
        else {
            sprite = image.getSubimage(65, 25, width, height);
            texture2 = new TexturePaint(sprite, new Rectangle(width, height));
        }
    }

    public void display(Graphics gfx1, boolean playerA){
        Graphics2D gfx2 = (Graphics2D) gfx1;
        if (playerA)
            gfx2.setPaint(texture);
        else
            gfx2.setPaint(texture2);
        //gfx1.fillRect((int)posX, (int)posY, width, height);
        Rectangle2D rect = new Rectangle2D.Double((int)posX, (int)posY, width, height);
        gfx2.fill(rect);
        drawScore(gfx1, playerA);

    }

    public void drawScore(Graphics gfx1, boolean playerA) {
        Font font = new Font("ARIAL", Font.PLAIN, 48);
        setScorePos(gfx1, playerA, font);
        gfx1.setFont(font);
        if (playerA)
            gfx1.setColor(Color.RED);
        else
            gfx1.setColor(Color.BLUE);
        gfx1.drawString(Integer.toString(score), (int)sPosX, 48);
    }

    public void setPos(boolean playerA){
        if (playerA)
            posX = 0;
        else
            posX = Game.WIDTH - width;
        posY = (double) Game.HEIGHT / 2 - (double) height / 2;
    }

    public void setScorePos(Graphics g, boolean playerA, Font font) {
        int strWidth = g.getFontMetrics(font).stringWidth(Integer.toString(score));
        if(playerA)
            sPosX = (double) Game.WIDTH /2 - strWidth * 2 - (double) Game.HEIGHT /8;
        else
            sPosX = (double) Game.WIDTH /2 + (double) Game.HEIGHT /8;
    }
}
