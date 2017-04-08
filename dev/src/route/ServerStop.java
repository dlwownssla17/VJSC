package route;

import java.net.HttpURLConnection;
import java.net.URL;

public class ServerStop {
	public static void main(String[] args) {
		HttpURLConnection connection = null;
		try {
			URL url = new URL("http://localhost:8000/stop");
			connection = (HttpURLConnection) url.openConnection();
		    connection.setRequestMethod("POST");
		    connection.setRequestProperty("Content-Type", 
		        "application/x-www-form-urlencoded");
		    
		    connection.setRequestProperty("Admin-Key", "jms");
		    
		    connection.setUseCaches(false);
		    connection.setDoOutput(true);
		    
		    int rc = connection.getResponseCode();
		    System.out.println(String.format("Response Code: %d", rc));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) connection.disconnect();
		}
	}
}
