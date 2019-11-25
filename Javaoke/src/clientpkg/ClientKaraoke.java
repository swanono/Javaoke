package clientpkg;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import common.Song;
import common.Constants.Networking;

public class ClientKaraoke extends Client {

    String musicTitle;
    Song music;
    ClientGUI gui;

    public ClientKaraoke(String musicTitle) {
        System.setProperty("org.java2d.opengl", "true");
        this.musicTitle = musicTitle;
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
            music = (Song) serverInput.readObject();
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
        long timer = System.currentTimeMillis();
        music.play();

        while(true) {
            if(music.hasChangedLyric(timer)) {
                String lyr = music.nextLyric();
                if(lyr == null)
                    break;
                else
                    gui.updateText(lyr);
            }
        }
    }

}