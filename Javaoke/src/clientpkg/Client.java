package clientpkg;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import common.Constants.CLI;
import common.Constants.Networking;

public abstract class Client {

    protected Socket clientSocket;

    protected Client() { }

    public void connectToServer(String ip) {
        try {
            System.out.println(CLI.CLIENT_OUT + "Establishing connexion ...");
            clientSocket = new Socket(ip, Networking.SERVER_LISTEN_PORT);
            System.out.println(CLI.CLIENTL_OUT + "Established connexion with server at " + ip + ":" + Networking.SERVER_LISTEN_PORT);
        } catch (UnknownHostException e) {
			e.printStackTrace();
			System.out.println(CLI.CLIENTL_OUT + "aucune adresse ne correspond à : " + ip);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("le port " + Networking.SERVER_LISTEN_PORT + " n'est pas ouvert");
		}
    }

    public abstract void sendRequest();
    public abstract void activate();

}