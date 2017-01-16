package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class Connection {
	public static String getResponseString(HttpURLConnection connection) throws IOException {
		StringBuffer sb = new StringBuffer();
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    	String line;
    	while ((line = in.readLine()) != null) {
    		sb.append(line);
    	}
    	in.close();
    	
    	return sb.toString();
	}
}
