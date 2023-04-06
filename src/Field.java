import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Field extends JFrame{
    Field(Game pong){
        JFrame frame = new JFrame();
        frame.getContentPane().add(pong);
        frame.setTitle("Pong");
        frame.setBackground(Color.BLACK);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setPreferredSize(Game.FIELD_SIZE);
        frame.setMinimumSize(Game.FIELD_SIZE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
