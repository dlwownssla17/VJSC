package fitbit;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import db.DBTools;
import model.User;
import util.Connection;

public class FitbitAccount {
	private String associatedUsername;
	private String accessToken;
	private String refreshToken;
	private String scope;
	
	private boolean hasActivityAccess, hasSleepAccess, hasProfileAccess, hasHeartRateAccess;
	
	public FitbitAccount(String associatedUsername, String accessToken, String refreshToken, String scope) {
		this.associatedUsername = associatedUsername;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.scope = scope;
		this.processScope();
	}
	
	private void processScope() {
		Set<String> scopeSet = new HashSet<String>(Arrays.asList(this.scope.split(" ")));
		this.hasActivityAccess = scopeSet.contains(FitbitAccess.ACTIVITY.toString());
		this.hasSleepAccess = scopeSet.contains(FitbitAccess.SLEEP.toString());
		this.hasProfileAccess = scopeSet.contains(FitbitAccess.PROFILE.toString());
		this.hasHeartRateAccess = scopeSet.contains(FitbitAccess.HEARTRATE.toString());
	}
	
	private boolean updateAccountData(String jsonData) throws JSONException {
		JSONObject jsonObj = new JSONObject(jsonData);
		this.accessToken = jsonObj.getString("access_token");
		this.refreshToken = jsonObj.getString("refresh_token");
		this.scope = jsonObj.getString("scope");
		return true;
	}
	
	public boolean persistAccountData() throws IOException {
		User user = DBTools.findUser(this.associatedUsername);
		DBTools.updateUserFitbitAccount(user);
		return true;
	}
	
	protected boolean hasAccess(FitbitAccess access) {
		switch(access) {
			case ACTIVITY: return this.hasActivityAccess;
			case SLEEP: return this.hasSleepAccess;
			case PROFILE: return this.hasProfileAccess;
			case HEARTRATE: return this.hasHeartRateAccess;
			default: return false;
		}
	}
	
	protected boolean refreshAccessToken() throws MalformedURLException, IOException, JSONException, FitbitException {
        URL url = new URL(FitbitTools.OAuth2URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        
        connection.setRequestMethod("POST");
        
        String authorizationHeader = String.format("Basic %s", FitbitTools.DebraAuthorizationHeader);
        connection.addRequestProperty("Authorization", authorizationHeader);
        connection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        String grantTypeParameter = "grant_type=refresh_token";
        String refreshTokenParameter = String.format("refresh_token=%s", this.refreshToken);
        String urlParameters = String.format("%s&%s", grantTypeParameter, refreshTokenParameter);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        connection.addRequestProperty("Content-Length", Integer.toString(postData.length));
        
        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        
        DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
        dos.write(postData);
        
        int rc = connection.getResponseCode();
        if (rc != 200) throw new FitbitException(
    			String.format("refreshAccessToken: Unexpected connection response code = %d.", rc));
        
        String jsonResponseString = Connection.getResponseString(connection);
    	
    	return this.updateAccountData(jsonResponseString) && this.persistAccountData();
	}
	
	public String getAssociatedUsername() {
		return this.associatedUsername;
	}
	
	public String setAssociatedUsername(String associatedUsername) {
		this.associatedUsername = associatedUsername;
		return this.associatedUsername;
	}
	
	public String getAccessToken() {
		return this.accessToken;
	}
	
	public String setAccessToken(String accessToken) {
		this.accessToken = accessToken;
		return this.accessToken;
	}
	
	public String getRefreshToken() {
		return this.refreshToken;
	}
	
	public String setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
		return this.refreshToken;
	}
	
	public String getScope() {
		return this.scope;
	}
	
	public String setScope(String scope) {
		this.scope = scope;
		return this.scope;
	}
}
