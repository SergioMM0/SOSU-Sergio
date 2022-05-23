package GUI.Models;

import BE.Case;
import BLL.BLLFacade;
import BLL.BLLManager;
import DAL.util.DalException;
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

    public Case createCase(Case newCase) throws DalException {
        return bllFacade.createCase(newCase);
    }
}
