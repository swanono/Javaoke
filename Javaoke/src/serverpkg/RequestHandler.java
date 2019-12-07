package serverpkg;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import common.Song;
import common.Constants.CLI;
import common.Constants.FileReading;
import common.Constants.Networking;

// classe prenant en charge les demandes des clients individuellements
public class RequestHandler implements Runnable {

    // streams permettant de communiquer entre client et serveur
    private ObjectInputStream clientInput;
    private ObjectOutputStream serverOutput;
    private Stats stats;

    public RequestHandler(ObjectInputStream in, ObjectOutputStream out) {
        clientInput = in;
        serverOutput = out;
        stats = StatsManager.getStats();
    }

    // méthode prenant en charge la requete client
    @Override
    public void run() {

        System.out.println(CLI.HANDL_OUT + "Handling request ...");

        try {
            String clientRequest = (String) clientInput.readObject();
            System.out.println(CLI.HANDL_OUT + "Read request : " + clientRequest);

            Networking.RequestType cliReqType = Networking.RequestType.parseRequestStr(clientRequest);
            System.out.println(CLI.HANDL_OUT + "Parsed Request : " + cliReqType);
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
        System.out.println(CLI.HANDL_OUT + "Client Request for music list");

        File directoryList = new File(FileReading.RSRC_PATH);
        if(!directoryList.isDirectory()) {
            System.err.println("Incorrect Path to music list");
            Thread.currentThread().interrupt();
            return;
        }
        String[] musicList = directoryList.list();
        String finalMessage = "";
        for(String s : musicList) 
            finalMessage += s + System.lineSeparator();

        finalMessage += formatStatMessage(stats);
        
        try {
            serverOutput.writeObject(finalMessage);

            System.out.println(CLI.HANDL_OUT + "Sent Music List to Client");
        } catch (IOException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            try {
                clientInput.close();
                serverOutput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void sendMusicFile(String musicTitle) {
        System.out.println(CLI.HANDL_OUT + "Client Request for specific music : " + musicTitle);

        stats.addOneToMusic(musicTitle);
        
        File dir = new File(FileReading.RSRC_PATH + musicTitle);
        if(!dir.exists()) {
            System.err.println("Music requested doesn't exist");
            Thread.currentThread().interrupt();
            return;
        }

        Song music = new Song(musicTitle);
        try {
            serverOutput.writeObject(music);

            serverOutput.writeObject(formatStatMessage(stats));

            System.out.println(CLI.HANDL_OUT + "Sent '" + musicTitle + "' to Client");
        } catch (IOException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            try {
                clientInput.close();
                serverOutput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    // méthode permettant d'extraire le titre d'une musique de la String de requete client
    private static String getTitleFromRequest(String req) throws Exception {
        String[] subReq = req.split(Networking.REQUEST_SEPARATOR);

        if(subReq.length < 2)
            throw new Exception("The client request is too short (no title for the music)");

        String title = subReq[1];
        return title;
    }

    private static String formatStatMessage(Stats s) {
        return System.lineSeparator() +
            "Most frequent client : " + s.getBestClient() + System.lineSeparator() +
            "most popular music : " + s.getBestMusic() + System.lineSeparator();
    }
    
}