package GUI.Models;

import BE.School;
import BLL.BLLFacade;
import BLL.BLLManager;
import DAL.Exceptions.DALException;
import javafx.collections.ObservableList;

public class AdminMOD {

    private BLLFacade bllFacade;
    private ObservableList<School> allSchools;

    public AdminMOD(){
        bllFacade = new BLLManager();
    }

    public ObservableList<School> getAllSchools() throws DALException {
        allSchools.addAll(bllFacade.getAllSchools());
        return allSchools;
    }
}
