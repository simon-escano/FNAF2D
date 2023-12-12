package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {
    Clip clip;
    URL[] soundURL = new URL[30];
    FloatControl volume;

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
        soundURL[9] = getClass().getResource("/sound/static.wav");
        soundURL[10] = getClass().getResource("/sound/footstep3.wav");
        soundURL[11] = getClass().getResource("/sound/footstep4.wav");
        soundURL[12] = getClass().getResource("/sound/chase.wav");
        soundURL[13] = getClass().getResource("/sound/bgm1.wav");
        soundURL[14] = getClass().getResource("/sound/slide.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
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

    public void changeVolume(float num) {
        volume.setValue(num);
    }
}
