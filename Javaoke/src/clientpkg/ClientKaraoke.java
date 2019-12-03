package clientpkg;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import common.Song;
import common.Constants.CLI;
import common.Constants.Networking;

public class ClientKaraoke extends Client {

    private String musicTitle;
    private MusicPlayer music;
    private ClientGUI gui;
    private int speed;
    private int pitch;
    private int[] singers;

    public ClientKaraoke(String musicTitle, int speed, int pitch, int[] singers) {
        System.setProperty("org.java2d.opengl", "true");
        this.musicTitle = musicTitle;
        this.pitch = pitch;
        this.speed = speed;
        this.singers = singers;
    }

    @Override
    public void sendRequest() {
        
        System.out.println(CLI.CLIENTK_OUT + "Sending Request ...");

        try {
            ObjectOutputStream clientOutput = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream serverInput = new ObjectInputStream(clientSocket.getInputStream());

            clientOutput.writeObject(Networking.RequestType.REQ_MUSIC + Networking.REQUEST_SEPARATOR + musicTitle.trim());
            System.out.println(CLI.CLIENTK_OUT + "Sent Request : " + Networking.RequestType.REQ_MUSIC + Networking.REQUEST_SEPARATOR + musicTitle.trim());

            System.out.println(CLI.CLIENTK_OUT + "Awaiting for response ...");
            music = new MusicPlayer(speed, pitch, singers, (Song) serverInput.readObject());
            System.out.println(CLI.CLIENTK_OUT + "Response received");

            System.out.println(serverInput.readObject());
        } catch (IOException | ClassCastException | ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void activate() {
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
                    gui.updateImageType(type.toLowerCase());
            }
        }
        music.closeTrack();
    }

}