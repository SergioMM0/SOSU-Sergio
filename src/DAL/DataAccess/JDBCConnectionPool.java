package DAL.DataAccess;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;



public class JDBCConnectionPool {

    private static final Logger LOGGER = Logger.getLogger(JDBCConnectionPool.class);
    private final SQLServerDataSource dataSource;
    private long connectionExpirationTimeMillis = 10_000;
    private HashMap<Connection, Long> locked = new HashMap<>();
    private HashMap<Connection, Long> unlocked = new HashMap<>();

    public JDBCConnectionPool() {
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
            LOGGER.error("Unable to establish a connection", e);
        }


    }

    public synchronized Connection getConnection() throws SQLServerException
    {
        long currentTimeMillis = System.currentTimeMillis();
        Connection connection;
        if(!unlocked.isEmpty())
        {
            Iterator<Connection> unlockedConnections = unlocked.keySet().iterator();
            while(unlockedConnections.hasNext())
            {
                connection = unlockedConnections.next();
                if(currentTimeMillis - unlocked.get(connection) > connectionExpirationTimeMillis)
                {
                    unlocked.remove(connection);
                    expire(connection);
                    connection = null;
                }
                else if(validate(connection))
                {
                    unlocked.remove(connection);
                    locked.put(connection, currentTimeMillis);
                    return connection;
                }
                else
                {
                    unlocked.remove(connection);
                    expire(connection);
                }
            }

        }
        connection = create();
        locked.put(connection, currentTimeMillis);
        System.out.println(currentTimeMillis);
        System.out.println(connectionExpirationTimeMillis);
        System.out.println(connection);
        return connection;
    }

    public void releaseConnection(Connection con)
    {
        locked.remove(con);
        unlocked.put(con, System.currentTimeMillis());
    }


    protected Connection create() throws SQLServerException{
    return dataSource.getConnection();
    }


    public void expire(Connection o) {
        try
        {
            o.close();
        }
        catch(SQLException ex)
        {
            LOGGER.error("Unable to close the connection", ex);
        }
    }


    public boolean validate(Connection o) {
        try {
            return !(o.isClosed());
        } catch (SQLException e) {
            LOGGER.error("Unable to check if connection is closed", e);
            return false;
        }
    }


}

