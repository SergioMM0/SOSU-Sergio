package BLL;

import BE.Case;
import BE.User;
import BLL.Exceptions.BLLException;
import DAL.DALFacade;
import DAL.DALManager;
import DAL.Exceptions.DALException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

class BLLManagerTest {
    BLLManager manager;
    DALFacade dalFacade;
    @Test
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

    @Test
    void createCase() {
        dalFacade = new DALManager();
        Case newCase = new Case(1,"First case","Some conditions",1);

        InvalidParameterException ex = Assertions.assertThrows(InvalidParameterException.class,()-> dalFacade.createCase(newCase));
        String actualMessage = ex.getMessage();
        String expectedMessage = "The case already exists in the system";
        Assertions.assertEquals(expectedMessage,actualMessage);
    }

    @Test
    void createPatient() {

    }

    @Test
    void getAllGroups() {
    }

    @Test
    void getAllCases() {
    }

    @Test
    void getAllPatients() {
    }

    @Test
    void addNewStudent() {
    }

    @Test
    void updateStudent() {
    }

    @Test
    void deleteStudent() {
    }

    @Test
    void getAllStudent() {
    }

    @Test
    void updatePatient() {
    }

    @Test
    void createNewGroup() {
    }

    @Test
    void updateGroup() {
    }

    @Test
    void addStudentToGroup() {
    }

    @Test
    void deleteGroup() {
    }

    @Test
    void removeParticipant() {
    }

    @Test
    void updateCase() {
    }

    @Test
    void deleteCase() {
    }

    @Test
    void deletePatient() {
    }

    @Test
    void saveStudentQuestionAnswer() {
    }

    @Test
    void getFirstQuestion() {
    }

    @Test
    void getNextQuestion() {
    }

    @Test
    void getPreviousQuestion() {
    }

    @Test
    void getQuestionaireAnswer() {
    }
}