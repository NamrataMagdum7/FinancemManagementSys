package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnUtil {

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Properties props = DBPropertyUtil.loadProperties();
            if (props == null) {
                System.out.println(" Failed to load DB properties.");
                return null;
            }

            String host = props.getProperty("host");
            String port = props.getProperty("port");
            String db = props.getProperty("database");
            String user = props.getProperty("user");
            String pass = props.getProperty("password");

            String url = "jdbc:mysql://" + host + ":" + port + "/" + db + "?useSSL=false";
            conn = DriverManager.getConnection(url, user, pass);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
