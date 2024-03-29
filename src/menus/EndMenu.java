package menus;

import core.Game;
import resources.FontCreator;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;

public class EndMenu {
    //Game
    Game pong;

    // Title
    String playerAWonString = "Player A won!";
    String playerBWonString = "Player B won!";
    String subtitle = "READY FOR A REMATCH?";
    String pressSpace = "PRESS SPACE FOR A REMATCH!";

    // Buttons
    public Rectangle mainMenuButton = new Rectangle(Game.WIDTH / 2 - 75, 400, 150,45);
    public Rectangle quitButton = new Rectangle(Game.WIDTH / 2 - 45, 500, 90, 45);

    public EndMenu(Game pong){
        this.pong = pong;
        FontCreator.loadAndRegisterFont("resources/fonts/hello-denver-display.denver-display-regular-regular.ttf", true);
        FontCreator.loadAndRegisterFont("resources/fonts/Monoton-Regular.ttf", false);
    }

    public void playAudio() throws UnsupportedAudioFileException, IOException {
        pong.audioEffects.playEnd(true);
    }

    public int getWidth(Graphics g, Font font, String title){
        return g.getFontMetrics(font).stringWidth(title);
    }

    public void drawTitle(Graphics g, Font font, String title, Color color, int y){
        g.setFont(font);
        g.setColor(color);
        g.drawString(title, Game.WIDTH / 2 - getWidth(g, font, title) / 2, y);
    }

    public void drawButtons(Graphics2D g, Rectangle button, String title, boolean isQuit){
        g.draw(button);
        if (isQuit) {
            //g.setColor(Color.red);
            g.drawString(title, button.x + 20, button.y + 30);
        }
        else {
            //g.setColor(Color.yellow);
            g.drawString(title, button.x + 15, button.y + 30);
        }
    }

    public void display(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        Font font = FontCreator.monoton.deriveFont(80f);
        Font font2 = FontCreator.denver.deriveFont(25f);
        Font font3 = FontCreator.denver.deriveFont(40f);
        if (pong.isOnline){
            if (pong.isPlayerAWon){
                drawTitle(g, font, playerAWonString, Color.blue, 100);
                drawTitle(g, font2, subtitle, Color.yellow, 140);
            }
            if (pong.isPlayerBWon){
                drawTitle(g, font, playerBWonString, Color.yellow, 100);
                drawTitle(g, font2, subtitle, Color.blue, 140);
            }
            if (pong.player1Ready)
                drawTitle(g, font2, "READY", Color.green, 240);
            else
                drawTitle(g, font2, "READY", Color.red, 240);

            if (pong.player2Ready)
                drawTitle(g, font2, "READY", Color.green, 270);
            else
                drawTitle(g, font2, "READY", Color.red, 270);
        }
        else {
            if (pong.isPlayerAWon){
                drawTitle(g, font, playerAWonString, Color.blue, 100);
                drawTitle(g, font3, pressSpace, Color.red, 260);
                drawTitle(g, font2, subtitle, Color.yellow, 140);


            }
            if (pong.isPlayerBWon){
                drawTitle(g, font, playerBWonString, Color.yellow, 100);
                drawTitle(g, font3, pressSpace, Color.red, 260);
                drawTitle(g, font2, subtitle, Color.blue, 140);

            }
        }
        drawButtons(g2d, mainMenuButton, "MAIN MENU", false);
        drawButtons(g2d, quitButton, "QUIT", true);
    }
}
