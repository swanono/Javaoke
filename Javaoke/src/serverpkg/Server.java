package serverpkg;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import common.Constants.Networking;

public class Server {

	private ServerSocket serverSocket;
	private Socket clientSocket;

	public static void main(String[] args) {

		Server serv = new Server();

		serv.listen();

	}

	private Server() {
		try {
			serverSocket = new ServerSocket(Networking.SERVER_LISTEN_PORT, 100, InetAddress.getLocalHost());
			System.out.println();
			System.out.println("Server listening on adress : " + serverSocket.getInetAddress().getHostAddress());
			System.out.println("Server listening on port : " + serverSocket.getLocalPort());

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	// méthode permettant d'accepter plusieurs client à la fois par la création de nouveaux threads
	private void listen() {

		try {
			while(true) {
				clientSocket = serverSocket.accept();
				ObjectInputStream clientInput = new ObjectInputStream(clientSocket.getInputStream());
				ObjectOutputStream serverOutput = new ObjectOutputStream(clientSocket.getOutputStream());
				
				Thread handlerThread = new Thread(new RequestHandler(clientInput, serverOutput));
				handlerThread.start();
				System.out.println("New Client Request Accepted");
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

	}

}
