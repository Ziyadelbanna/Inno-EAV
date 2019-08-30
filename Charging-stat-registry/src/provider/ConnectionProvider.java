package provider;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionProvider {
	private static Connection connect = null;
	static{  
		try{  
			Class.forName(Provider.DRIVER).newInstance();  
			connect = DriverManager.getConnection(Provider.CONNECTION_URL, Provider.USERNAME, Provider.PASSWORD);  
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static Connection getConnection(){
		if (connect == null) {
			try{  
				Class.forName(Provider.DRIVER);  
				connect = DriverManager.getConnection(Provider.CONNECTION_URL, Provider.USERNAME, Provider.PASSWORD);  
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	    return connect;
	}
	
}
