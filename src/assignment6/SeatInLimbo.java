package assignment6;

public class SeatInLimbo extends Exception
{
    private static final long serialVersionUID = 1L;
    private Seat seat;

    public SeatInLimbo(String message)
    {
        super(message);
    }
    public SeatInLimbo(String message, Seat seat)
    {
    	super(message);
    	this.seat = seat;
    }
    public SeatInLimbo(String message, Throwable throwable)
    {
        super(message, throwable);
    }
    
    public String getInvalidSeatInfo(){
    	
    	return "Seat: " + this.seat.getRow() + 
    			Integer.toString(this.seat.getColumn());
    }
}
