import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Field extends JFrame{
    Field(Game pong){
        JFrame frame = new JFrame();
        frame.setBackground(Color.BLACK);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(pong);
        frame.setPreferredSize(Game.FIELD_SIZE);
        frame.setMinimumSize(Game.FIELD_SIZE);
        frame.setTitle("Pong");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
