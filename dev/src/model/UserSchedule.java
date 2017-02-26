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
	
	public int getCapacity() {
		return this.capacity;
	}
	
	private boolean isFull() {
		return this.items.size() >= this.capacity;
	}
	
	public HashMap<Date, ArrayList<ScheduleItem>> getItems() {
		return this.items;
	}
	
	private boolean containsDate(Date date) {
		return this.items.containsKey(date);
	}
	
	public ArrayList<ScheduleItem> lookup(Date date) {
		if (!containsDate(date)) return null;
		
		return this.items.get(date);
	}
	
	public void addScheduleItem(Date date, ScheduleItem scheduleItem) {
		if (!containsDate(date)) this.items.put(date, new ArrayList<>());
		
		// make sure that the schedule items are sorted in order of start date time
		ArrayList<ScheduleItem> listOfScheduleItems = this.items.get(date);
		boolean scheduleItemAdded = false;
		for (int i = 0; i < listOfScheduleItems.size(); i++) {
			if (scheduleItem.getStartDateTime().before(listOfScheduleItems.get(i).getStartDateTime())) {
				listOfScheduleItems.add(i, scheduleItem);
				scheduleItemAdded = true;
				break;
			}
		}
		if (!scheduleItemAdded) listOfScheduleItems.add(scheduleItem);
	}
	
	public boolean removeScheduleItem(Date date, ScheduleItem scheduleItem) {
		if (!containsDate(date)) return false;
		
		return this.items.get(date).remove(scheduleItem);
	}
}
