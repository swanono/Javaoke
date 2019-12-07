package serverpkg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import common.Constants.CLI;
import common.Constants.Networking;

public class Server {

	private ServerSocket serverSocket;
	private Socket clientSocket;

	public static void main(String[] args) {

		boolean isLocal = false;
		if(args.length >= 1)
			if(args[0].equals("-l"))
				isLocal = true;
	

		Server serv = new Server(isLocal);

		serv.listen();

	}

	private Server(boolean isLocal) {
		try {
			if(isLocal)
				serverSocket = new ServerSocket(Networking.SERVER_LISTEN_PORT, 100, InetAddress.getLoopbackAddress());
			else
				serverSocket = new ServerSocket(Networking.SERVER_LISTEN_PORT);
			System.out.println();
			System.out.println(CLI.SERV_OUT + "Server listening on adress : " + serverSocket.getInetAddress().getHostAddress());
			System.out.println(CLI.SERV_OUT + "Server listening on port : " + serverSocket.getLocalPort());

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	// méthode permettant d'accepter plusieurs client à la fois par la création de nouveaux threads
	private void listen() {
		Stats stats = StatsManager.open();

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		boolean runServ = true;
		try {
			while(runServ) {
				System.out.println(CLI.SERV_OUT + "Awaiting for new client ...");
				clientSocket = serverSocket.accept();
				ObjectInputStream clientInput = new ObjectInputStream(clientSocket.getInputStream());
				ObjectOutputStream serverOutput = new ObjectOutputStream(clientSocket.getOutputStream());
				
				stats.addOneToUser(clientSocket.getInetAddress().getHostName());

				Thread handlerThread = new Thread(new RequestHandler(clientInput, serverOutput));
				handlerThread.start();
				System.out.println(CLI.SERV_OUT + "New Client Request Accepted : " +
					clientSocket.getInetAddress().getHostName() + " @ " +
					clientSocket.getInetAddress().getHostAddress() + ":" +
					clientSocket.getPort());

				if(in.ready())
					if(in.readLine().trim().equals(CLI.SERV_STOP_ARG))
						runServ = false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			StatsManager.close();
		}

	}

}
