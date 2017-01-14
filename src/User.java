import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class User {
    public String accessToken;
    public String refreshToken;
    public String userId;
    public String scope;
    public String fileName;
    public boolean hasActivityAccess = false;
    public boolean hasSleepAccess = false;
    public boolean hasProfileAccess = false;
    public boolean hasHeartRateAccess = false;

    private String OAuth2URL = "https://api.fitbit.com/oauth2/token";
    private String DebraAuthorizationHeader = "MjI4NzVSOjBmZDM0YTdjZTFiMGY5ZjMwOTc4OTA0Mzc1MGI0MmQ0";

    public User(String accessToken, String refreshToken, String userId, String scope, String fileName) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userId = userId;
        this.scope = scope;
        this.fileName = fileName;
    }

    public User(String fileName) {
        String[] data = this.readDataFromFile(fileName);
        this.fileName = fileName;
        if (data == null) {
            System.out.println("Error: Could not read user authentication file.");
            return;
        }
        this.accessToken = data[0];
        this.refreshToken = data[1];
        this.userId = data[2];
        this.scope = data[3];
        processUserAccess();
    }

    public boolean refreshAccessToken() {
        HttpURLConnection connection = null;
        URL url;
        try {
            url = new URL(OAuth2URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            String authorizationHeader = "Basic " + DebraAuthorizationHeader;
            connection.addRequestProperty("Authorization", authorizationHeader);
            connection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            String grantTypeParameter = "grant_type=refresh_token";
            String refreshTokenParameter = "refresh_token=" + refreshToken;
            String urlParameters = grantTypeParameter + "&" + refreshTokenParameter;
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            int postDataLength = postData.length;
            connection.addRequestProperty("Content-Length", Integer.toString(postDataLength));
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            try(DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.write(postData);
            }

            int responseCode = connection.getResponseCode();
            StringBuffer response = new StringBuffer();
            if (responseCode == 200) {
                String inputLine;
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                while((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                String[] responseData = parseJSON(response.toString());
                this.accessToken = responseData[0];
                this.refreshToken = responseData[1];
                this.userId = responseData[2];
                this.scope = responseData[3];
                //return response.toString();
                if (!writeDataToFile()) {
                    return false;
                }
                System.out.println("Successfully Refreshed Access Token!");
                return true;
            } else {
                System.out.println(responseCode);
            }
            return false;
        } catch (MalformedURLException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    private String[] parseJSON(String data) {
        String accessTokenTag = "\"access_token\"";
        int accessTokenTagStart = data.indexOf(accessTokenTag);
        int accessTokenTagEnd = accessTokenTagStart + accessTokenTag.length();
        int accessTokenStart = data.indexOf("\"", accessTokenTagEnd);
        int accessTokenEnd = data.indexOf("\"", accessTokenStart + 1);
        String accessToken = data.substring(accessTokenStart + 1, accessTokenEnd);
        //System.out.println(accessToken);

        String refreshTokenTag = "\"refresh_token\"";
        int refreshTokenTagStart = data.indexOf(refreshTokenTag);
        int refreshTokenTagEnd = refreshTokenTagStart + refreshTokenTag.length();
        int refreshTokenStart = data.indexOf("\"", refreshTokenTagEnd);
        int refreshTokenEnd = data.indexOf("\"", refreshTokenStart + 1);
        String refreshToken = data.substring(refreshTokenStart + 1, refreshTokenEnd);
        //System.out.println(refreshToken);

        String scopeTag = "\"scope\"";
        int scopeTagStart = data.indexOf(scopeTag);
        int scopeTagEnd = scopeTagStart + scopeTag.length();
        int scopeStart = data.indexOf("\"", scopeTagEnd);
        int scopeEnd = data.indexOf("\"", scopeStart + 1);
        String scope = data.substring(scopeStart + 1, scopeEnd);
        //System.out.println(scope);

        String userIDTag = "\"user_id\"";
        int userIDTagStart = data.indexOf(userIDTag);
        int userIDTagEnd = userIDTagStart + userIDTag.length();
        int userIDStart = data.indexOf("\"", userIDTagEnd);
        int userIDEnd = data.indexOf("\"", userIDStart + 1);
        String userID = data.substring(userIDStart + 1, userIDEnd);
        //System.out.println(userID);

        String[] results = new String[4];
        results[0] = accessToken;
        results[1] = refreshToken;
        results[2] = userID;
        results[3] = scope;
        return results;
    }

    private void processUserAccess() {
        this.hasProfileAccess = this.scope.contains("profile");
        this.hasActivityAccess = this.scope.contains("activity");
        this.hasHeartRateAccess = this.scope.contains("heartrate");
        this.hasSleepAccess = this.scope.contains("sleep");
    }

    public boolean hasAccess(String content) {
        if (content == "sleep") {
            return this.hasSleepAccess;
        } else if (content == FitBitActivities.HeartRate) {
            return this.hasHeartRateAccess;
        } else if (content == "profile") {
            return this.hasProfileAccess;
        } else {
            return this.hasActivityAccess;
        }
    }

    private boolean writeDataToFile() {
        try{
            PrintWriter writer = new PrintWriter(this.fileName, "UTF-8");
            writer.println(this.accessToken);
            writer.println(this.refreshToken);
            writer.println(this.userId);
            writer.println(this.scope);
            writer.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private String[] readDataFromFile(String fileName) {
        FileInputStream fis;
        try {
            fis = new FileInputStream(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line = null;
            String results = "";
            while ((line = br.readLine()) != null) {
                results = results + line + "\n";
            }
            if (results.length() == 0) {
                br.close();
                return null;
            }
            results = results.substring(0, results.length() - 1);
            String[] parts = results.split("\n");
            if (parts == null || parts.length != 4) {
                br.close();
                return null;
            }
            br.close();
            return parts;
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }
}
