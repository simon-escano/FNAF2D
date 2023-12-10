package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {
    Clip clip;
    URL[] soundURL = new URL[30];

    public Sound() {
        soundURL[0] = getClass().getResource("/sound/ambience.wav");
        soundURL[1] = getClass().getResource("/sound/footstep1.wav");
        soundURL[2] = getClass().getResource("/sound/footstep2.wav");
        soundURL[3] = getClass().getResource("/sound/main-menu.wav");
        soundURL[4] = getClass().getResource("/sound/jumpscare.wav");
        soundURL[5] = getClass().getResource("/sound/switch.wav");
        soundURL[6] = getClass().getResource("/sound/select.wav");
        soundURL[7] = getClass().getResource("/sound/pickup.wav");
        soundURL[8] = getClass().getResource("/sound/door.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
}
