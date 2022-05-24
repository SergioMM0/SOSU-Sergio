package GUI.Models;

import BE.User;
import BLL.BLLFacade;
import BLL.BLLManager;
import BLL.Exceptions.BLLException;
import DAL.Exceptions.DALException;

public class LoginMOD {

    private BLLFacade bllFacade;

    public LoginMOD(){
        bllFacade = new BLLManager();
    }

    public User checkCredentials(String email, String password)throws DALException, BLLException{
        return bllFacade.checkCredentials(email, password);
    }

}
