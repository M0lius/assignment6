package assignment6;

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
