package serverpkg;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import common.Constants.Networking;

// classe prenant en charge les demandes des clients individuellements
public class RequestHandler implements Runnable {

    // streams permettant de communiquer entre client et serveur
    private ObjectInputStream clientInput;
    private ObjectOutputStream serverOutput;

    public RequestHandler(ObjectInputStream in, ObjectOutputStream out) {
        clientInput = in;
        serverOutput = out;
    }

    // méthode prenant en charge la requete client
    @Override
    public void run() {

        System.out.println("Handling request ...");

        try {
            String clientRequest = (String) clientInput.readObject();
            System.out.println("Read request : " + clientRequest);

            Networking.RequestType cliReqType = Networking.RequestType.parseRequestStr(clientRequest);
            System.out.println("Parsed Request : " + cliReqType);
            switch (cliReqType) {
                case REQ_LIST:
                    sendMusicList();
                    break;
                case REQ_MUSIC:
                    sendMusicFile(getTitleFromRequest(clientRequest));
                    break;
            
                default:
                    System.err.println("Invalid Client Request");
                    Thread.currentThread().interrupt();
                    return;
            }
        } catch (ClassNotFoundException | IOException | ClassCastException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
            return;
        } catch (Exception e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
            return;
        } finally {
            // ne pas oublier de fermer les streams
            try {
                clientInput.close();
                serverOutput.close();
            } catch (IOException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
                return;
            }
        }

    }

    private void sendMusicList() {
        System.out.println("Client Request for music list");
        // TODO ajouter l'envoi de la liste des musiques stockées
        try {
            serverOutput.writeObject("test" + System.lineSeparator() + "test2");
        } catch (IOException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
            return;
        }
    }
    private void sendMusicFile(String musicTitle) {
        System.out.println("Client Request for specific music");
        // TODO ajouter l'envoi d'un fichier de musique
    }

    // méthode permettant d'extraire le titre d'une musique de la String de requete client
    private String getTitleFromRequest(String req) throws Exception {
        String[] subReq = req.split(Networking.REQUEST_SEPARATOR);

        if(subReq.length < 2)
            throw new Exception("The client request is too short (no title for the music)");

        String title = subReq[1];
        return title;
    }
    
}