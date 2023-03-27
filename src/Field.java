import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Field extends JFrame{
    Field(){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setPreferredSize(Game.FIELD_SIZE);
        frame.setMinimumSize(Game.FIELD_SIZE);
        frame.setBackground(Color.black);
        frame.setTitle("Pong");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
