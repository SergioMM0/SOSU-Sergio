package GUI.Models;

import BE.User;
import BLL.BLLFacade;
import BLL.BLLManager;
import BLL.Exceptions.BLLException;
import DAL.util.DalException;

public class LoginMOD {

    private BLLFacade bllFacade;

    public LoginMOD(){
        bllFacade = new BLLManager();
    }

    public User checkCredentials(String email, String password)throws DalException, BLLException{
        return bllFacade.checkCredentials(email, password);
    }

}
