package assignment6;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ThreadedTicketClients implements Runnable {
	String hostname = "127.0.0.1";
	String threadname = "X";
	TicketClients sc;

	public ThreadedTicketClients(TicketClients sc, String hostname, String threadname) {
		this.sc = sc;
		this.hostname = hostname;
		this.threadname = threadname;
	}

	public void run() {
		System.out.flush();
		try {
			Socket echoSocket = new Socket(hostname, TicketServer.PORT);
			// PrintWriter out =
			new PrintWriter(echoSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			echoSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

public class TicketClients {
	ThreadedTicketClients tc;
	String result = "dummy";
	String hostName = "";
	String threadName = "";

	TicketClients(String hostname, String threadname) {
		tc = new ThreadedTicketClients(this, hostname, threadname);
		hostName = hostname;
		threadName = threadname;
	}

	TicketClients(String name) {
		this("localhost", name);
	}

	TicketClients() {
		this("localhost", "unnamed client");
	}

	void requestTicket() {
		// TODO thread.run()
		tc.run();
		System.out.println(hostName + "," + threadName + " got one ticket");
	}

	void sleep() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
