package GUI.Models;

import BE.User;
import BLL.BLLFacade;
import BLL.BLLManager;
import DAL.Exceptions.DALException;

public class ManageStudentMOD {

    private BLLFacade bllFacade;

    public ManageStudentMOD(){
        bllFacade = new BLLManager();
    }

    public User addNewStudent(User user) throws DALException {
        return bllFacade.addNewStudent(user);
    }

    public void updateStudent(User student) throws DALException {
        bllFacade.updateStudent(student);
    }
}
