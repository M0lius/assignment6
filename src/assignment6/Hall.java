/**
 * Classes to simulate clients and ticket servers
 * @author Tauseef Aziz
 * @author Mario Molina
 * @version 1.00 2016-4-20
 */


package assignment6;

//Purpose: Defines a hall 
public class Hall{
	final static int ROW = 27;		//dimensions for Bates Hall
	final static int COL = 28;
	
	private Seat[][] seats = new Seat[ROW][COL];	//makes a hall that is 27rows X 28columns 
	
	/**
	 * default hall constructor
	 */
	public Hall(){
		for(int i = 0; i < ROW; i++){
			for (int j = 0; j < COL; j++){
				seats[i][j] = new Seat(i,j); //initalizes all the seats depending on their 
											 //location on the matrix
			}
		}
	}
	/**
	 * Checks if seat is taken
	 * @param row row of set
	 * @param column column of seat
	 * @return
	 */
	public boolean isSeatTaken(int row, int column){
		return seats[row][column].isTaken(); //marks seat at certain location taken
	}
	
	/**
	 * Marks the seat as taken
	 * @param row row of seat
	 * @param column column of seat
	 */
	public void markAvailableSeatTaken(int row, int column){//requirement
		seats[row][column].setTaken(true);
	}
	
	/**
	 * Calculates and returns best available seat
	 * @return best available seat
	 * @throws SoldOut no seats left
	 * @throws SeatInLimbo seat is already being considered
	 */
	public synchronized Seat bestAvailableSeat() throws SoldOut, SeatInLimbo{ //requirement
		for(int row = 0; row < ROW; row ++){
			for(int column = 9; column < COL; column++){
				if(!seats[row][column].isTaken()){
					if(!seats[row][column].isBeingConsidered()){
						seats[row][column].setBeingConsidered(true);
						return seats[row][column];
					}
					else{
						throw new SeatInLimbo("seat is already being considered", seats[row][column]);
					}
				}
			}
			for(int column = 0; column < 9; column++){
				if(!seats[row][column].isTaken()){
					if(!seats[row][column].isBeingConsidered()){
						seats[row][column].setBeingConsidered(true);
						return seats[row][column];
					}
					else{
						throw new SeatInLimbo("seat is already being considered", seats[row][column]);
					}
				}
			}
		}
		
		throw new SoldOut("No more Tickets"); //throws when no more tickets are available
	}
}
