import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import util.DayBasedRecurringTime;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import errors.ErrorCodes;
import errors.InvalidRecurrenceException;
import model.ScheduleItem;
import model.User;

public class DEBRA {
	private User user;
	
	private boolean isNumeric(String s) {
		if (s == null || s.isEmpty()) { return false; }
		for (int i = 0; i < s.length(); i++) {
			int val = s.charAt(i)-'0';
			if (val < 0 || val > 9) { return false; }
		}
		return true;
	}
	
	/*
	 * @param args[0] is a description of the schedule item
	 * @param args[1-n] of the format x-y, where x corresponds to day (0-6) and y corresponds to time (0-2400) 
	 * @returns schedule item's ID (non-negative number) or an error (negative number)
	 */
	private String createScheduleItem(String[] args) {
		if (args.length <= 1) { return ErrorCodes.ID_CREATE_SCHEDULE_ITEM_NO_SCHEDULE; }
		String description = args[0];
		
		List<ImmutableList<Integer>> recurringTimesList = new ArrayList<>();
		for (int i = 1; i < args.length; i++) {
			String[] part = args[i].split("-");
			
			if (part.length != 2 || !isNumeric(part[0]) || !isNumeric(part[1])) {
				return ErrorCodes.ID_CREATE_SCHEDULE_ITEM_INVALID_TIME;
			}
			
			int day = Integer.parseInt(part[0]);
			int time = Integer.parseInt(part[1]);
			ImmutableList<Integer> dayTime = ImmutableList.<Integer>builder()
				.add(day)
				.add(time)
				.build();
			recurringTimesList.add(dayTime);
		}
		
		// TODO(vivekaraj): This is not an ideal way to control logic. Think of better way
		DayBasedRecurringTime recurringTimes;
		try {
			recurringTimes = new DayBasedRecurringTime(recurringTimesList);
		} catch (InvalidRecurrenceException e) {
			return ErrorCodes.ID_CREATE_SCHEDULE_ITEM_INVALID_TIME;
		}
		
		ScheduleItem item = new ScheduleItem(user, description, recurringTimes);
		System.out.println(item.toString());
		int status = user.addScheduleItem(item);
		if (status < 0) { return "" + status; }
		return "" + item.getID();
	}
	
	public String functionCall(int functionID, String[] args) {
		switch (functionID) {
			case 0:
				return createScheduleItem(args);
			default: 
				return ErrorCodes.ID_INVALID_FUNCTION;
		}
	}
	
	public String processInput(String input) {
		String[] parameters = input.split(",");
		
		if (!isNumeric(parameters[0])) {
			return ErrorCodes.ID_INVALID_FUNCTION;
		}
		
		int functionID = Integer.parseInt(parameters[0]);
		String[] args = new String[parameters.length-1];
		for (int i = 1; i < parameters.length; i++) {
			args[i-1] = parameters[i];
		}
		
		return functionCall(functionID, args);		
	}
	
	public static void main(String[] args) {
		// TEST CREATE SCHEDULE ITEM
		String[] test = { 
			"Vivek's test case", // schedule item description
			"1-1000", 
			"3-400",				
		};
		
		DEBRA debra = new DEBRA();
		debra.user = new User("u1", "p1");
		System.out.println(debra.createScheduleItem(test));
		
		/*
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
		*/
	}
}
