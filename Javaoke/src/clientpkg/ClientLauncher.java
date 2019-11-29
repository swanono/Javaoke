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
                client = launchClientKaraoke(args);
                break;
            default:
                printInvalidArgs(0);
                break;
        }

        client.connectToServer(args[0]);
        client.sendRequest();
        client.activate();

    }

    private static Client launchClientKaraoke(String[] args){
        Client client = null;
        try{
            switch(args.length){
                case 3:
                    client = new ClientKaraoke(args[2], 1, 1);
                    break;
                case 4:
                    if (args[3].contains(CLI.ARG_OPTION_SPEED))
                        client = new ClientKaraoke(args[2], Integer.valueOf(args[3].split("=")[1]), 1);
                    else
                        client = new ClientKaraoke(args[2], 1, Integer.valueOf(args[3].split("=")[1]));
                    break;
                case 5:
                    if (args[3].contains(CLI.ARG_OPTION_PITCH))
                        client = new ClientKaraoke(args[2], Integer.valueOf(args[3].split("=")[1]), Integer.valueOf(args[4].split("=")[1]));
                    else
                        client = new ClientKaraoke(args[2], Integer.valueOf(args[4].split("=")[1]), Integer.valueOf(args[3].split("=")[1]));
                default:
                    printInvalidArgs(0);
                    break;
            }

        }
        catch(NumberFormatException e){
            printInvalidArgs(0);
        }

        return client;

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