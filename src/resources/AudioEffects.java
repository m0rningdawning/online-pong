package resources;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioEffects {
    public Clip clip;
    public AudioInputStream audioStream;
    public File hit;
    public File sides;
    public boolean isPlaying = false;
    public AudioEffects() throws UnsupportedAudioFileException, IOException {
        hit = new File("sfx/hit.wav");
        sides = new File("sfx/sides.wav");
    }

    public void play(boolean isHit) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        if (isPlaying)
            stop();
        if (isHit)
            audioStream = AudioSystem.getAudioInputStream(hit);
        else
            audioStream = AudioSystem.getAudioInputStream(sides);
        clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
        isPlaying = true;
    }

    public void stop() {
        clip.stop();
        isPlaying = false;
    }
}
