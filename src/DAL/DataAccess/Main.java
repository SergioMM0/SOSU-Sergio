package DAL.DataAccess;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;

public class Main {
    public static void main(String args[]) {
        // Do something...


        // Create the ConnectionPool:
       // JDBCConnectionPool pool = new JDBCConnectionPool(
         //       "org.hsqldb.jdbcDriver", "jdbc:hsqldb://localhost/mydb",
           //     "sa", "secret");

        // Get a connection:
       // Connection con = pool.checkOut();

        // Use the connection


        // Return the connection:
        //pool.checkIn(con);

        JDBCConnectionPool d = new JDBCConnectionPool();

        try {
            d.getConnection();
        } catch (SQLServerException e) {
            e.printStackTrace();
        }

    }
}
