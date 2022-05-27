package GUI.Models;

import BE.Category;
import BLL.BLLFacade;
import BLL.BLLManager;
import DAL.Exceptions.DALException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EvaluateCaseMOD {

    private BLLFacade bllFacade;
    private ObservableList<Category> allCategoriesHC;
    private ObservableList<Category> allCategoriesFA;

    public EvaluateCaseMOD(){
        bllFacade = new BLLManager();
        allCategoriesHC = FXCollections.observableArrayList();
        allCategoriesFA = FXCollections.observableArrayList();
    }

    public ObservableList<Category> getAllCategoriesHC() throws DALException {
        allCategoriesHC.addAll(bllFacade.getAllCategoriesHC());
        return allCategoriesHC;
    }
}
