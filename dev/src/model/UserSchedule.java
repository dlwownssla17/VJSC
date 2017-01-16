package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class UserSchedule {
	private int capacity;
	private HashMap<Date, ArrayList<ScheduleItem>> items;
	
	public UserSchedule(int capacity) {
		this.capacity = capacity;
		this.items = new HashMap<>();
	}
	
	private boolean isFull() {
		return this.items.size() >= this.capacity;
	}
}
