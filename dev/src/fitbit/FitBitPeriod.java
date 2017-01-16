package fitbit;

/**
 *  FitBit Periods are used to specify a length of time for the representation
 *  of data.  For example, if one wants to obtain data over the span of a month,
 *  the period to select would be month.
 */

public enum FitBitPeriod {
	DAY("1d"), WEEK("1w"), MONTH("1m"), THREE_MONTHS("3m"), SIX_MONTHS("6m"), YEAR("1y");
	
	private String period;
	
	private FitBitPeriod(String period) {
		this.period = period;
	}
	
	@Override
	public String toString() {
		return period;
	}
}