import org.junit.Test;
import static org.junit.Assert.*;
import core.*;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class BallTest {

    @Test
    public void testInitializeDirection() throws IOException, UnsupportedAudioFileException {
        Game pong = new Game();
        Ball ball = new Ball(pong);
        ball.setPos();
        assertTrue(ball.posX != 0 && ball.posY != 0);
    }
}
