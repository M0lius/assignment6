package assignment6;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ThreadedTicketClient implements Runnable {
	String hostname = "127.0.0.1";
	String threadname = "X";
	boolean attended = false;
	TicketClient sc;

	public ThreadedTicketClient(TicketClient sc, String hostname, String threadname) {
		this.sc = sc;
		this.hostname = hostname;
		this.threadname = threadname;
	}

	public void run() {
		System.out.flush();
		try {
			while(TicketServer.port1Busy && TicketServer.port2Busy){}
			if (!TicketServer.port1Busy) {
				Socket echoSocket = new Socket(hostname, TicketServer.PORT);
				PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
				while(!in.ready()){}
				String serverName = in.readLine();
				String Sold = in.readLine();
				if(Sold.equals("Sold!")){
					String ticketInfo = in.readLine();
					System.out.println(serverName + " has sold a ticket to " + threadname);
					System.out.println("TICKET INFO: " + ticketInfo);
				}
				else{
					System.out.println("SOLD OUT: " + serverName + " unable to sell a ticket to " + threadname);
				}
				//BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
				echoSocket.close();
			} else {
				if (TicketServer.PORT2 != -1) {
					Socket echoSocket2 = new Socket(hostname, TicketServer.PORT2);
					PrintWriter out2 = new PrintWriter(echoSocket2.getOutputStream(), true);
					BufferedReader in2 = new BufferedReader(new InputStreamReader(echoSocket2.getInputStream()));
					while(!in2.ready()){}
					String serverName = in2.readLine();
					String Sold = in2.readLine();
					if(Sold.equals("Sold!")){
						String ticketInfo = in2.readLine();
						System.out.println(serverName + " has sold a ticket to " + threadname);
						System.out.println("TICKET INFO: " + ticketInfo);
					}
					else{
						System.out.println("SOLD OUT: " + serverName + " unable to sell a ticket to " + threadname);
					}
					//BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
					echoSocket2.close();
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

public class TicketClient {
	ThreadedTicketClient tc;
	String result = "dummy";
	String hostName = "";
	String threadName = "";
	Thread t;

	TicketClient(String hostname, String threadname) {
		tc = new ThreadedTicketClient(this, hostname, threadname);
		t = new Thread(tc);
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
		t.start();
	}

	void sleep() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
