package GUI.Models;

import BE.Group;
import BLL.BLLFacade;
import BLL.BLLManager;
import DAL.Exceptions.DALException;

public class ManageGroupMOD {

    private BLLFacade bllFacade;

    public ManageGroupMOD(){
        bllFacade = new BLLManager();
    }

    public Group createNewGroup(Group group) throws DALException {
        return bllFacade.createNewGroup(group);
    }

    public void updateGroup(Group selectedGroup) throws DALException {
        bllFacade.updateGroup(selectedGroup);
    }
}
