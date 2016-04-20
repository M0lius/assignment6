/**
 * Classes to simulate clients and ticket servers
 * @author Tauseef Aziz
 * @author Mario Molina
 * @version 1.00 2016-4-20
 */

package assignment6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

//Purpose: Defines ticket server
public class TicketServer {
	static int PORT = -1;// Port value of Port 1
	static int PORT2 = -1;//Port value of Port 2
	static Hall batesHall = new Hall(); //the theater hall we are selling seats for
	final static int MAXPARALLELTHREADS = 3; //we are only using 2 servers like pdf said

	public static void start(int portNumber) throws IOException {
		if (PORT == -1) { //first port not initialized
			PORT = portNumber; //initalize it
		} else if (PORT2 == -1){ //first port initialized but not the second
			PORT2 = portNumber; //initialize it
		} else {
			return; //no more than two ports please (makes this call useless
		}
		Runnable serverThread = new ThreadedTicketServer(portNumber, "noname");//if no name given
		Thread t = new Thread(serverThread);		
		t.start();
		
	}
	
	//same as above but initializes name of server
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
	
	public static void reset(){ //resets both ports and the theater seats
		PORT = -1;
		PORT2 = -1;
		batesHall = new Hall();
	}
	
	
}

//Purpose: Runs the ticket server
class ThreadedTicketServer implements Runnable {

	String hostname = "127.0.0.1"; 
	String threadname = "X"; //name of this server (example: BoxOfficeA)
	TicketClient sc;
	int PORT = -1;
	
	/**
	 * Constructor for ticket server runner
	 * @param portNumber
	 * @param serverName
	 */
	ThreadedTicketServer(int portNumber, String serverName){
		PORT = portNumber;
		threadname = serverName;
	}

	public void run() {
		ServerSocket serverSocket;
		try {
			while(true){
				serverSocket = new ServerSocket(PORT);
				Socket clientSocket = serverSocket.accept();
				//used to send messages to client
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				out.println(threadname);
				try{
					Seat best = new Seat();
					boolean foundSeat = false;
					while(!foundSeat){
						try {
							//find best available seat
							best = TicketServer.batesHall.bestAvailableSeat();
							foundSeat = true;
						} catch (SeatInLimbo e){
							//seat found has already been reserved
							System.out.println(threadname + ": Failed to Reserve " + e.getInvalidSeatInfo());
						}
					}
					//sends ticket received message to client
					TicketServer.batesHall.markAvailableSeatTaken(best.getRowLocation(), best.getColumnLocation());
					out.println("Sold!");
					out.println(best.printTicketSeat());

					
				} catch (SoldOut e) {
					//Sends sold out message to client
					out.println("SoldOut!");
					System.exit(0);
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
	