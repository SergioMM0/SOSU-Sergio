package DAL.Exceptions;

public class DALException extends Exception {

    public DALException(String text , Throwable cause){
        super(text , cause);
    }
}
