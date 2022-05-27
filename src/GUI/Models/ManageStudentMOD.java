package GUI.Models;

import BE.User;
import BLL.BLLFacade;
import BLL.BLLManager;
import BLL.Exceptions.BLLException;
import DAL.Exceptions.DALException;

public class ManageStudentMOD {

    private BLLFacade bllFacade;

    public ManageStudentMOD(){
        bllFacade = new BLLManager();
    }

    public User addNewStudent(User user) throws DALException, BLLException {
        return bllFacade.addNewUser(user);
    }

    public void updateStudent(User student) throws DALException, BLLException{
        bllFacade.updateUser(student);
    }
}
