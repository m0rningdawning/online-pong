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

    public void drawButtons(Graphics2D g, Rectangle button, String title){
        g.draw(button);
        g.drawString(title, button.x + 20, button.y + 30);
    }

    public void display(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        Font font = new Font("ARIAL", Font.BOLD, 80);
        Font font2 = new Font("ARIAL", Font.BOLD, 20);
        if (pong.isPlayerAWon){
            drawTitle(g, font, playerAWonString, Color.blue, 100);
            drawTitle(g, font2, subtitle, Color.yellow, 140);
        }
        else {
            drawTitle(g, font, playerBWonString, Color.yellow, 100);
            drawTitle(g, font2, subtitle, Color.blue, 140);
        }
        drawButtons(g2d, mainMenuButton, "MAIN MENU");
        drawButtons(g2d, quitButton, "QUIT");
    }
}
