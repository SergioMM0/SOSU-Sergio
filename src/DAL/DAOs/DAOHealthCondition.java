package DAL.DAOs;

import DAL.DataAccess.ConnectionProvider;

public class DAOHealthCondition {

    private final ConnectionProvider connectionProvider;

    public DAOHealthCondition(){
        connectionProvider = new ConnectionProvider();
    }
}
