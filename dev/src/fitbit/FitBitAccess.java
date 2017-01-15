package fitbit;

public enum FitBitAccess {
	WEIGHT("weight"), SOCIAL("social"), ACTIVITY("activity"), SETTINGS("settings"), PROFILE("profile"),
	LOCATION("location"), HEARTRATE("heartrate"), SLEEP("sleep"), NUTRITION("nutrition");
	
	private String access;
	
	private FitBitAccess(String access) {
		this.access = access;
	}
	
	@Override
	public String toString() {
		return access;
	}
}
