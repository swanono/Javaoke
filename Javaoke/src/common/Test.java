package common;
import clientpkg.MusicPlayer;

public class Test {
    public static void main(String[] args) {
        Song test = new Song("Black-Eyed-Peas-I-Gotta-Feeling");
        MusicPlayer musicP = new MusicPlayer(1,0, null, test);
        musicP.playTrack();
    }
}