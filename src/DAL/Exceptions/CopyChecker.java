package DAL.Exceptions;

public class CopyChecker {
/*
    private static CopyChecker instance;

    private CopyChecker(){}

    public static CopyChecker getInstance(){
        if(instance == null){
            instance = new CopyChecker();
        }
        return instance;
    }


 */
    public static boolean checkIfCopy(int integer){
        return integer != 0;
    }
}
