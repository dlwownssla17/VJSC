package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ScheduleItem {
	int id;
	private String description;
	private User associatedUser;
	private Progress progress;
	private ScheduleItemNotificationParams notificationParams;
	
	private double yellowThreshold, greenThreshold;	
	
	synchronized int setID() {
		try {
			File file = new File("max_schedule_id");
			Scanner sc = new Scanner(file);
			int ret = sc.nextInt();
			sc.close();
			file = new File("max_schedule_id");
			FileWriter write = new FileWriter(file);
			write.write("" + (ret+1));
			write.close();
			return ret;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		throw new RuntimeException("Error in setting ID for schedule item");
	}
	
	public ScheduleItem(String description, double yellowThreshold, double greenThreshold)
			throws IllegalArgumentException {
		if (description.length() > 128)
			throw new IllegalArgumentException("The description should be 128 characters or less.");
		this.description = description;
		
		if (yellowThreshold < 0. || yellowThreshold > 0.999)
			throw new IllegalArgumentException("The yellow threshold must be between 0% and 99.9%.");
		if (greenThreshold < 0.001 || greenThreshold > 1.)
			throw new IllegalArgumentException("The green threshold must be between 0.1% and 100%.");
		if (yellowThreshold >= greenThreshold)
			throw new IllegalArgumentException("The yellow threshold must be below the green threshold.");
		this.yellowThreshold = yellowThreshold;
		this.greenThreshold = greenThreshold;
		id = setID();
	}
	
	public ScheduleItem(String description) throws IllegalArgumentException {
		this(description, 0.5, 0.8);
	}
}
