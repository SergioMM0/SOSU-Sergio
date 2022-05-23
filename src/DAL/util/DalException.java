package DAL.util;

public class DalException extends Exception {

    public  DalException(String text , Throwable cause){
        super(text , cause);
    }
}
