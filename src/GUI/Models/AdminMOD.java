package GUI.Models;

import BE.School;
import BLL.BLLFacade;
import BLL.BLLManager;
import BLL.Exceptions.BLLException;
import DAL.Exceptions.DALException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AdminMOD {

    private BLLFacade bllFacade;
    private ObservableList<School> allSchools;

    public AdminMOD(){
        bllFacade = new BLLManager();
        allSchools = FXCollections.observableArrayList();
    }

    public ObservableList<School> getAllSchools() throws DALException, BLLException {
        allSchools.addAll(bllFacade.getAllSchools());
        return allSchools;
    }
}
