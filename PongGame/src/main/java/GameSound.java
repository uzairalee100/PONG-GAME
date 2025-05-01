import javax.sound.sampled.*;
import java.io.*;

public class GameSound {
    public static void playBounce() {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File("bounce.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (Exception e) {
            System.out.println("Sound error: " + e);
        }
    }
}
