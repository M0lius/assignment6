package assignment6;

public class Hall{
	//Seat[][]
	private Seat[][] seats = new Seat[27][28];	
	
	public Hall(){
		for(int i = 0; i < 27; i++){
			for (int j = 0; j < 28; j++){
				seats[i][j] = new Seat(i,j);
			}
		}
	}
	
	public boolean isSeatTaken(int row, int column){
		return seats[row][column].isTaken();
	}
	
	public void markAvailableSeatTaken(int row, int column){
		seats[row][column].setTaken(true);
	}
	
	public Seat bestAvailableSeat() throws SoldOut{
		for(int row = 0; row < 27; row ++){
			for(int column = 9; column < 28; column++){
				if(!seats[row][column].isTaken()){
					return seats[row][column];
				}
			}
			for(int column = 0; column < 9; column++){
				if(!seats[row][column].isTaken()){
					return seats[row][column];
				}
			}
		}
		
		throw new SoldOut("No more Tickets");
	}
}
