package GUI.Models;

import BE.Patient;
import BLL.BLLFacade;
import BLL.BLLManager;
import BLL.Exceptions.BLLException;
import DAL.Exceptions.DALException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class NewPatientMOD {

    private BLLFacade bllFacade;

    public NewPatientMOD(){
        bllFacade = new BLLManager();
    }

    public Patient createPatient(Patient patient)throws DALException, BLLException {
        return bllFacade.createPatient(patient);
    }
}
