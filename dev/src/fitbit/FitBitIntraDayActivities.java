package fitbit;

/**
 * FitBit IntraDay Activities are activities that can return data during the
 * progression of the day.
 *
 */

public enum FitBitIntraDayActivities {
	CALORIES("calories"), STEPS("steps"), DISTANCE("distance"), FLOORS("floors"), ELEVATION("elevation");
	
	private String name;
	
	private FitBitIntraDayActivities(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}