/**
 * Classes to simulate clients and ticket servers
 * @author Tauseef Aziz
 * @author Mario Molina
 * @version 1.00 2016-4-20
 */


package assignment6;

import static org.junit.Assert.fail;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import org.junit.Test;

//Purpose: Test our client and ticket server simulation
public class TestTicketOffice {
	//array of random names
	final static String[] NAMES = {"Nelle", "Anja" , "Charolette", "Burt", "Tauseef", "Ariel", "Modesto", "Reina", "Ed", 
										"Sondra", "Lyla", "Ruby", "Kent", "Noe", "Pamela", "Susanna", "Shaneka", "Jesica", 
										"Willow", "Debbi", "Mario", "Warren", "Filiberto", "Cruz", "Kerri", "Antoinette", 
										"Lashunda", "Werner", "Clifton", "Lena", "Aleshia", "Dann", "Camila", "Cherise", 
										"Benny", "Lupe", "Monty", "Lura", "Hermelinda", "Darline", "Venetta", "Alfredo", 
										"Donnette", "Rolf", "Robin", "Adolph", "Angla", "Elissa", "Jennine", "Keri", "Magaly", 
										"Hobert", "Perry", "Lisa", "Jo", "Mehtaab", "Niraj", "Chase", "Valvano", "Che", "Telang",
										"Yerraballi", "Fatima", "Amanda", "Farhan", "Jett", "Maxwell", "Brandon", "Alec",
										"Garret", "Andrei", "Tong", "Kevin", "Caroline", "Brian", "Horng-Bin", "Qing", "Allen", 
										"Henry", "Sneha", "Alvin", "Ali", "Rohan", "Rikin", "George", "Michael", "Kaisheng"};

	public static int score = 0;
	/**
	 * Randomly selects a name from an array
	 * @param array String array holding names
	 * @return String returns a random name from the array
	 */
	public String getRandomName(String[] array) {
		Random rand = new Random();
		int i = rand.nextInt(array.length);
		return array[i];
	}
	
	//Our test to fully simulate tickets being sold for Bates Hall
	@Test
	public void main() {
		int customers = 0;
		//line of ticket clients
		Queue<TicketClient> line = new LinkedList<TicketClient>();
		int random = (int )(Math.random() * 901 + 100);//adds a number between 100-1000
		customers += random;
		for(int i = 0; i < random + 1; i++){
			//adds clients to queue
			line.add(new TicketClient("localhost", getRandomName(NAMES), i +1));
		}
		try {
			//Starts two ticket servers
			TicketServer.start(16798, "BoxOffice A");
			TicketServer.start(16796, "BoxOffice B");
		} catch (Exception e) {
			fail();
		}
		while(!line.isEmpty()){
			TicketClient nextInLine = line.remove();
			nextInLine.requestTicket();
			
			if (line.size() < 100){ //adds more customers if line has less than 100 customers
				random = (int )(Math.random() * 801 + 100); //adds a number between 100-800
				for(int i = customers + 1; i < random + customers + 1; i++){
					line.add(new TicketClient("localhost", getRandomName(NAMES), i +1));
				}
				customers += random;
			}
		}
		TicketServer.reset();
	}

//	@Test
//	public void basicServerTest() {
//		try {
//			TicketServer.start(16789);
//		} catch (Exception e) {
//			fail();
//		}
//		TicketClient client = new TicketClient();
//		client.requestTicket();
//		TicketServer.stop();
//	}
//
//	@Test
//	public void testServerCachedHardInstance() {
//		try {
//			TicketServer.start(16790);
//		} catch (Exception e) {
//			fail();
//		}
//		TicketClient client1 = new TicketClient("localhost", "c1");
//		TicketClient client2 = new TicketClient("localhost", "c2");
//		client1.requestTicket();
//		client2.requestTicket();
//		TicketServer.stop();
//
//	}
//
//	@Test
//	public void twoNonConcurrentServerTest() {
//		try {
//			TicketServer.start(16791);
//		} catch (Exception e) {
//			fail();
//		}
//		TicketClient c1 = new TicketClient("nonconc1");
//		TicketClient c2 = new TicketClient("nonconc2");
//		TicketClient c3 = new TicketClient("nonconc3");
//		c1.requestTicket();
//		c2.requestTicket();
//		c3.requestTicket();
//		TicketServer.stop();
//
//	}
	
//	@Test
//	public void DoubleServerTest() {
//		try {
//			TicketServer.start(16793, "BoxOffice A");
//			TicketServer.start(16795, "BoxOffice B");
//		} catch (Exception e) {
//			fail();
//		}
//		TicketClient c1 = new TicketClient("John");
//		TicketClient c2 = new TicketClient("Worm");
//		TicketClient c3 = new TicketClient("Mario");
//		c1.requestTicket();
//		c2.requestTicket();
//		c3.requestTicket();
//		
//		TicketServer.stop();
//
//	}
	
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
