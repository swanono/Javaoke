package clientpkg;

import common.Constants.CLI;

public class ClientLauncher {

    public static void main(String[] args) {

        for(String s : args)
            System.out.print(s + " ");

        if(args.length < 2)
            printInvalidArgs(1);
        
        Client client = null;

        // choix du type de client (et de requete) en fonction des arguments
        switch (args[1]) {
            case CLI.ARG_2_MUSIC_LIST:
                client = new ClientList();
                break;
            case CLI.ARG_2_MUSIC_FILE:
                if(args.length < 3)
                    printInvalidArgs(2);
                client = new ClientKaraoke(args[2]);
                break;
            default:
                printInvalidArgs(0);
                break;
        }

        client.connectToServer(args[0]);
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
                System.err.println("<request-type> can be " + CLI.ARG_2_MUSIC_LIST + " or " + CLI.ARG_2_MUSIC_FILE);
                break;
            case 2:
                System.err.println("Command must include the music title for argument " + CLI.ARG_2_MUSIC_FILE);
                break;
        }
        System.exit(1);
    }

}