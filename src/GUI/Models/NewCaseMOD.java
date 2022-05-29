package GUI.Models;

import BE.Case;
import BLL.BLLFacade;
import BLL.BLLManager;
import BLL.Exceptions.BLLException;
import DAL.Exceptions.DALException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class NewCaseMOD {

    private BLLFacade bllFacade;

    public NewCaseMOD(){
        bllFacade = new BLLManager();
    }

    public Case createCase(Case newCase) throws DALException, BLLException {
        return bllFacade.createCase(newCase);
    }
}
