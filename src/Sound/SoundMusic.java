package src.Sound;


import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundMusic {
    private String file_path, sound_path;
    private int state;
    Thread thread;
    public SoundMusic(String file_path) {
        this.file_path = file_path;
        this.sound_path = file_path;
        this.state = 1;
    }

    public void playSound(String sound_path) {
        this.sound_path = sound_path;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File file = new File(sound_path);
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                    AudioFormat format = audioStream.getFormat();
                    DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
                    SourceDataLine audioLine = (SourceDataLine) AudioSystem.getLine(info);
                    byte[] data = new byte[512 * 1024];
                    audioLine.open();
                    audioLine.start();
                    int bytesRead = 0;
                    while (bytesRead != -1) {
                        if (state == 3) break;
                        bytesRead = audioStream.read(data, 0, data.length);
                        if (bytesRead >= 0) {
                            audioLine.write(data, 0, bytesRead);
                        }
                    }
                    audioLine.drain();
                    audioLine.close();
                } catch (UnsupportedAudioFileException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (LineUnavailableException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}


