package GUI.Models;

import BE.FunctionalAbility;
import BE.Patient;
import BE.Subcategory;
import BLL.BLLFacade;
import BLL.BLLManager;
import DAL.Exceptions.DALException;

public class AssessFunctionalAbilityMOD {

    private BLLFacade bllFacade;

    public AssessFunctionalAbilityMOD(){
        bllFacade = new BLLManager();
    }

    public void addFunctionalAbility(FunctionalAbility currentFunctionalAbility, Patient currentPatient,
                                     Subcategory currentSubcategory) throws DALException{
        bllFacade.addFunctionalAbility(currentFunctionalAbility,currentPatient,currentSubcategory);
    }

    public void updateFunctionalAbility(FunctionalAbility currentFunctionalAbility,
                                        Patient currentPatient, Subcategory currentSubcategory) throws DALException {
        bllFacade.updateFunctionalAbility(currentFunctionalAbility,currentPatient,currentSubcategory);
    }
}
