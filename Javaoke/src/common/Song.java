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
                System.out.println(line);
            }


            // MIDI 

            tempFile = new File(Constants.FileReading.RSRC_PATH + title + "/" + title + Constants.FileReading.EXT_MIDI);

            // Exception for new File doesn't exist
            // Testing the File object
            if(!tempFile.exists() || tempFile.isDirectory() || !tempFile.canRead()) {
                System.out.println("The title is incorrect or the file doesn't exist.");
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
}

