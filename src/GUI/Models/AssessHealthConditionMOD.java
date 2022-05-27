package GUI.Models;

import BE.HealthCondition;
import BE.Patient;
import BE.Subcategory;
import BLL.BLLFacade;
import BLL.BLLManager;
import DAL.Exceptions.DALException;

public class AssessHealthConditionMOD {

    private BLLFacade bllFacade;

    public AssessHealthConditionMOD(){
        bllFacade = new BLLManager();
    }


    public void addHealthCondition(HealthCondition currentHealthCondition, Subcategory subcategory, Patient patient) throws DALException {
        bllFacade.addHealthCondition(currentHealthCondition,subcategory,patient);
    }

    public void updateHealthCondition(HealthCondition healthCondition, Subcategory subcategory, Patient patient) throws DALException {
        bllFacade.updateHealthCondition(healthCondition,subcategory,patient);
    }
}
