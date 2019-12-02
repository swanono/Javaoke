package common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Track;
import javax.sound.midi.ShortMessage;

import common.Constants.FileReading;
import common.Constants.Networking;

public class Song implements Serializable {
    
    private static final long serialVersionUID = -3359255885389392855L;
    
    private transient File tempFile;
    private transient Sequencer music;
    private String title;
    private List<LyricSentence> lyrics;
    
    
    public Song(String title) {
        this.title = title;

        // LYRICS 
        lyrics = new ArrayList<LyricSentence>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(Constants.FileReading.RSRC_PATH + title + "/" + title + Constants.FileReading.EXT_LYRICS)));
            String line;

            while((line = br.readLine()) != null) {
                LyricSentence lyric = new LyricSentence(line);
                lyrics.add(lyric);
            }


            // MIDI 

            tempFile = new File(Constants.FileReading.RSRC_PATH + title + "/" + title + Constants.FileReading.EXT_MIDI);

            // Exception for new File doesn't exist
            // Testing the File object
            if(!tempFile.exists() || tempFile.isDirectory() || !tempFile.canRead()) {
                System.err.println("The title is incorrect or the file doesn't exist.");
                System.exit(1);
            }

        
            music = MidiSystem.getSequencer();
            music.setSequence(MidiSystem.getSequence(tempFile));
            br.close();
        }
        catch (MidiUnavailableException | InvalidMidiDataException | IOException e) {
            e.printStackTrace();
        }
    }

   public LyricSentence getLyric(int index) {
        LyricSentence res = null;
        if(index < lyrics.size() - 1) 
            res = lyrics.get(index);
        return res;
    }



    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        
        BufferedInputStream fileReader = new BufferedInputStream(new FileInputStream(tempFile));

        byte[] buffer = new byte[Networking.STREAM_ARRAY_LENGTH];
        int count = 0;

        while((count = fileReader.read(buffer)) > 0) {
            oos.write(buffer, 0, count);
        }

        fileReader.close();
        
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        
        tempFile = File.createTempFile(title, FileReading.EXT_MIDI);

        BufferedOutputStream fileWriter = new BufferedOutputStream(new FileOutputStream(tempFile));

        byte[] buffer = new byte[Networking.STREAM_ARRAY_LENGTH];
        int count = 0;

        while((count = ois.read(buffer)) > 0) {
            fileWriter.write(buffer, 0, count);
        }
        

        try {
            music = MidiSystem.getSequencer();
            music.setSequence(MidiSystem.getSequence(tempFile));
        } catch (MidiUnavailableException | InvalidMidiDataException | IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        fileWriter.close();

    }

    public void  manageLyrics(int[] singers) {

        if (singers != null && singers[1] != -1) {

            // Removing all the no singer
            for(int i = 0; i < lyrics.size() ; i++){
                boolean isInside = false;
                for (int j = 0; j < singers.length; j++){
                    if (singers[j] == lyrics.get(i).getIdSinger()){
                        isInside = true;
                        break;
                    }
                }
                if (!isInside)
                    lyrics.get(i).setLyricAsMute();
            }

        }
        else if (singers != null) {
            // Setting all singer as singer one
            for(LyricSentence ly : lyrics)
                ly.setLyricAsSingerOne();
        }

    }

    public void playSong(float speed, int pitch){
        try {
            

            System.out.println("You are playing : " + this.title + " || Your settings is : x" + String.valueOf(speed) + " speed and Pitch Bend = " + String.valueOf(pitch));
            music.open();

            // Speed setting
            music.setTempoFactor(speed);

            // Pitch setting
            Track [] tracks = music.getSequence().getTracks();
            if ( tracks != null ) {
                for ( int i = 0; i < tracks.length; i++ ) {
                    Track track = tracks[i];
                    for (int j = 0; j < track.size(); j++){
                        if (track.get(j).getMessage() instanceof ShortMessage){
                            ShortMessage m = (ShortMessage)track.get(j).getMessage();
                            
                            int note = m.getData1();
                            int sign = 1; // symbolize +1 or -1

                            if (pitch < 0)
                                sign = -1;

                            while ( pitch != 0 && note >= 0 && note <= 127 ){
                                note = note + (int)sign*12; 
                                pitch = pitch - sign;
                            }
                            m.setMessage(m.getCommand(), m.getChannel(), note, m.getData2());
                        }
                    }
                        
                }
            }
            
            music.start();
            
            
        }
        catch (MidiUnavailableException | IllegalStateException | InvalidMidiDataException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void stopSong(){
        try {
            // Close the Sequencer
            music.stop();
            music.close();
            System.out.println("Thanks for using Javaoke for your karaoke sessions ! We hope you enjoyed our app !");
            System.out.println("If you enjoyed it, could you please give us a good mark ! :)");
        }
        catch (IllegalStateException e ){
            e.printStackTrace();
        }
        
    }

    public int getLyricsSize() {
        return lyrics.size();
    }
}

