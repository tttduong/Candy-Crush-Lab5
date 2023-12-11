package test;
import java.io.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;



// To play sound using Clip, the process need to be alive.
// Hence, we use a Swing application.
public class Sound3 extends JFrame {
   private String filePath, soundPath;

   public Sound3() {
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setTitle("Test Sound Clip");
      this.setSize(300, 200);
      this.setVisible(true);

      try {
         soundPath = "D:/game/candy/testgame/src/test/BINZ.wav";
         File file = new File(soundPath);
        
         // URL url = this.getClass().getClassLoader().getResource("https://www.youtube.com/watch?v=uH78GUo2TOs");
         AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
         // Get a sound clip resource.
         Clip clip = AudioSystem.getClip();
         // Open audio clip and load samples from the audio input stream.
         clip.open(audioIn);
         clip.start();
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      }
   }

   public static void main(String[] args) {
      new Sound();
   }
}
