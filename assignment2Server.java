import java.io.*;
import java.net.*;

public class assignment2Server {
	
	protected Socket clientSocket           = null;
	protected ServerSocket serverSocket     = null;
	protected assignment2ServerThread[] threads    = null;
	protected int numClients                = 0;

	public static int SERVER_PORT = 6969;
	public static int MAX_CLIENTS = 25;

	public assignment2Server() {
		try {
			serverSocket = new ServerSocket(SERVER_PORT);
			System.out.println("Running...");
			System.out.println("Listening to port: "+SERVER_PORT);
			threads = new assignment2ServerThread[MAX_CLIENTS];
			while(true) {
				clientSocket = serverSocket.accept();
				System.out.println("Client #"+(numClients+1)+" connected.");
				threads[numClients] = new assignment2ServerThread(clientSocket);
				threads[numClients].start();
				numClients++;
			}
		} catch (IOException e) {
			System.err.println("IOException while creating server connection");
		}
	}
	


	public static void main(String[] args) {
		assignment2Server newServer = new assignment2Server();
	}
}