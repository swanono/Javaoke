package clientpkg;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import common.Constants.Networking;

public class ClientList extends Client {

    @Override
    public void sendRequest() {
        try {
            ObjectOutputStream clientOutput = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream serverInput = new ObjectInputStream(clientSocket.getInputStream());

            // TODO peut-etre à changer en writeObject
            clientOutput.writeChars(Networking.RequestType.REQ_LIST + Networking.REQUEST_SEPARATOR + "Ask-List-1");
            String response = (String) serverInput.readObject();
            // TODO à finir
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void activate() {
        // TODO Auto-generated method stub

    }
    
}