package clientpkg;

import common.LyricSentence;
import common.Song;

public class MusicPlayer {

    private Song song;
    private double volume; // Maybe it is not necessary
    private int speed;
    private int lyricIndex;

    public MusicPlayer(int speed, Song song) {
        this.song = song;
        this.speed = speed;
        volume = 1.0; //just a value to start
    }

    public playTrack() {

        Sequencer musicSequencer = song.getMusicSequencer();
        // Need to check if the sequencer is ok

        musicSequencer.open();
        musicSequencer.start();

    }

    public boolean hasChangedLyric(long timerStart) {
        boolean res = false;
        if(lyricIndex >= lyrics.size())
            res = true;
        else if(System.currentTimeMillis() - timerStart >= lyrics.get(lyricIndex + 1).date)
            res = true;
        return res;
    }

    public String getLyric() {
        String res = null;
        LyricSentence lyric = song.getLyric(lyricIndex);
        if(lyric != null )
            res = lyric.getText();
        return res;
    }
    public int getIdSinger() {
        int res = -1;
        LyricSentence lyric = song.getLyric(lyricIndex);
        if(lyric != null )
            res = lyric.getIdSinger();
        return res;
    }

    public String getType() {
        String res = null;
        LyricSentence lyric = song.getLyric(lyricIndex);
        if(lyric != null )
            res = lyric.getType();
        return res;
    }

    public void next() {
        lyricIndex++;
    }

    public void closeTrack(){
        // Close the Sequencer
        musicSequencer.stop();
        musicSequencer.close();
    }


}