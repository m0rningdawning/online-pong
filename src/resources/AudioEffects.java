package resources;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioEffects {
    public Clip clip;
    public AudioInputStream audioStream;
    public File hit;
    public File sides;
    public File victory;
    public File defeat;
    public boolean isPlaying = false;

    public AudioEffects() throws UnsupportedAudioFileException, IOException {
        hit = new File("sfx/hit.wav");
        sides = new File("sfx/sides.wav");
        victory = new File("sfx/victory.wav");
        defeat = new File("sfx/defeat.wav");
    }

    public void play(boolean isHit) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        if (isHit)
            audioStream = AudioSystem.getAudioInputStream(hit);
        else
            audioStream = AudioSystem.getAudioInputStream(sides);
        clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
    }

    public void playEnd(boolean isVictory) throws UnsupportedAudioFileException, IOException {
        if (isPlaying)
            stop();
        if (isVictory)
            audioStream = AudioSystem.getAudioInputStream(victory);
        else
            audioStream = AudioSystem.getAudioInputStream(defeat);
        try {
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            isPlaying = true;
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }
    public void stop() {
        clip.stop();
        isPlaying = false;
    }
}
