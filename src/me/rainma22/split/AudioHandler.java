package me.rainma22.split;

import java.io.*;
import javax.sound.sampled.*;

public class AudioHandler {
    private File[] musics;
    public long cliptime=0;
    public AudioHandler(){
        musics=new File("WAV").listFiles();
    }
    public void playMusic() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (true) {
                    try {
                        AudioInputStream stream;
                        AudioFormat format;
                        DataLine.Info info;
                        Clip clip;
                        for (File f : musics) {
                            stream = AudioSystem.getAudioInputStream(f);
                            format = stream.getFormat();
                            info = new DataLine.Info(Clip.class, format);
                            clip = (Clip) AudioSystem.getLine(info);
                            clip.open(stream);
                            clip.start();
                            cliptime=0;
                            while (cliptime<clip.getMicrosecondLength()&&!Main.SkipMusic()){
                                cliptime=clip.getMicrosecondPosition();
                                Thread.sleep(100);
                                System.out.println(cliptime+"/"+clip.getMicrosecondLength());
                            }
                            if (Main.SkipMusic()) Main.SkipMusic(false);
                            clip.close();
                            System.out.println("clip closed");
                        }
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                }
            }
        }.start();
    }
    public void playSFX(int i){
        new Thread(){
            @Override
            public void run() {
                super.run();try {
            long cliptime=0;
            File f=new File("SFX",i+".wav");
            AudioInputStream stream;
            AudioFormat format;
            DataLine.Info info;
            Clip clip;
                stream = AudioSystem.getAudioInputStream(f);
                format = stream.getFormat();
                info = new DataLine.Info(Clip.class, format);
                clip = (Clip) AudioSystem.getLine(info);
                clip.open(stream);
                clip.start();
                while (cliptime<clip.getMicrosecondLength()){
                    cliptime=clip.getMicrosecondPosition();
                    Thread.sleep(100);
                }
                clip.close();
            } catch(Exception e){
            e.printStackTrace();
        }

    }
        }.start();
    }
}