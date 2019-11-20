package clientpkg;

import common.Constants.CLI;

public class ClientLauncher {

    public static void main(String[] args) {

        if(args.length < 3)
            printInvalidArgs(1);
        
        Client client = null;

        // choix du type de client (et de requete) en fonction des arguments
        switch (args[2]) {
            case CLI.ARG_2_MUSIC_LIST:
                client = new ClientList();
                break;
            case CLI.ARG_2_MUSIC_FILE:
                if(args.length < 4)
                    printInvalidArgs(2);
                client = new ClientKaraoke(args[3]);
                break;
            default:
                printInvalidArgs(0);
                break;
        }

        client.connectToServer(args[1]);
        client.sendRequest();
        client.activate();

    }

    // méthode à activer si la commande est mauvaise
    private static void printInvalidArgs(int version) {
        System.err.println("Invalid Command Line Argument(s)");
        switch(version) {
            case 0:
                System.err.println("Allowed Arguments are " + CLI.ARG_2_MUSIC_LIST + " and " + CLI.ARG_2_MUSIC_FILE);
                break;
            case 1:
                System.err.println("Command must be of shape : ClientLauncher <ip-server> <request-type> (<music-title>)");
                break;
            case 2:
                System.err.println("Command must include the music title for argument " + CLI.ARG_2_MUSIC_FILE);
                break;
        }
        System.exit(1);
    }

}