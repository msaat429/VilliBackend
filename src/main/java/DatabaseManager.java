import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private static Connection getRemoteConnection() {
        if (System.getenv("RDS_HOSTNAME") != null) {
            try {
                Class.forName("org.postgresql.Driver");
                String dbName = System.getenv("RDS_DB_NAME");
                String userName = System.getenv("RDS_USERNAME");
                String password = System.getenv("RDS_PASSWORD");
                String hostname = System.getenv("RDS_HOSTNAME");
                String port = System.getenv("RDS_PORT");
                String jdbcUrl = "jdbc:postgresql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password;
                //logger.trace("Getting remote connection with connection string from environment variables.");
                Connection con = DriverManager.getConnection(jdbcUrl);
                //logger.info("Remote connection successful.");
                return con;
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
                //logger.warn(e.toString());
            }
            catch (SQLException e) {
                e.printStackTrace();
                //logger.warn(e.toString());
            }
        }
        return null;
    }

    public static void main(String[] args) throws SQLException {
        try {
            Connection con = getRemoteConnection();
            Statement stmt = con.createStatement();
            String sql = "CREATE TABLE ALLERGY " +
                    "(userID varchar(15) not NULL, " +
                    " restrictionID VARCHAR(15) not NULL, " +
                    " allergyName VARCHAR(128), " +
                    " sensitivity INTEGER, " +
                    " PRIMARY KEY ( restrictionID ))";
            stmt.execute(sql);
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }


}
