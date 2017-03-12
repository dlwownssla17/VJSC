package route;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import db.DBTools;
import model.User;

public class Server {
	public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        
        server.createContext("/register", new RegisterHandler());
        server.createContext("/login", new LoginHandler());
        server.createContext("/logout", new LogoutHandler());
        
        server.setExecutor(null); // creates a default executor
        server.start();
	}
	
	static class RegisterHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange t) throws IOException {
			String requestMethod = t.getRequestMethod();
			if (requestMethod.equals("GET")) {
				
			} else if (requestMethod.equals("POST")) {
				Headers headers = t.getRequestHeaders();
				String username = headers.getFirst("Username");
				String password = headers.getFirst("Password");
				
				User newUser = DBTools.registerUser(username, password);
				
				String response = "";
				int rc = newUser != null ? 200 : 401;
				if (rc == 200) {
					System.out.println(String.format("Register SUCCESS: %s - %s", username, password));
				} else {
					System.out.println("Register FAILURE");
				}
				t.sendResponseHeaders(rc, response.getBytes().length);
	            OutputStream os = t.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
			} else {
				
			}
		}
		
	}
	
	static class LoginHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange t) throws IOException {
			String requestMethod = t.getRequestMethod();
			if (requestMethod.equals("GET")) {
				
			} else if (requestMethod.equals("POST")) {
				Headers headers = t.getRequestHeaders();
				String username = headers.getFirst("Username");
				String password = headers.getFirst("Password");
				
				User existingUser = DBTools.loginUser(username, password);
				
				String response = "";
				int rc = existingUser != null ? 200 : 401;
				t.sendResponseHeaders(rc, response.getBytes().length);
	            OutputStream os = t.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
			} else {
				
			}
		}
		
	}
	
	static class LogoutHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange t) throws IOException {
			String requestMethod = t.getRequestMethod();
			if (requestMethod.equals("GET")) {
				
			} else if (requestMethod.equals("POST")) {
				
				// nothing for now
				
				String response = "";
				t.sendResponseHeaders(200, response.getBytes().length);
	            OutputStream os = t.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
			} else {
				
			}
		}
		
	}
}
