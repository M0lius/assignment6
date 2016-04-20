/**
 * Classes to simulate clients and ticket servers
 * @author Tauseef Aziz
 * @author Mario Molina
 * @version 1.00 2016-4-20
 */

package assignment6;

//Purpose: Defines Seat that are in the hall
public class Seat
{
	
	final static String[] rows = {"A","B","C","D","E","F","G","H","I",
	"J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","AA"};  //alphabet+AA
	
	private boolean taken; //true if seat is taken
	private boolean beingConsidered;
	private String row;// Strin A - AA
	private int rowNum;// the index of the row
	private int columnNum; //the index of the column 
	private int column; // seat number from 101-128
	private String location; //general location (check below)
	/*
	 * FM = Front Middle FR = Front Right FL = Front Left
	 * BM = BackMiddle   BR = Back Right  BL = Back Left
	 */
	
	/**
	 * General Seat constructor
	 */
	public Seat() //general initializer, just puts invalid values on variables 
	{
		taken = true; 
		row = "invalid";
		rowNum = -1;
		columnNum = -1;
		column = -1;
		location = "invalid";
	}
	/**
	 * Seat constructor
	 * @param row
	 * @param column
	 */
	public Seat(int row, int column) //Initializes a seat
	{
		taken = false;
		this.rowNum = row;
		this.columnNum = column;
		this.row = rows[row];
		this.column = column + 101;
		
		//creates location depending on position (!!!only for "batesHall" as of now!!!)
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
	
	//general getters and setters
	public boolean isTaken() {
		return taken; //returns true if taken
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
	
	//sets the seat as taken
	public void setTaken(boolean status)
	{
		taken = status;
	}
	
	public boolean isBeingConsidered() {
		return beingConsidered;
	}

	public void setBeingConsidered(boolean beingConsidered) {
		this.beingConsidered = beingConsidered;
	}

	/**
	 * outputs a string with the general seat information
	 * @return String with ticket info
	 */
	public String printTicketSeat(){
		String ticket = new String("ROW: " + this.row + 
				" SEAT: " + Integer.toString(column)+ " AREA: " + this.location);
		return ticket;
	}
	
}