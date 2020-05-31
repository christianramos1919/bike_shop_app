package utils;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionUtil {
	//singleton for returning a single database connection
	private static ConnectionUtil cu = null;
	private static Properties prop;
	private ConnectionUtil() {
		prop = new Properties();
		try {
			// using the class loader to get our properties file
			// then we don't have to rely on the file system
			InputStream dbProperties = ConnectionUtil.class.getClassLoader().
					getResourceAsStream("database.properties");
			prop.load(dbProperties);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static synchronized ConnectionUtil getConnectionUtil() {
		if (cu == null)
			cu = new ConnectionUtil();
		return cu;
	}
	
	public Connection getConnection() {
		Connection conn = null;
		try {
			// registering our database driver class
			Class.forName(prop.getProperty("drv"));
			conn = DriverManager.getConnection(
						prop.getProperty("url"),
						prop.getProperty("usr"),
						prop.getProperty("psw")
					);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}

