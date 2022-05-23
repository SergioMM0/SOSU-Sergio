package GUI.Models;

import BE.Group;
import BE.Patient;
import BLL.BLLFacade;
import BLL.BLLManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AssignCaseMOD {

    private BLLFacade bllFacade;

    public AssignCaseMOD(){
        bllFacade = new BLLManager();
    }

}
