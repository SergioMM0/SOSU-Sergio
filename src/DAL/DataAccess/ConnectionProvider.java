package DAL.DataAccess;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class ConnectionProvider {

     private SQLServerDataSource ds;

     public ConnectionProvider()  {
          ds = new SQLServerDataSource();
          ds.setDatabaseName("SimulationPlatformSOSU");
          ds.setUser("CSe21B_29");
          ds.setPassword("CSe21B_29");
          ds.setPortNumber(1433);
          ds.setServerName("10.176.111.31");
     }

     public Connection getConnection() throws SQLServerException {
          System.out.println("Connection Ongoing ");
          return ds.getConnection();
     }

}

