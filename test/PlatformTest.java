import org.junit.Test;
import static org.junit.Assert.*;
import core.*;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class PlatformTest {

    @Test
    public void testInitializePosition() throws IOException, UnsupportedAudioFileException {
        new Game();
        Platform platform1 = new Platform(true);
        Platform platform2 = new Platform(false);
        assertTrue(platform1.posX == 0 && platform1.posY == (double) Game.HEIGHT /2 - (double) platform1.height /2);
        assertTrue(platform2.posX == Game.WIDTH - (int)(platform2.width * 1.5) && platform2.posY == (double) Game.HEIGHT /2 - (double) platform1.height /2);
    }
}
