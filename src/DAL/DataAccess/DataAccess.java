package DAL.DataAccess;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class DataAccess {

     private final SQLServerDataSource dataSource;

     public DataAccess()  {

          dataSource = new SQLServerDataSource();
          try (InputStream input = new FileInputStream("data/database.properties")) {

               Properties prop = new Properties();

               // load a properties file
               prop.load(input);
               dataSource.setServerName(prop.getProperty("servername"));
               dataSource.setUser(prop.getProperty("user"));
               dataSource.setPassword(prop.getProperty("password"));
               dataSource.setDatabaseName(prop.getProperty("databasename"));
               // get the property value and print it out

          } catch (IOException e) {
               e.printStackTrace();
          }
     }

     public Connection getConnection() throws SQLServerException {
          System.out.println("Connection Ongoing ");
          return dataSource.getConnection();
     }

}

