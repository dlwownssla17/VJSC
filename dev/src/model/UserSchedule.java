package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserSchedule {
	private User user;
	private Set<ScheduleItem> items;
	
	
	public UserSchedule(User user) {
		this.user = user;
		this.items = new HashSet<>();
	}
	
	public User getUser() { return this.user; }
	
	public List<ScheduleItem> getScheduleItemsForDate(Date d) {
		List<ScheduleItem> ret = new ArrayList<>();
		for (ScheduleItem item : items) {
			if (item.happensOnDate(d)) {
				ret.add(item);
			}
		}
		Collections.sort(ret);
		return ret;
	}
	
	public void completedItem(int id) {
		user.getAdherenceParams().completedTask();
	}
	
	public void didntCompleteItem(int id) {
		user.getAdherenceParams().didntCompleteTask();
	}
	
	public void deleteItem(int id) {
		for (ScheduleItem item : items) {
			if (item.getID() == id) {
				user.getAdherenceParams().deletedTask();
				items.remove(item);
			}
		}
	}
	
	public void addItem(ScheduleItem item) {
		items.add(item);
	}
	
}
