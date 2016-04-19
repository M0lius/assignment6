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
	static private boolean port1Busy = false;
	static private boolean port2Busy = false;
	static Hall batesHall = new Hall();
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
		Runnable serverThread = new ThreadedTicketServer(portNumber, "noname");
		Thread t = new Thread(serverThread);		
		t.start();
		
	}
	public static void start(int portNumber, String serverName) throws IOException {
		if (PORT == -1) {
			PORT = portNumber;
		} else if (PORT2 == -1){
			PORT2 = portNumber;
		} else {
			return;
		}
		Runnable serverThread = new ThreadedTicketServer(portNumber, serverName);
		Thread t = new Thread(serverThread);		
		t.start();
	}
	public static void stop(){
		PORT = -1;
		PORT2 = -1;
		batesHall = new Hall();
	}
	static public boolean isPort1Busy(){
		return port1Busy;
	}
	static public boolean isPort2Busy(){
		return port2Busy;
	}
	static public void setPort1Free(){
			port1Busy = false;
	}
	static public void setPort2Free(){
			port2Busy = false;
	}
	static public void setPort1Busy(){
			port1Busy = true;
	}
	static public void setPort2Busy(){
			port2Busy = true;
	}
	
}

class ThreadedTicketServer implements Runnable {

	String hostname = "127.0.0.1"; 
	String threadname = "X"; //name of this server (example: BoxOfficeA)
	TicketClient sc;
	int PORT = -1;
	
	ThreadedTicketServer(int portNumber, String serverName){
		PORT = portNumber;
		threadname = serverName;
	}


	public void run() {
		// TODO 422C
		ServerSocket serverSocket;
		try {
			while(true){
				serverSocket = new ServerSocket(PORT);
				Socket clientSocket = serverSocket.accept();
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				out.println(threadname);
				try{
					Seat best = TicketServer.batesHall.bestAvailableSeat();
					TicketServer.batesHall.markAvailableSeatTaken(best.getRowLocation(), best.getColumnLocation());
					out.println("Sold!");
					out.println(best.printTicketSeat());
					
				} catch (SoldOut e) {
					out.println("SoldOut!");	
				}
				in.close();
				out.close();
				serverSocket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
	