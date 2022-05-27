package DAL.DAOs;

import DAL.DataAccess.ConnectionProvider;

public class DAOFunctionalAbility {

    private final ConnectionProvider connectionProvider;

    public DAOFunctionalAbility(){
        connectionProvider = new ConnectionProvider();
    }


}
