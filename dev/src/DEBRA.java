import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

public class DEBRA {
	private boolean isNumeric(String s) {
		if (s == null || s.isEmpty()) { return false; }
		for (int i = 0; i < s.length(); i++) {
			int val = s.charAt(i)-'0';
			if (val < 0 || val > 9) { return false; }
		}
		return true;
	}
	
	private String test1(String[] args) {
		return "These are your arguments: " + Arrays.toString(args);
	}
	
	public String functionCall(int functionID, String[] args) {
		switch (functionID) {
			case 0:
				return test1(args);
			default: 
				return ErrorCodes.INVALID_FUNCTION_ID;
		}
	}
	
	public String processInput(String input) {
		String[] parameters = input.split(",");
		
		if (!isNumeric(parameters[0])) {
			return ErrorCodes.INVALID_FUNCTION_ID;
		}
		
		int functionID = Integer.parseInt(parameters[0]);
		String[] args = new String[parameters.length-1];
		for (int i = 1; i < parameters.length; i++) {
			args[i-1] = parameters[i];
		}
		
		return functionCall(functionID, args);		
	}
	
	public static void main(String[] args) {
		String HOST_NAME = args[0];
		int PORT_NUMBER = Integer.parseInt(args[1]);
		
		DEBRA debra = new DEBRA();

		try {
			Socket socket = new Socket(HOST_NAME, PORT_NUMBER);
		    PrintWriter out =
		        new PrintWriter(socket.getOutputStream(), true);
		    BufferedReader in =
		        new BufferedReader(
		            new InputStreamReader(socket.getInputStream()));
		    BufferedReader stdIn =
		        new BufferedReader(
		            new InputStreamReader(System.in));
		    String userInput;
		    while ((userInput = in.readLine()) != null) {
		    	out.println(debra.processInput(userInput));
		    }
		    socket.close();
		} catch (IOException e) {
			
		}
	}
}
