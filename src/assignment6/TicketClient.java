package assignment6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ThreadedTicketClient{
	String hostname = "127.0.0.1";
	String threadname = "X";
	int portNum = -1;
	TicketClient sc;
	Thread buyTicket;


	public ThreadedTicketClient(TicketClient sc, String hostname, String threadname) {
		this.sc = sc;
		this.hostname = hostname;
		this.threadname = threadname;
		Thread buyTicket;
	}

	public void connect() {
		System.out.flush();
		while(TicketServer.isPort1Busy()&& TicketServer.isPort2Busy()){}//wait till one port is open
		if (!TicketServer.isPort1Busy()) { //port one is not busy
			portNum = TicketServer.PORT; //use port one
			TicketServer.setPort1Busy();//mark busy
		} else if(TicketServer.PORT2 != -1) {//port two is initialized
			portNum = TicketServer.PORT2;//use port two
			TicketServer.setPort2Busy();//mark busy
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
						System.out.println(serverName + " has sold a ticket to " + threadname);
						System.out.println("TICKET INFO: " + ticketInfo);
						//out.println("THANKS!");
						Thread.sleep(500);
						
					} else {
						System.out.println("SOLD OUT: " + serverName + " unable to sell a ticket to " + threadname);
						//out.println(":(");
						Thread.sleep(500);
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
					synchronized (TicketServer.class){
						TicketServer.setPort1Free();//mark it free
					}
				} else {
					synchronized (TicketServer.class){
						TicketServer.setPort2Free();//mark port 2 free;
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
