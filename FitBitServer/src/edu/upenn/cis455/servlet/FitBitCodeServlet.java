package edu.upenn.cis455.servlet;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FitBitCodeServlet extends HttpServlet {
	
	public String makePostRequestToDebra2(String username, Hashtable<String, String> data) {
		try {
			String urlParameters = "";
			urlParameters += "Access-Token=" + data.get("\"access_token\"") + "&";
			urlParameters += "Refresh-Token=" + data.get("\"refresh_token\"") + "&";
			urlParameters += "Scope=" + data.get("\"scope\"");
			//String urlParameters  = "param1=a&param2=b&param3=c";
			byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
			int    postDataLength = postData.length;
			String request        = "http://ec2-34-205-71-82.compute-1.amazonaws.com:8000/fitbit/save-metadata";
			URL url;
			url = new URL( request );
			HttpURLConnection conn= (HttpURLConnection) url.openConnection();           
			conn.setDoOutput( true );
			conn.setInstanceFollowRedirects( false );
			conn.setRequestMethod( "POST" );
			//conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
			//conn.setRequestProperty( "charset", "utf-8");
			conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
			conn.setUseCaches( false );
			try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
			   wr.write( postData );
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String makePostRequestToDebra(String username, Hashtable<String, String> data) {
		HttpURLConnection connection = null;
		try {
		    //Create connection
		    URL url = new URL("http://ec2-34-205-71-82.compute-1.amazonaws.com:8000/fitbit/save-metadata");
		    connection = (HttpURLConnection) url.openConnection();
		    connection.setRequestMethod("POST");
		    connection.setRequestProperty("Username", 
		    		username);
		    String parameters = "";
		    parameters += "{\"Access-Token\":" + "\"" + data.get("\"access_token\"") + "\",";
		    parameters += "\"Refresh-Token\":" + "\"" + data.get("\"refresh_token\"") + "\",";
		    parameters += "\"Scope\":" + "\"" + data.get("\"scope\"") + "\"}";
		    connection.setRequestProperty("Content-Length", 
		    		"" + parameters.length());
		    
		    connection.setUseCaches(false);
		    connection.setDoOutput(true);
		    
		    DataOutputStream wr = new DataOutputStream (
		            connection.getOutputStream());
		        wr.writeBytes(parameters);
		        wr.close();

		    //Get Response  
		    InputStream is = connection.getInputStream();
		    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		    StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
		    String line;
		    while ((line = rd.readLine()) != null) {
		        response.append(line);
		        response.append('\r');
		    }
		    rd.close();
		    return response.toString();
		} catch (Exception e) {
		    e.printStackTrace();
		    return null;
		} finally {
		    if (connection != null) {
		      connection.disconnect();
		    }
		}
	}
	
	public String makePostRequest(String code) {
		HttpURLConnection connection = null;
		try {
		    //Create connection
		    URL url = new URL("https://api.fitbit.com/oauth2/token");
		    connection = (HttpURLConnection) url.openConnection();
		    connection.setRequestMethod("POST");
		    connection.setRequestProperty("Content-Type", 
			        "application/x-www-form-urlencoded");
		    connection.setRequestProperty("Authorization", 
		        "Basic MjI4NzVSOjBmZDM0YTdjZTFiMGY5ZjMwOTc4OTA0Mzc1MGI0MmQ0");
		    String parameters = "";
		    String debraURL = "http%3A%2F%2Fec2-34-205-71-82.compute-1.amazonaws.com%3A8080%2Fdebra%2Ffitbitcode";
		    parameters += "grant_type=authorization_code&redirect_uri=" + debraURL + "&clientId=22875R&code=";
		    parameters += code;
		    connection.setRequestProperty("Content-Length", 
		    		"" + parameters.length());
		    
		    connection.setUseCaches(false);
		    connection.setDoOutput(true);
		    
		    DataOutputStream wr = new DataOutputStream (
		            connection.getOutputStream());
		        wr.writeBytes(parameters);
		        wr.close();

		    //Get Response  
		    InputStream is = connection.getInputStream();
		    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		    StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
		    String line;
		    while ((line = rd.readLine()) != null) {
		        response.append(line);
		        response.append('\r');
		    }
		    rd.close();
		    return response.toString();
		} catch (Exception e) {
		    e.printStackTrace();
		    return null;
		} finally {
		    if (connection != null) {
		      connection.disconnect();
		    }
		}
	}
	
	public Hashtable<String, String> parseJSON(String response) {
		Hashtable<String, String> result = new Hashtable<String, String>();
		String accessTokenName = "\"access_token\"";
		int pos1 = response.indexOf(accessTokenName);
		if (pos1 == -1) {
			return null;
		}
		int pos2 = pos1 + accessTokenName.length() + 1;
		int pos3 = response.indexOf('\"', pos2);
		int pos4 = response.indexOf('\"', pos3 + 1);
		String accessToken = response.substring(pos3 + 1, pos4);
		result.put(accessTokenName, accessToken);
		
		String refreshTokenName = "\"refresh_token\"";
		pos1 = response.indexOf(refreshTokenName);
		if (pos1 == -1) {
			return null;
		}
		pos2 = pos1 + refreshTokenName.length() + 1;
		pos3 = response.indexOf('\"', pos2);
		pos4 = response.indexOf('\"', pos3 + 1);
		String refreshToken = response.substring(pos3 + 1, pos4);
		result.put(refreshTokenName, refreshToken);
		
		String scopeTokenName = "\"scope\"";
		pos1 = response.indexOf(scopeTokenName);
		if (pos1 == -1) {
			return null;
		}
		pos2 = pos1 + scopeTokenName.length() + 1;
		pos3 = response.indexOf('\"', pos2);
		pos4 = response.indexOf('\"', pos3 + 1);
		String scope = response.substring(pos3 + 1, pos4);
		result.put(scopeTokenName, scope);
		return result;
	}
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String query = request.getQueryString();
		String[] parts = query.split("=");
		String codeAll = parts[1];
		
		Cookie[] cookies = request.getCookies();
		Cookie c = null;
		String username = "";
		if (cookies != null) {
			for (int i = 0; i < cookies.length; ++i) {
				if (cookies[i].getName().equals("Debra-User")) {
					username = cookies[i].getValue();
				}
			}
		}
		//int codeEnd = codeAll.indexOf("#_=_");
		//String code = codeAll.substring(0, codeEnd);
		PrintWriter out = response.getWriter();
		out.println("<P>User: " + username + "</P>");
		String responseString = this.makePostRequest(codeAll);
		out.println("<P>Response: " + responseString + "</P>");
		Hashtable<String, String> result = this.parseJSON(responseString);
		out.println("<P>Code: " + codeAll + "</P>");
		if (result == null) {
			out.println("<P>ERROR</P>");
		} else {
			out.println("<P>Access Token: " + result.get("\"access_token\"") + "</P>");
			out.println("<P>Refresh Token: " + result.get("\"refresh_token\"") + "</P>");
			out.println("<P>Scope: " + result.get("\"scope\"") + "</P>");
			this.makePostRequestToDebra(username, result);
			System.out.println("Finished");
		}
		System.out.println("Finished");
	}
}
