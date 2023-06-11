package menus;

import core.Game;
import java.awt.*;
import resources.FontCreator;

public class MainMenu {
    // Title
    String title = "PONG";
    String subtitle = "TOTALLY BUGLESS ;)";

    // Buttons
    public Rectangle playButton = new Rectangle(Game.WIDTH / 2 - 45, 250, 90, 45);
    public Rectangle playOnlineButton = new Rectangle(Game.WIDTH / 2 - 85, 350, 170,45);
    public Rectangle quitButton = new Rectangle(Game.WIDTH / 2 - 42, 450, 85, 45);

    public MainMenu(){
        FontCreator.loadAndRegisterFont("resources/fonts/hello-denver-display.denver-display-regular-regular.ttf", true);
        FontCreator.loadAndRegisterFont("resources/fonts/Monoton-Regular.ttf", false);
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
        //g.drawString(title, button.x + 20, button.y + 30);
        if (isQuit) {
            //g.setColor(Color.red);
            g.drawString(title, button.x + 19, button.y + 30);
        }
        else {
            //g.setColor(Color.yellow);
            g.drawString(title, button.x + 18, button.y + 30);
        }
    }

    public void display(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        Font font = FontCreator.monoton.deriveFont(80f);
        Font font2 = FontCreator.denver.deriveFont(25f);
        drawTitle(g, font, title, Color.blue, 100);
        drawTitle(g, font2, subtitle,Color.yellow, 140);
        drawButtons(g2d, playButton, "PLAY", true);
        drawButtons(g2d, playOnlineButton, "PLAY ONLINE", false);
        drawButtons(g2d, quitButton, "QUIT", true);
    }
}
