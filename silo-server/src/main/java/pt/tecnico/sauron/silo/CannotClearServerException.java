package pt.tecnico.sauron.silo;

//Exception for when camera is not found
public class CannotClearServerException extends Exception{
    private static final long serialVersionUID = 1L;

    public CannotClearServerException(String message){
        super(message);

    }
}