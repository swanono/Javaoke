package clientpkg;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import common.Constants.CLI;
import common.Constants.Networking;

public class ClientList extends Client {

    private String musicList;

    @Override
    public void sendRequest() {

        System.out.println(CLI.CLIENTL_OUT + "Sending Request ...");

        try {
            ObjectOutputStream clientOutput = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream serverInput = new ObjectInputStream(clientSocket.getInputStream());

            clientOutput.writeObject(Networking.RequestType.REQ_LIST + Networking.REQUEST_SEPARATOR + "Ask-List-1");
            System.out.println(CLI.CLIENTL_OUT + "Sent Request : " + Networking.RequestType.REQ_LIST + Networking.REQUEST_SEPARATOR + "Ask-List-1");
            
            System.out.println(CLI.CLIENTL_OUT + "Awaiting for response ...");
            musicList = (String) serverInput.readObject();
            System.out.println(CLI.CLIENTL_OUT + "Response received");

            clientOutput.close();
            serverInput.close();
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void activate() {
        System.out.println("---------- JAVAOKE MUSIC LIST ----------");
        System.out.println("The list of available musics is :" + System.lineSeparator());
        System.out.println(musicList + System.lineSeparator());
        System.out.println("Type 'ClientLauncher <ip-server> " + CLI.ARG_2_MUSIC_FILE + " <music-title>' to play a music");
        System.out.println("----------------------------------------");
    }
    
}