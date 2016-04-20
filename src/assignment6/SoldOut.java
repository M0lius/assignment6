/**
 * Classes to simulate clients and ticket servers
 * @author Tauseef Aziz
 * @author Mario Molina
 * @version 1.00 2016-4-20
 */


package assignment6;

//Purpose: Exception for when all tickets have been sold
public class SoldOut extends Exception
{
    private static final long serialVersionUID = 1L;

    public SoldOut(String message)
    {
        super(message);
    }

    public SoldOut(String message, Throwable throwable)
    {
        super(message, throwable);
    }
}
