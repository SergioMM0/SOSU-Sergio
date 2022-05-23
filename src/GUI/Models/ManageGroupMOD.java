package GUI.Models;

import BE.Group;
import BLL.BLLFacade;
import BLL.BLLManager;
import DAL.util.DalException;

public class ManageGroupMOD {

    private BLLFacade bllFacade;

    public ManageGroupMOD(){
        bllFacade = new BLLManager();
    }

    public Group createNewGroup(Group group) throws DalException{
        return bllFacade.createNewGroup(group);
    }

    public void updateGroup(Group selectedGroup) throws DalException{
        bllFacade.updateGroup(selectedGroup);
    }
}
