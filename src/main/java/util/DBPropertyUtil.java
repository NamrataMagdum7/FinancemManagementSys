package util;

import java.io.InputStream;
import java.util.Properties;

public class DBPropertyUtil {

    public static Properties loadProperties() {
        Properties props = new Properties();
        try (InputStream input = DBPropertyUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                System.out.println(" Sorry, unable to find db.properties");
                return null;
            }
            props.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return props;
    }
}