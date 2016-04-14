package assignment6;

public class Hall {
	//Seat[][]
	private Seat[][] Seats = new Seat[27][28];
	String[] rows = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","AA"};
	
	public Hall(){
		for(int i = 0; i < 27; i++){
			for (int j = 0; j < 28; j++){
				Seats[i][j] = new Seat(rows[i],j);
			}
		}
	}
	
	public boolean isSeatTaken(int row, int column){
		return Seats[row][column].isTaken();
	}
	
	public void markAvailableSeatTaken(int row, int column){
		Seats[row][column].setTaken(true);
	}
	
	public void bestAvailableSeat(){
		
		
	}
}
