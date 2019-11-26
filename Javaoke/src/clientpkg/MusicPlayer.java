package clientpkg;

import common.LyricSentence;
import common.Song;

public class MusicPlayer {

    private Song song;
    private double volume; // Maybe it is not necessary
    private float speed;
    private int lyricIndex;
    private long timerStart;

    public MusicPlayer(int speed, Song song) {
        this.song = song;
        volume = 1.0; //just a value to start
        timerStart = 0;
        lyricIndex = -1;
        if(speed>=0)
            this.speed = speed;
        else
            this.speed = -1/speed;

    }

    public void playTrack() {
        timerStart = System.currentTimeMillis();
        song.playSong(speed);
    }

    public boolean hasChangedLyric() {
        boolean res = false;
        if(lyricIndex >= song.getLyricsSize())
            res = true;
        else if(System.currentTimeMillis() - timerStart >= (long)song.getLyric(lyricIndex + 1).getDate()*speed)
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
        song.stopSong();
    }
    @Override
    public void finalize() {
        closeTrack();
    }



}