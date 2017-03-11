package model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import util.CreateLookupDate;
import util.DateAndCalendar;
import util.DateFormat;

public class UserSchedule {
	private int capacity;
	private User associatedUser;
	private HashMap<Date, ArrayList<ScheduleItem>> items;
	private HashMap<Date, Integer> dailyScores;
	private long scheduleIdCounter;
	private long recurringIdCounter;

	public UserSchedule(int capacity) {
		this.capacity = capacity;
		this.items = new HashMap<>();
		this.scheduleIdCounter = 0;
		this.recurringIdCounter = 0;
	}

	public UserSchedule(int capacity, User associatedUser) {
		this(capacity);
		this.associatedUser = associatedUser;
	}

	public int getCapacity() {
		return this.capacity;
	}

	private boolean isFull() {
		return this.items.size() >= this.capacity;
	}

	public User getAssociatedUser() {
		return this.associatedUser;
	}

	public User setAssociatedUser(User associatedUser) {
		this.associatedUser = associatedUser;
		return this.associatedUser;
	}

	public HashMap<Date, ArrayList<ScheduleItem>> getItems() {
		return this.items;
	}

	public void ensureDate(int year, int month, int day) {
		this.ensureDate(CreateLookupDate.getInstance(year, month, day));
	}

	public void ensureDate(Date date) {
		if (!this.items.containsKey(date))
			this.items.put(date, new ArrayList<>());
	}

	public ArrayList<ScheduleItem> getItemsForDate(int year, int month, int day) {
		return this.getItemsForDate(CreateLookupDate.getInstance(year, month, day));
	}

	public ArrayList<ScheduleItem> getItemsForDate(Date date) {
		this.ensureDate(date);

		return this.items.get(date);
	}

	public ArrayList<ScheduleItem> getActiveItemsForDate(int year, int month, int day) {
		return this.getActiveItemsForDate(CreateLookupDate.getInstance(year, month, day));
	}

	public ArrayList<ScheduleItem> getActiveItemsForDate(Date date) {
		this.ensureDate(date);

		ArrayList<ScheduleItem> activeItems = new ArrayList<>();
		for (ScheduleItem item : this.items.get(date)) {
			if (item.isActive())
				activeItems.add(item);
		}
		return activeItems;
	}

	// add schedule item according to frontend-backend protocol
	public synchronized void addScheduleItem(int year, int month, int day, int recurringType, int[] recurringValue,
			int endType, String endValue, String title, String description, String typeString, String startTimeString,
			String progressTypeString, int duration) {
		Date startDate = CreateLookupDate.getInstance(year, month, day);

		// recurrence
		ScheduleItemRecurrence recurrence = null;
		if (recurringType > 4) {
			throw new IllegalArgumentException("invalid recurringType.");
		} else if (recurringType >= 0) {
			Date recurrenceStartDateTime = scheduleItemStartDateTimeFromStartTimeString(startDate, startTimeString);
			if (endType == 0) { // never
				recurrence = new ScheduleItemRecurrence(this.recurringIdCounter++, recurringType, recurringValue,
						recurrenceStartDateTime);
			} else if (endType == 1) { // after Y occurrences
				recurrence = new ScheduleItemRecurrence(this.recurringIdCounter++, recurringType, recurringValue,
						recurrenceStartDateTime, Integer.parseInt(endValue));
			} else if (endType == 2) { // on T
				try {
					Date endDateTime = DateFormat.getDate(endValue, ModelTools.DATE_FORMAT);
					Calendar calendar = DateAndCalendar.dateToCalendar(endDateTime);
					calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startTimeString.substring(0, 2)));
					calendar.set(Calendar.MINUTE, Integer.parseInt(startTimeString.substring(3, 5)));
					calendar.set(Calendar.SECOND, Integer.parseInt(startTimeString.substring(6)));
					recurrence = new ScheduleItemRecurrence(this.recurringIdCounter++, recurringType, recurringValue,
							recurrenceStartDateTime, DateAndCalendar.calendarToDate(calendar));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} else {
				throw new IllegalArgumentException("invalid endType.");
			}
		}

		// add single schedule item instances
		for (Date date : this.computeRecurringDates(startDate, recurrence)) {
			this.addSingleScheduleItem(date, recurrence, title, description, typeString, startTimeString,
					progressTypeString, duration);
		}
	}

	// TODO: account for never ending recurrences
	// TODO: refactor
	private ArrayList<Date> computeRecurringDates(Date startDate, ScheduleItemRecurrence recurrence) {
		ArrayList<Date> recurringDates = new ArrayList<>();

		// not recurring
		if (recurrence == null) {
			recurringDates.add(startDate);
			return recurringDates;
		}

		Calendar calendar = DateAndCalendar.dateToCalendar(startDate);
		switch (recurrence.getRecurringType()) {
		case 0: // daily
			while (!calendar.after(DateAndCalendar.dateToCalendar(recurrence.getEndDateTime()))) {
				recurringDates.add(DateAndCalendar.calendarToDate(calendar));
				calendar.add(Calendar.DAY_OF_MONTH, 1);
			}
			break;
		case 1: // every X days
			while (!calendar.after(DateAndCalendar.dateToCalendar(recurrence.getEndDateTime()))) {
				recurringDates.add(DateAndCalendar.calendarToDate(calendar));
				calendar.add(Calendar.DAY_OF_MONTH, recurrence.getRecurringValue()[0]);
			}
			break;
		case 2: // every certain days of the week
			for (int dayOfWeek : recurrence.getRecurringValue()) {
				Calendar c = (Calendar) calendar.clone();

				if (c.get(Calendar.DAY_OF_WEEK) > dayOfWeek + 1) {
					c.add(Calendar.WEEK_OF_MONTH, 1);
				}
				c.set(Calendar.DAY_OF_WEEK, dayOfWeek + 1);

				while (!c.after(DateAndCalendar.dateToCalendar(recurrence.getEndDateTime()))) {
					recurringDates.add(DateAndCalendar.calendarToDate(c));
					c.add(Calendar.WEEK_OF_MONTH, 1);
				}
			}
			break;
		case 3: // weekly
			if (calendar.get(Calendar.DAY_OF_WEEK) > recurrence.getRecurringValue()[0] + 1) {
				calendar.add(Calendar.WEEK_OF_MONTH, 1);
			}
			calendar.set(Calendar.DAY_OF_WEEK, recurrence.getRecurringValue()[0] + 1);

			while (!calendar.after(DateAndCalendar.dateToCalendar(recurrence.getEndDateTime()))) {
				recurringDates.add(DateAndCalendar.calendarToDate(calendar));
				calendar.add(Calendar.WEEK_OF_MONTH, 1);
			}
			break;
		case 4: // monthly
			if (calendar.get(Calendar.DAY_OF_MONTH) > recurrence.getRecurringValue()[0]) {
				calendar.add(Calendar.MONTH, 1);
			}
			calendar.set(Calendar.DAY_OF_MONTH, recurrence.getRecurringValue()[0]);

			while (!calendar.after(DateAndCalendar.dateToCalendar(recurrence.getEndDateTime()))) {
				recurringDates.add(DateAndCalendar.calendarToDate(calendar));
				calendar.add(Calendar.MONTH, 1);
			}
			break;
		default:
			throw new IllegalArgumentException("invalid recurring type.");
		}

		return recurringDates;
	}

	private void addSingleScheduleItem(Date date, ScheduleItemRecurrence recurrence, String title, String description,
			String typeString, String startTimeString, String progressTypeString, int duration) {
		this.ensureDate(date);

		// create schedule item
		ScheduleItemType type = scheduleItemTypeFromString(typeString);
		Date startDateTime = scheduleItemStartDateTimeFromStartTimeString(date, startTimeString);
		Progress progress = scheduleItemProgressFromProgressTypeString(progressTypeString);
		ScheduleItem scheduleItem = new ScheduleItem(this.scheduleIdCounter++, title, description, type,
				this.associatedUser, startDateTime, progress, null); // TODO:
																		// notification
																		// params

		// set recurrence
		scheduleItem.setRecurrence(recurrence);

		// make sure that the schedule items are sorted in order of start date
		// time
		ArrayList<ScheduleItem> listOfScheduleItems = this.items.get(date);
		boolean scheduleItemAdded = false;
		for (int i = 0; i < listOfScheduleItems.size(); i++) {
			if (scheduleItem.getStartDateTime().before(listOfScheduleItems.get(i).getStartDateTime())) {
				listOfScheduleItems.add(i, scheduleItem);
				scheduleItemAdded = true;
				break;
			}
		}
		if (!scheduleItemAdded)
			listOfScheduleItems.add(scheduleItem);
	}

	// edit schedule item according to frontend-backend protocol
	public synchronized void editScheduleItem(int year, int month, int day, long id, String title, String description,
			String startTimeString, int duration) {
		Date date = CreateLookupDate.getInstance(year, month, day);
		this.ensureDate(date);

		for (ScheduleItem scheduleItem : this.items.get(date)) {
			if (scheduleItem.getId() == id) {
				scheduleItem.setTitle(title);
				scheduleItem.setDescription(description);
				Date startDateTime = scheduleItemStartDateTimeFromStartTimeString(year, month, day, startTimeString);
				scheduleItem.setStartDateTime(startDateTime);
				scheduleItem.setDuration(duration);
				break;
			}
		}
	}

	// remove schedule item according to frontend-backend protocol
	public synchronized void removeScheduleItem(int year, int month, int day, long id, long recurringId) {
		Date date = CreateLookupDate.getInstance(year, month, day);
		this.ensureDate(date);

		// remove only this instance
		if (recurringId == -1) {
			ArrayList<ScheduleItem> listOfScheduleItems = this.items.get(date);
			int removeIndex = -1;
			for (int i = 0; i < listOfScheduleItems.size(); i++) {
				if (listOfScheduleItems.get(i).getId() == id) {
					removeIndex = i;
					break;
				}
			}
			if (removeIndex >= 0)
				listOfScheduleItems.remove(removeIndex);
			return;
		}

		// remove all instances with the same recurring id from given day
		Calendar calendar = DateAndCalendar.dateToCalendar(date);
		for (Date existingDate : this.items.keySet()) {
			if (!calendar.after(DateAndCalendar.dateToCalendar(existingDate))) {
				ArrayList<ScheduleItem> listOfScheduleItems = this.items.get(existingDate);
				int removeIndex = -1;
				for (int i = 0; i < listOfScheduleItems.size(); i++) {
					if (listOfScheduleItems.get(i).getRecurrence().getRecurringId() == recurringId) {
						removeIndex = i;
						break;
					}
				}
				if (removeIndex >= 0)
					listOfScheduleItems.remove(removeIndex);
			}
		}
	}

	public synchronized int getDailyScore(int year, int month, int day) {
		return this.getDailyScore(CreateLookupDate.getInstance(year, month, day));
	}

	public synchronized int getDailyScore(Date date) {
		this.ensureDate(date);

		int dailyScore = 0;
		for (ScheduleItem item : this.items.get(date)) {
			dailyScore += item.getScore();
		}

		return dailyScore;
	}

	public synchronized void updateScore(int year, int month, int day, int score) {
		this.updateScore(CreateLookupDate.getInstance(year, month, day), score);
	}

	public synchronized void updateScore(Date date, int score) {
		this.ensureDate(date);
		this.dailyScores.put(date, score);
	}

	// POST /[user id]/update-daily-scores
	public void updateDailyScores(int year, int month, int day) {
		Calendar lastDayChecked = DateAndCalendar.dateToCalendar(CreateLookupDate.getInstance(year, month, day));
		Calendar oneDayAgo = Calendar.getInstance();
		oneDayAgo.add(Calendar.DAY_OF_MONTH, -1);

		Calendar calendar = (Calendar) lastDayChecked.clone();
		while (!calendar.after(oneDayAgo)) {
			Date date = DateAndCalendar.calendarToDate(calendar);
			this.updateScore(date, this.getDailyScore(date));
		}
	}

	public synchronized ProgressColor getProgressColor(int year, int month, int day) {
		return this.getProgressColor(CreateLookupDate.getInstance(year, month, day));
	}

	// TODO: account for white and grey colors
	public synchronized ProgressColor getProgressColor(Date date) {
		this.ensureDate(date);

		ArrayList<ScheduleItem> listOfScheduleItems = this.items.get(date);

		int greenCount = 0, yellowCount = 0, redCount = 0;
		if (listOfScheduleItems != null) {
			for (ScheduleItem item : listOfScheduleItems) {
				ProgressColor pc = item.getProgressColor();
				switch (pc) {
				case RED:
					redCount++;
					break;
				case YELLOW:
					yellowCount++;
					break;
				case GREEN:
					greenCount++;
					break;
				case WHITE:
					break;
				case GREY:
					break;
				default:
					throw new IllegalStateException();
				}
			}
		}

		// simple majority ruling; break ties in favor of green > yellow > red
		if (Math.max(Math.max(greenCount, yellowCount), redCount) == 0)
			return ProgressColor.GREY;
		if (greenCount >= yellowCount && greenCount >= redCount)
			return ProgressColor.GREEN;
		if (yellowCount >= redCount)
			return ProgressColor.YELLOW;
		return ProgressColor.RED;
	}

	public static ScheduleItemType scheduleItemTypeFromString(String typeString) {
		switch (typeString) {
		case "eating":
			return ScheduleItemType.EATING;
		case "exercise":
			return ScheduleItemType.EXERCISE;
		case "medication":
			return ScheduleItemType.MEDICATION;
		case "blood_glucose_measurement":
			return ScheduleItemType.BG_MEASUREMENT;
		default:
			throw new IllegalArgumentException("invalid type string.");
		}
	}

	private static void updateStartTime(Calendar calendar, String startTimeString) {
		calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startTimeString.substring(0, 2)));
		calendar.set(Calendar.MINUTE, Integer.parseInt(startTimeString.substring(3, 5)));
		calendar.set(Calendar.SECOND, Integer.parseInt(startTimeString.substring(6)));
	}

	public static Date scheduleItemStartDateTimeFromStartTimeString(int year, int month, int day,
			String startTimeString) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		updateStartTime(calendar, startTimeString);
		return DateAndCalendar.calendarToDate(calendar);
	}

	public static Date scheduleItemStartDateTimeFromStartTimeString(Date date, String startTimeString) {
		Calendar calendar = DateAndCalendar.dateToCalendar(date);
		updateStartTime(calendar, startTimeString);
		return DateAndCalendar.calendarToDate(calendar);
	}

	public static Progress scheduleItemProgressFromProgressTypeString(String progressTypeString) {
		switch (progressTypeString) {
		case "boolean":
			return new BooleanProgress();
		case "percentage":
			return new PercentageProgress();
		default: // not implement levels progress type yet
			throw new IllegalArgumentException("invalid progress type string.");
		}
	}
}
