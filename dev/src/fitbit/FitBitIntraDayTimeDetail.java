package fitbit;

/**
 * FitBitTimeDetail is used to pick how spaced apart data is for IntraDay data.
 * Activities use 1min or 15min, but heart data uses 1sec or 1min.
 *
 */

public enum FitBitIntraDayTimeDetail {
	SECONDS("1sec"), MINUTES("1min"), FIFTEEN_MINUTES("15min");
	
	private String time;
	
	private FitBitIntraDayTimeDetail(String time) {
		this.time = time;
	}
	
	@Override
	public String toString() {
		return time;
	}
}
