package soundPlayer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;

public class MusicPlayer {
    private final File file;
    // 是否循环播放
    private volatile boolean isLoop = false;
    // 是否正在播放
    private volatile boolean isPlaying;
    // FloatControl.Type.MASTER_GAIN的值(可用于调节音量)
    private float newVolumn = 7;
    private PlayThread playThread;
    public static MusicPlayer chessSelect = new MusicPlayer("sounds\\chessSelect.wav");
    public static MusicPlayer chessMove = new MusicPlayer("sounds\\chessMove.wav");
    public static MusicPlayer init = new MusicPlayer("sounds\\init.wav");
    public static MusicPlayer win = new MusicPlayer("sounds\\win.wav");
    public static MusicPlayer wrongMove = new MusicPlayer("sounds\\wrongMove.wav");
    public MusicPlayer(String srcPath) {
        file = new File(srcPath);
    }

    /**
     * 播放音乐
     */
    public void play() {
        over();
        if (playThread == null) {
            playThread = new PlayThread();
            playThread.start();
        }
    }

    /**
     * 结束音乐（并非暂停）
     */
    public void over() {
        isPlaying = false;
        if (playThread != null) {
            playThread = null;
        }
    }

    /**
     * 设置循环播放
     * @param isLoop
     * @return	返回当前对象
     */
    public MusicPlayer setLoop(boolean isLoop) {
        this.isLoop = isLoop;
        return this;
    }

    /**
     * -80.0~6.0206测试,越小音量越小
     * @param newVolumn
     * @return	返回当前对象
     */
    public MusicPlayer setVolumn(float newVolumn) {
        this.newVolumn = newVolumn;
        return this;
    }

    /**
     * 异步播放线程
     */
    private class PlayThread extends Thread {

        @Override
        public void run() {
            isPlaying = true;
            do {
//				isPlaying = true;

                SourceDataLine sourceDataLine = null;
                BufferedInputStream bufIn = null;
                AudioInputStream audioIn = null;
                try {
                    bufIn = new BufferedInputStream(new FileInputStream(file));
                    audioIn = AudioSystem.getAudioInputStream(bufIn); // 可直接传入file

                    AudioFormat format = audioIn.getFormat();
                    sourceDataLine = AudioSystem.getSourceDataLine(format);
                    sourceDataLine.open();
                    // 必须open之后
                    if (newVolumn != 7) {
                        FloatControl control = (FloatControl) sourceDataLine.getControl(FloatControl.Type.MASTER_GAIN);
//						System.out.println(control.getMaximum());
//						System.out.println(control.getMinimum());
                        control.setValue(newVolumn);
                    }

                    sourceDataLine.start();
                    byte[] buf = new byte[512];
//					System.out.println(audioIn.available());
                    int len = -1;
                    while (isPlaying && (len = audioIn.read(buf)) != -1) {
                        sourceDataLine.write(buf, 0, len);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                    if (sourceDataLine != null) {
                        sourceDataLine.drain();
                        sourceDataLine.close();
                    }
                    try {
                        if (bufIn != null) {
                            bufIn.close();
                        }
                        if (audioIn != null) {
                            audioIn.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } while (isPlaying && isLoop);
        }
    }

}