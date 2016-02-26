package core;

// http://www.anyexample.com/programming/java/java_play_wav_sound_file.xml

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public final class Sound {

    static String strFilename;
    private static File soundFile;
    private static AudioInputStream audioStream;
    private static AudioFormat audioFormat;
    private static SourceDataLine sourceLine;

    public static void playSound(String filename) {
        strFilename = filename;
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    soundFile = new File(strFilename);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(1);
                }

                try {
                    audioStream = AudioSystem.getAudioInputStream(soundFile);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(1);
                }

                audioFormat = audioStream.getFormat();

                DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
                try {
                    sourceLine = (SourceDataLine) AudioSystem.getLine(info);
                    sourceLine.open(audioFormat);
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                    System.exit(1);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(1);
                }

                sourceLine.start();

                int nBytesRead = 0;
                byte[] abData = new byte[128000];
                while (nBytesRead != -1) {
                    try {
                        nBytesRead = audioStream.read(abData, 0, abData.length);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (nBytesRead >= 0) {
                        @SuppressWarnings("unused")
                        int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
                    }
                }

                sourceLine.drain();
                sourceLine.close();
            }
        }).start();

    }

}
