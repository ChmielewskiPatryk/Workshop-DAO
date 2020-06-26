import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ConcurrentModificationException;

public class DBUtil {
    final static String DB_USER = "root";
    final static String DB_PASS ="coderslab";

    public static Connection connect (String dbName) throws SQLException {
        String DB_URL ="jdbc:mysql://localhost:3306/" + dbName + "?useSSL=false&characterEncoding=utf8";
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

}
