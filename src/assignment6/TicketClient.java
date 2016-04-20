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
import java.net.Socket;

//Purpose: Runs the ticket client
class ThreadedTicketClient{
	String hostname = "127.0.0.1"; //localhost
	String threadname = "X";//name of client
	int customerNum = -1;//customer number
	int portNum = -1; //number of port being used
	TicketClient sc;// the client
	Thread buyTicket; //thread when client is being attended
	volatile static private boolean port1Busy = false;//if port1 is attending a client
	volatile static private boolean port2Busy = false;//if port2 is attending a client
    private static final Object waitObject = ThreadedTicketClient.class;
 	
	static public boolean isPort1Busy(){
		return port1Busy;
	}
	static public boolean isPort2Busy(){
		return port2Busy;
	}

	/**
	 * Constructor for ticket client runner
	 * @param sc the ticket client
	 * @param hostname the name of the host
	 * @param threadname name of the thread
	 */
	public ThreadedTicketClient(TicketClient sc, String hostname, String threadname, int num) {
		this.sc = sc;
		this.hostname = hostname;
		this.threadname = threadname;
		this.customerNum = num;

		Thread buyTicket;
	}

	/**
	 * Connects client with server
	 * Creates client thread once connection is made in order to move to next client
	 * Marks respective port as busy right before connecting
	 * Marks respective port free once thread is finished 
	 */
	public void connect() {
		System.out.flush();
		while(isPort1Busy()&& isPort2Busy()){}//wait till one port is open
		if (!isPort1Busy()) { //port one is not busy
			synchronized (ThreadedTicketClient.waitObject){
				portNum = TicketServer.PORT; //use port one
				port1Busy = true;//mark port 2 free;
				ThreadedTicketClient.waitObject.notifyAll();
			}
		} else if(TicketServer.PORT2 != -1) {//port two is initialized
			synchronized (ThreadedTicketClient.waitObject){
				portNum = TicketServer.PORT2;//use port two
				port2Busy = true;//mark port 2 free;
				ThreadedTicketClient.waitObject.notifyAll();
			}
		}
		
		buyTicket = new Thread() {
			public void run() {
				Socket echoSocket;
				try {
					echoSocket = new Socket(hostname, portNum);
					//Used to receive messages from server
					BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
					while (!in.ready()) {
					}
					String serverName = in.readLine();
					String Sold = in.readLine();
					if (Sold.equals("Sold!")) {
						//Client has been sold a ticket
						String ticketInfo = in.readLine();
						System.out.println(serverName + " has sold a ticket to Customer# " + customerNum + ": " + threadname + " || TICKET INFO: " + ticketInfo);
						Thread.sleep(150);
						
					} else {
						//No more tickets left
						System.out.println("SOLD OUT: " + serverName + " unable to sell a ticket to Customer# " + customerNum + ": " + threadname);
						Thread.sleep(150);
					}
					echoSocket.close();				
				} catch (IOException e) {
					// Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// Auto-generated catch block
					e.printStackTrace();
				}
				if(portNum == TicketServer.PORT){//if used port one
					synchronized (ThreadedTicketClient.waitObject){
						port1Busy = false;//mark it free
						ThreadedTicketClient.waitObject.notifyAll();
					}
				} else {
					synchronized (ThreadedTicketClient.waitObject){
						port2Busy = false;//mark port 2 free;
						ThreadedTicketClient.waitObject.notifyAll();
					}
				}
			}
		};
		buyTicket.start();
	}
}

//Purpose: Defines the TicketClient
public class TicketClient {
	ThreadedTicketClient tc;
	String result = "dummy";
	String hostName = "";
	String threadName = "";

	//Constructors
	TicketClient(String hostname, String threadname, int num) {
		tc = new ThreadedTicketClient(this, hostname, threadname, num);
		hostName = hostname;
		threadName = threadname;
	}

	TicketClient(String name) {
		this("localhost", name, 0);
	}

	TicketClient() {
		this("localhost", "unnamed client", 0);
	}
	
	/**
	 * Requests ticket from server
	 */
	void requestTicket() {
		tc.connect();
	}

	void sleep() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
