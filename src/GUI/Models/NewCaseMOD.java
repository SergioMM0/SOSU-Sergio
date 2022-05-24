package GUI.Models;

import BE.Case;
import BLL.BLLFacade;
import BLL.BLLManager;
import DAL.Exceptions.DALException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class NewCaseMOD {

    private BLLFacade bllFacade;
    private ObservableList<String> allCategories;
    private ObservableList<String> allSubcategories;

    public NewCaseMOD(){
        bllFacade = new BLLManager();
        allCategories = FXCollections.observableArrayList();
        allSubcategories = FXCollections.observableArrayList();
    }

    public Case createCase(Case newCase) throws DALException {
        return bllFacade.createCase(newCase);
    }
}
