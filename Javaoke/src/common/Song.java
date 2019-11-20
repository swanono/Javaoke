package common;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;

public class Song {

    private Sequencer music;
    private String title;
    private List<LyricSentence> lyrics;
    
    public Song(String title) {
       
        this.title = title;

        // LYRICS 
        lyrics = new ArrayList<LyricSentence>();

        BufferedReader br = new BufferedReader(new FileReader(new  File(title + Constants.FileReading.EXT_LYRICS)));
        String line;

        while((line = br.readLine()) != null) {
            LyricSentence lyric = new LyricSentence(line);
            lyrics.add(lyric);
        }


        // MIDI 

        File midiFile = new File(title + Constants.FileReading.EXT_MIDI);

        // Exception for new File doesn't exist
        // Testing the File object
        if(!midiFile.exists() || midiFile.isDirectory() || !midiFile.canRead()) {
            System.out.println("The title is incorrect or the file doesn't exist.");
            System.exit(1);
        }

        try {
            music = MidiSystem.getSequencer();
            music.setSequence(MidiSystem.getSequence(midiFile));
        }
        catch (MidiUnavailableException | InvalidMidiDataException | IOException e) {
            e.printStackTrace();
        }
    }
}

