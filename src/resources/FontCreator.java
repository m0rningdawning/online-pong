package resources;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FontCreator {
    public static Font denver;
    public static Font monoton;
    public static void loadAndRegisterFont(String fontFilePath, boolean isDenver) {
        try {
            File fontFile = new File(fontFilePath);
            if (isDenver) {
                denver = Font.createFont(Font.TRUETYPE_FONT, fontFile);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(denver);
            }
            else {
                monoton = Font.createFont(Font.TRUETYPE_FONT, fontFile);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(monoton);
            }
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }
}
