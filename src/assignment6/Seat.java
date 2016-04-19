package assignment6;

public class Seat
{
	private String[] rows = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","AA"};
	private boolean taken;
	private String row;
	public int rowNum;
	public int columnNum;
	private int column;
	private String location;
	
	public Seat()
	{
		taken = false;
		row = "";
		rowNum = -1;
		columnNum = -1;
		column = -1;
		location = "invalid";
	}
	
	public Seat(int row, int column)
	{
		taken = false;
		this.rowNum = row;
		this.columnNum = column;
		this.row = rows[row];
		this.column = column + 101;
		
		if(row < 14 && column < 9){
			location = "FR"; //Front Right
		}
		if(row < 14 && column > 19){
			location = "FL"; //Front Left
		}
		if(row < 14 && column >= 9 && column <= 19){
			location = "FM"; //Front Middle
		}
		if(row >= 14 && column < 9){
			location = "BR"; //Back Right
		}
		if(row >= 14 && column > 19){
			location = "BL"; //Back Left
		}
		if(row >= 14 && column >= 9 && column <= 19){
			location = "BM"; //Back Middle
		}
	}

	public boolean isTaken() {
		return taken;
	}

	public String getRow() {
		return row;
	}
	
	public String getLocation() {
		return location;
	}

	public int getColumn() {
		return column;
	}
	
	public int getRowLocation(){
		return rowNum;
	}
	public int getColumnLocation(){
		return columnNum;
	}
	
	public void setTaken(boolean status)
	{
		taken = status;
	}
	public String printTicketSeat(){
		String ticket = new String("ROW: " + this.row + 
				" SEAT: " + Integer.toString(column)+ " AREA: " + this.location);
		return ticket;
	}
	
}