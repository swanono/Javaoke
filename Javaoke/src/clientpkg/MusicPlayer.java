package clientpkg;

import common.LyricSentence;
import common.Song;

public class MusicPlayer {

    private Song song;
    private int pitch; // Maybe it is not necessary
    private float speed;
    private int lyricIndex;
    private long timerStart;
    private int[] singers;

    public MusicPlayer(int speed, int pitch, int[] singers, Song song) {
        this.song = song;
        this.singers = singers;
        this.pitch = pitch;
        this.speed = 1.0f + speed/10f;
        timerStart = 0;
        lyricIndex = -1;


    }

    public void playTrack() {
        timerStart = System.currentTimeMillis();
        song.manageLyrics(singers);
        song.playSong(speed, pitch);
    }

    public boolean hasChangedLyric() {
        boolean res = false;
        if(lyricIndex + 2 >= song.getLyricsSize())
            res = true;
        else if(System.currentTimeMillis() - timerStart >= (long)song.getLyric(lyricIndex + 1).getDate()/speed)
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