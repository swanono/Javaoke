package clientpkg;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import common.Song;
import common.Constants.Networking;

public class ClientKaraoke extends Client {

    String musicTitle;
    Song music;

    public ClientKaraoke(String musicTitle) {
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
        // TODO Auto-generated method stub

        

    }

}