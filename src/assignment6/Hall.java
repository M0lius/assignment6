package assignment6;

public class Hall{
	private Seat[][] seats = new Seat[27][28];	//makes a hall that is 27rows X 28columns 
	
	public Hall(){
		for(int i = 0; i < 27; i++){
			for (int j = 0; j < 28; j++){
				seats[i][j] = new Seat(i,j); //initalizes all the seats depending on their 
											 //location on the matrix
			}
		}
	}
	
	public boolean isSeatTaken(int row, int column){
		return seats[row][column].isTaken(); //marks seat at certain location taken
	}
	
	public void markAvailableSeatTaken(int row, int column){//requirement
		seats[row][column].setTaken(true);
	}
	
	public synchronized Seat bestAvailableSeat() throws SoldOut, SeatInLimbo{ //requirement
		for(int row = 0; row < 27; row ++){
			for(int column = 9; column < 28; column++){
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
