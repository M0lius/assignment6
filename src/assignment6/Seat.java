package assignment6;

public class Seat
{
	private boolean taken;
	private String row;
	private int number;
	
	public Seat()
	{
		taken = false;
		row = "";
		number = -1;
	}
	
	public Seat(String row, int number)
	{
		taken = false;
		this.row = row;
		this.number = number;
	}

	public boolean isTaken() {
		return taken;
	}

	public String getRow() {
		return row;
	}

	public int getNumber() {
		return number;
	}
	
	public void setTaken(boolean status)
	{
		taken = status;
	}
	
}