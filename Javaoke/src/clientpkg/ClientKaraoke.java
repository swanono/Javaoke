package clientpkg;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import common.Song;
import common.Constants.Networking;

public class ClientKaraoke extends Client {

    String musicTitle;
    MusicPlayer music;
    ClientGUI gui;
    int speed;
    int pitch;

    public ClientKaraoke(String musicTitle, int speed, int pitch) {
        System.setProperty("org.java2d.opengl", "true");
        this.musicTitle = musicTitle;
        this.pitch = pitch;
        this.speed = speed;
    }

    @Override
    public void sendRequest() {
        
        System.out.println("Sending Request ...");

        try {
            ObjectOutputStream clientOutput = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream serverInput = new ObjectInputStream(clientSocket.getInputStream());

            clientOutput.writeObject(Networking.RequestType.REQ_MUSIC + Networking.REQUEST_SEPARATOR + musicTitle.trim());
            System.out.println("Sent Request : " + Networking.RequestType.REQ_MUSIC + Networking.REQUEST_SEPARATOR + musicTitle.trim());
            
            System.out.println("Awaiting for response ...");
            music = new MusicPlayer(1, (Song) serverInput.readObject());
            System.out.println("Response received");
        } catch (IOException | ClassCastException | ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void activate() {
        //music.printLyrics();
        gui = new ClientGUI(musicTitle);

        // Lancer la musique ici
        music.playTrack();

        while(true) {
            if(music.hasChangedLyric()) {
                music.next();
                String lyr = music.getLyric();
                String type = music.getType();
                int idSinger = music.getIdSinger();
                
                if(lyr == null)
                    break;
                else
                    gui.updateText(lyr, type, idSinger);
            }
        }
    }

}