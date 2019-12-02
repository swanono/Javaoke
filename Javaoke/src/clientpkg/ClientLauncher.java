package clientpkg;

import java.util.ArrayList;
import java.util.List;

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

    private static Integer[] sortArgs(String[] args){
        Integer[] sortArgs = new Integer[3]; // Sorting as {speed, pitch, singersIndexInArgs}
        
        // Default value :
        sortArgs[0] = 1;
        sortArgs[1] = 0;
        sortArgs[2] = null;

        switch(args.length){
            case 3:
                break;
            case 4:
                sortArgs = sortArg(sortArgs, args[3], 3);
                break;
            case 5:
                sortArgs = sortArg(sortArg(sortArgs, args[4], 4), args[3], 3);
                break;
            case 6:
                sortArgs = sortArg(sortArg(sortArg(sortArgs, args[5], 5), args[4], 4), args[3], 3);
                break;
            default:
                printInvalidArgs(0);
                break;
        }
        return sortArgs;
    }

    private static Integer[] sortArg(Integer[] sortArgs, String arg, int indexArg){
        if (arg.contains(CLI.ARG_OPTION_SPEED)){
            sortArgs[0] = Integer.valueOf(arg.split("=")[1]);
        }
        else if (arg.contains(CLI.ARG_OPTION_PITCH)){
            sortArgs[1] = Integer.valueOf(arg.split("=")[1]);
        }
        else if (arg.contains(CLI.ARG_OPTION_SINGER)){
            sortArgs[2] = indexArg;
        }
        else {
            printInvalidArgs(0);
        }
        return sortArgs;
    }


    private static Client launchClientKaraoke(String[] args){
        Client client = null;
        try{
            
            Integer[] sortedArgs = sortArgs(args);
            if (sortedArgs[2] == null)
                client = new ClientKaraoke(args[2], (int)sortedArgs[0], (int)sortedArgs[1], null);
            else{
                String[] stringSingers = args[sortedArgs[2]].split("=")[1].split(",");
                int[] singers = new int[stringSingers.length + 1];
                singers[0] = 0; // Add Chorus to the singers
                for(int i = 1; i < singers.length; i++)
                    singers[i] = (int)Integer.valueOf(stringSingers[i-1]);
                client = new ClientKaraoke(args[2], (int)sortedArgs[0], (int)sortedArgs[1], singers);
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