package assignment6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TicketServer {
	static int PORT = -1;
	static int PORT2 = -1;
	static String ServerName = "invalid";
	static String ServerName2 = "invalid";
	// EE422C: no matter how many concurrent requests you get,
	// do not have more than three servers running concurrently
	final static int MAXPARALLELTHREADS = 3;

	public static void start(int portNumber) throws IOException {
		if (PORT == -1) {
			PORT = portNumber;
		} else if (PORT2 == -1){
			PORT2 = portNumber;
		} else {
			return;
		}
		Runnable serverThread = new ThreadedTicketServer(portNumber);
		Thread t = new Thread(serverThread);		
		t.start();
		
	}
	public static void start(int portNumber, String serverName) throws IOException {
		if (PORT == -1) {
			PORT = portNumber;
			ServerName = serverName;
		} else if (PORT2 == -1){
			PORT2 = portNumber;
			ServerName2 = serverName;
		} else {
			return;
		}
		Runnable serverThread = new ThreadedTicketServer(portNumber);
		Thread t = new Thread(serverThread);		
		t.start();
	}
	public static void stop(){
		PORT = -1;
		PORT2 = -1;		
	}
}

class ThreadedTicketServer implements Runnable {

	String hostname = "127.0.0.1";
	String threadname = "X";
	String testcase;
	TicketClient sc;
	int PORT = -1;
	
	ThreadedTicketServer(int portNumber){
		PORT = portNumber;
	}

	public void run() {
		// TODO 422C
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(PORT);
			Socket clientSocket = serverSocket.accept();
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}