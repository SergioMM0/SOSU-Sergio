package BLL;

import BE.Case;
import BE.Group;
import BE.Patient;
import BE.User;
import BLL.Exceptions.BLLException;
import DAL.DALFacade;
import DAL.DALManager;
import DAL.Exceptions.DALException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import java.time.LocalDate;

@DisplayName("BLL Manager Test as well as DAL Facade entities creation routine")
class BLLManagerTest {
    BLLManager manager;
    DALFacade dalFacade;


    @Test
    @DisplayName("Credentials test (login routine) ")
    void checkCredentials() {

        manager = new BLLManager();

        User actualuser = null;
        try {
            actualuser = manager.checkCredentials(".",".");

        } catch (DALException | BLLException e) {
            e.printStackTrace();
        }
        User expecteduser =  new User(1,"Dotted",".",2);
        expecteduser.setId(1);
        Assertions.assertEquals(expecteduser.getId() , actualuser.getId());
    }

    @DisplayName("Duplicate Case creation test")
    @Test
    void createCase() {
        dalFacade = new DALManager();
        Case newCase = new Case(1,"First case","Some conditions",1);

        InvalidParameterException ex = Assertions.assertThrows(InvalidParameterException.class,()-> dalFacade.createCase(newCase));
        String actualMessage = ex.getMessage();
        String expectedMessage = "The case already exists in the system";
        Assertions.assertEquals(expectedMessage,actualMessage);
    }

    @DisplayName("Duplicate Patient creation test")
    @Test
    void createPatient() {
        dalFacade = new DALManager();
        Patient patient = new Patient(1,"Jhon","Travolta", LocalDate.now(),"Male",null,1);

        InvalidParameterException ex = Assertions.assertThrows(InvalidParameterException.class,()-> dalFacade.createPatient(patient));
        String actualMessage = ex.getMessage();
        String expectedMessage = "The patient already exists in the system";
        Assertions.assertEquals(expectedMessage,actualMessage);
    }

    @DisplayName("Duplicate Student creation test")
    @Test
    void addNewStudent() {
        dalFacade = new DALManager();
        User user =  new User(1,"Dotted",".",2);
        InvalidParameterException ex = Assertions.assertThrows(InvalidParameterException.class,()-> dalFacade.addNewUser(user));
        String actualMessage = ex.getMessage();
        String expectedMessage = "User already exists in the system";
        Assertions.assertEquals(expectedMessage,actualMessage);
    }

    @DisplayName("Duplicate group creation test")
    @Test
    void createNewGroup() {
        dalFacade = new DALManager();
        Group group = new Group(1,"A good group",null,1);
        InvalidParameterException ex = Assertions.assertThrows(InvalidParameterException.class,()-> dalFacade.createGroup(group));
        String actualMessage = ex.getMessage();
        String expectedMessage = "The group already exists";
        Assertions.assertEquals(expectedMessage,actualMessage);
    }

}