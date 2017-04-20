package fitbit;

public enum FitbitAccess {
	WEIGHT("weight"), SOCIAL("social"), ACTIVITY("activity"), SETTINGS("settings"), PROFILE("profile"),
	LOCATION("location"), HEARTRATE("heartrate"), SLEEP("sleep"), NUTRITION("nutrition");
	
	private String access;
	
	private FitbitAccess(String access) {
		this.access = access;
	}
	
	@Override
	public String toString() {
		return access;
	}
}
