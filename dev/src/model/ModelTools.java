package model;

public class ModelTools {
	public static double DEFAULT_YELLOW_THRESHOLD = 0.5;
	public static double DEFAULT_GREEN_THRESHOLD = 0.8;
	
	public static int DEFAULT_CAPACITY = 365;
	
	public static int START_ACTIVE_MINUTES_BEFORE = 5;
	public static int START_ACTIVE_MINUTES_AFTER = 30;
	public static int END_ACTIVE_MINUTES_BEFORE = 5;
	public static int END_ACTIVE_MINUTES_AFTER = 30;
	
	public static int MODIFIABLE_AFTER_CREATE = 10;
	public static int NOT_MODIFIABLE_BEFORE_START = 120;
	
	public static String DATE_FORMAT = "yyyy-MM-dd";
	public static String TIME_FORMAT = "HH:mm:ss";
	public static String DATE_TIME_FORMAT = String.format("%s %s", DATE_FORMAT, TIME_FORMAT);
}
