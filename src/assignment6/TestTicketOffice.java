package assignment6;

import static org.junit.Assert.fail;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.Test;

public class TestTicketOffice {

	public static int score = 0;

	@Test
	public void basicServerTest() {
		try {
			TicketServer.start(16789);
		} catch (Exception e) {
			fail();
		}
		TicketClient client = new TicketClient();
		client.requestTicket();
		TicketServer.stop();
	}

	@Test
	public void testServerCachedHardInstance() {
		try {
			TicketServer.start(16790);
		} catch (Exception e) {
			fail();
		}
		TicketClient client1 = new TicketClient("localhost", "c1");
		TicketClient client2 = new TicketClient("localhost", "c2");
		client1.requestTicket();
		client2.requestTicket();
		TicketServer.stop();

	}

	@Test
	public void twoNonConcurrentServerTest() {
		try {
			TicketServer.start(16791);
		} catch (Exception e) {
			fail();
		}
		TicketClient c1 = new TicketClient("nonconc1");
		TicketClient c2 = new TicketClient("nonconc2");
		TicketClient c3 = new TicketClient("nonconc3");
		c1.requestTicket();
		c2.requestTicket();
		c3.requestTicket();
		TicketServer.stop();

	}
	
	@Test
	public void DoubleServerTest() {
		try {
			TicketServer.start(16793, "BoxOffice A");
			TicketServer.start(16795, "BoxOffice B");
		} catch (Exception e) {
			fail();
		}
		TicketClient c1 = new TicketClient("John");
		TicketClient c2 = new TicketClient("Worm");
		TicketClient c3 = new TicketClient("Mario");
		c1.requestTicket();
		c2.requestTicket();
		c3.requestTicket();
		
		TicketServer.stop();

	}
	
	@Test
	public void main() {
		int customers = 5;
		Queue<TicketClient> line = new LinkedList<TicketClient>();
		int random = (int )(Math.random() * 901 + 100);
		for(int i = 0; i < random + 1; i++){
			line.add(new TicketClient(new String("Person" + Integer.toString(i))));
		}
		
		try {
			TicketServer.start(16798, "BoxOffice A");
			//TicketServer.start(16796, "BoxOffice B");
		} catch (Exception e) {
			fail();
		}		
		while(!line.isEmpty()){
			TicketClient nextInLine = line.remove();
			nextInLine.requestTicket();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		TicketServer.stop();

	}

//	@Test
//	public void twoConcurrentServerTest() {
//		try {
//			TicketServer.start(16792);
//			//TicketServer.start(16793);
//		} catch (Exception e) {
//			fail();
//		}
//		final TicketClient c1 = new TicketClient("conc1");
//		final TicketClient c2 = new TicketClient("conc2");
//		final TicketClient c3 = new TicketClient("conc3");
//		Thread t1 = new Thread() {
//			public void run() {
//				c1.requestTicket();
//			}
//		};
//		Thread t2 = new Thread() {
//			public void run() {
//				c2.requestTicket();
//			}
//		};
//		Thread t3 = new Thread() {
//			public void run() {
//				c3.requestTicket();
//			}
//		};
//		t1.start();
//		t2.start();
//		t3.start();
//		try {
//			t1.join();
//			t2.join();
//			t3.join();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		TicketServer.stop();
//
//	}
}
