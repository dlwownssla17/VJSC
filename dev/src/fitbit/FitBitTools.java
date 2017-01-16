package fitbit;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;

import util.Connection;

public class FitBitTools {
	protected static String OAuth2URL = "https://api.fitbit.com/oauth2/token";
	protected static String DebraAuthorizationHeader = "MjI4NzVSOjBmZDM0YTdjZTFiMGY5ZjMwOTc4OTA0Mzc1MGI0MmQ0";
	// TODO store DebraAuthorizationHeader somewhere else
	
	private static String FitBitAPIUserURL = "https://api.fitbit.com/1/user/-/";
	private static String jsonExtension = ".json";
	
	private static String fitBitAPISendHTTPRequest(FitBitAccount fitBitAccount, String targetURL) {
		URL url;
		HttpURLConnection connection = null;
        try {
        	url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            
            String authorizationHeader = "Bearer " + fitBitAccount.getAccessToken();
            connection.setRequestProperty("Authorization", authorizationHeader);
            
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            int rc = connection.getResponseCode();
            if (rc == 401) fitBitAccount.refreshAccessToken();
            
            if (rc == 200) return Connection.getResponseString(connection);
            else if (rc == 429) throw new FitBitException(
            			String.format("fitBitAPISendHTTPRequest: Reached FitBit API Limit. (response code = %d)", rc));
            else throw new FitBitException(
            			String.format("fitBitAPISendHTTPRequest: Unexpected connection response code = %d.", rc));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
        	e.printStackTrace();
        } catch (FitBitException e) {
        	e.printStackTrace();
        	System.out.println(e.getMessage());
        }
        return null;
	}
	
	
}
