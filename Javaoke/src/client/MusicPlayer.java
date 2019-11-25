package client;

public class MusicPlayer {

    private Song song;
    private float volume; // Maybe it is not necessary
    private int speed;

    public MusicPlayer(int speed, Song song) {
        this.song = song;
        this.speed = speed;
        volume = 0; //just a value to start
    }

    public playTrack() {

        Sequencer musicSequencer = song.getMusicSequencer();
        // Need to check if the sequencer is ok

        
        musicSequencer.open();
        musicSequencer.start();
        while(true) { 
            if(musicSequencer.isRunning()) {
                try {
                    Thread.sleep(1000); // Check every second
                    // Check if we need to add a setter for the volume
                    // there !
                } catch(InterruptedException ignore) {
                    break;
                }
            } else {
                break; // stop the music
            }
        }
        // Close the Sequencer
        musicSequencer.stop();
        musicSequencer.close();

    }
}