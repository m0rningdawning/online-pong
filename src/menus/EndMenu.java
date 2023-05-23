package menus;

import core.Game;
import java.awt.*;

public class EndMenu {
    //Game
    Game pong;

    // Title
    String playerAWonString = "Player A won!";
    String playerBWonString = "Player B won!";
    String subtitle = "Ready for a rematch?";
    String pressSpace = "Press SPACE for a rematch";
    //private boolean playerAWon;

    // Buttons
    public Rectangle mainMenuButton = new Rectangle(Game.WIDTH / 2 - 75, 350, 150,45);
    public Rectangle quitButton = new Rectangle(Game.WIDTH / 2 - 45, 450, 90, 45);

    public EndMenu(Game pong){
        this.pong = pong;
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
        Font font = new Font("ARIAL", Font.BOLD, 80);
        Font font2 = new Font("ARIAL", Font.BOLD, 20);
        Font font3 = new Font("ARIAL", Font.BOLD, 40);
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
