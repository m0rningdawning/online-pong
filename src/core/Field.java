package core;

import javax.swing.*;

public class Field extends JFrame{
    Field(Game pong){
        JFrame frame = new JFrame("Pong");
        frame.getContentPane().add(pong);
        //frame.setBackground(new Color(27,119,90));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setPreferredSize(Game.FIELD_SIZE);
        frame.setMinimumSize(Game.FIELD_SIZE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.pack();
    }
}