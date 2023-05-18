package core;

import menus.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class MouseInput implements MouseListener {

    private MainMenu mMenu;
    private EndMenu eMenu;
    private Game pong;

    public MouseInput(MainMenu mMenu, EndMenu eMenu, Game pong) {
        this.pong = pong;
        this.mMenu = mMenu;
        this.eMenu = eMenu;
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        if (!Game.playStatus) {
            // Play button
            if (mx >= Game.WIDTH / 2 - 42 && mx <= Game.WIDTH / 2 + 42 && my >= 250 && my <= 295) {
                try {
                    pong.clearStats(false);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                pong.controlsExplanation();
                Game.playStatus = true;
            }
            // Play online button
            if (mx >= Game.WIDTH / 2 - 85 && mx <= Game.WIDTH / 2 + 85 && my >= 350 && my <= 395) {
                try {
                    pong.initNetwork();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                pong.controlsExplanation();
                Game.playStatus = true;
            }
            // Quit button
            if (mx >= Game.WIDTH / 2 - 42 && mx <= Game.WIDTH / 2 + 42 && my >= 450 && my <= 495) {
                System.exit(0);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
