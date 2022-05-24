package GUI.Models;

import BE.Patient;
import BLL.BLLFacade;
import BLL.BLLManager;
import DAL.Exceptions.DALException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class NewPatientMOD {

    private BLLFacade bllFacade;

    public NewPatientMOD(){
        bllFacade = new BLLManager();
    }

    public Patient createPatient(Patient patient)throws DALException {
        return bllFacade.createPatient(patient);
    }
}
