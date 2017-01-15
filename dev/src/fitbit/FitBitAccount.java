package fitbit;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import util.IO;

/* TODO refresh account data involving database, as opposed to metadata file */

public class FitBitAccount {
	private String accessToken;
	private String refreshToken;
	private String userId;
	private String scope;
	private String tokenType;
	private long expiredIn;
	
	public boolean hasActivityAccess, hasSleepAccess, hasProfileAccess, hasHeartRateAccess;
	
	private String clientSideAccountDataFileName;
	
	/* This constructor is currently not used (until TODO above is handled) */
	public FitBitAccount(String accessToken, String refreshToken, String userId, String scope,
			String tokenType, long expiredIn) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.userId = userId;
		this.scope = scope;
		this.tokenType = tokenType;
		this.expiredIn = expiredIn;
		processScope();
	}
	
	public FitBitAccount(String clientSideAccountDataFileName) {
		this.clientSideAccountDataFileName = clientSideAccountDataFileName;
		String[] accountData = IO.readFile(clientSideAccountDataFileName).split("\n");
		
		this.accessToken = accountData[0];
		this.refreshToken = accountData[1];
		this.userId = accountData[2];
		this.scope = accountData[3];
		this.tokenType = accountData[4];
		this.expiredIn = Long.parseLong(accountData[5]);
		processScope();
	}
	
	private void processScope() {
		Set<String> scopeSet = new HashSet<String>(Arrays.asList(this.scope.split(" ")));
		this.hasActivityAccess = scopeSet.contains("activity");
		this.hasSleepAccess = scopeSet.contains("sleep");
		this.hasProfileAccess = scopeSet.contains("profile");
		this.hasHeartRateAccess = scopeSet.contains("heartrate");
	}
	
	/* TODO change this to involve database instead of file */
	private boolean updateAccountData() {
		String accountData = String.format("%s\n%s\n%s\n%s\n%s\n%d\n", this.accessToken, this.refreshToken,
				this.userId, this.scope, this.tokenType, this.expiredIn);
		return IO.writeFile(this.clientSideAccountDataFileName, accountData);
	}
	
	protected boolean hasAccess(FitBitAccess access) {
		switch(access) {
			case ACTIVITY: return this.hasActivityAccess;
			case SLEEP: return this.hasSleepAccess;
			case PROFILE: return this.hasProfileAccess;
			case HEARTRATE: return this.hasHeartRateAccess;
			default: return false;
		}
	}
	
	protected boolean refreshAccessToken() {
        URL url;
        HttpURLConnection connection = null;
        try {
        	url = new URL(FitBitTools.OAuth2URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            
            String authorizationHeader = "Basic " + FitBitTools.DebraAuthorizationHeader;
            connection.addRequestProperty("Authorization", authorizationHeader);
            connection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            String grantTypeParameter = "grant_type=refresh_token";
            String refreshTokenParameter = "refresh_token=" + this.refreshToken;
            String urlParameters = grantTypeParameter + "&" + refreshTokenParameter;
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            connection.addRequestProperty("Content-Length", Integer.toString(postData.length));
            
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            
            try(DataOutputStream dos = new DataOutputStream(connection.getOutputStream())) {
                dos.write(postData);
            }
            
            int rc = connection.getResponseCode();
            if (rc == 200) {
            	StringBuffer jsonResponseSb = new StringBuffer();
            	BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            	String currentLine;
            	while ((currentLine = in.readLine()) != null) {
            		jsonResponseSb.append(currentLine);
            	}
            	in.close();
            	
            	System.out.println(jsonResponseSb.toString());
            	
            	return true; // will be changed
            } else {
            	System.out.println("Unexpected connection response code: " + rc);
            	System.out.println(connection.getResponseMessage());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
	}
	
	public static void main(String[] args) {
		FitBitAccount acc = new FitBitAccount("userAuthentication.txt");
		acc.refreshAccessToken();
	}
}
