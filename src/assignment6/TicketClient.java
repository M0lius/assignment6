package assignment6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ThreadedTicketClient{
	String hostname = "127.0.0.1"; //localhost
	String threadname = "X";//name of thread attending client
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

	public ThreadedTicketClient(TicketClient sc, String hostname, String threadname) {
		this.sc = sc;
		this.hostname = hostname;
		this.threadname = threadname;
		Thread buyTicket;
	}

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
					PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
					BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
					while (!in.ready()) {
					}
					String serverName = in.readLine();
					String Sold = in.readLine();
					if (Sold.equals("Sold!")) {
						String ticketInfo = in.readLine();
						System.out.println(serverName + " has sold a ticket to " + threadname + " || TICKET INFO: " + ticketInfo);
						//System.out.println("TICKET INFO: " + ticketInfo);
						//out.println("THANKS!");
						Thread.sleep(150);
						
					} else {
						System.out.println("SOLD OUT: " + serverName + " unable to sell a ticket to " + threadname);
						//out.println(":(");
						Thread.sleep(150);
					}
					// BufferedReader stdIn = new BufferedReader(new
					// InputStreamReader(System.in));
					echoSocket.close();				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
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


public class TicketClient {
	ThreadedTicketClient tc;
	String result = "dummy";
	String hostName = "";
	String threadName = "";

	TicketClient(String hostname, String threadname) {
		tc = new ThreadedTicketClient(this, hostname, threadname);
		hostName = hostname;
		threadName = threadname;
	}

	TicketClient(String name) {
		this("localhost", name);
	}

	TicketClient() {
		this("localhost", "unnamed client");
	}

	void requestTicket() {
		// TODO thread.run()
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
