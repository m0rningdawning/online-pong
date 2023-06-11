package core;

import javax.swing.*;
import java.awt.*;

public class Field extends JFrame {
    Field(Game pong) {
        setTitle("Pong");
        setIconImage(Toolkit.getDefaultToolkit().getImage("resources/textures/icon.png"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pong.setPreferredSize(Game.FIELD_SIZE);
        pong.setMinimumSize(Game.FIELD_SIZE);
        setMinimumSize(Game.FIELD_SIZE);
        add(pong);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
