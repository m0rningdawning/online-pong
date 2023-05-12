import org.junit.Test;
import static org.junit.Assert.*;
import core.*;

import java.io.IOException;

public class BallTest {

    @Test
    public void testInitializeDirection() throws IOException {
        Game pong = new Game();
        Ball ball = new Ball(pong);
        ball.initializeDirection();
        assertTrue(ball.dirX != 0 && ball.dirY != 0);
    }
}
