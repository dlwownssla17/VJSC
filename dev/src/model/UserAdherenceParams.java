package model;

import java.util.Arrays;
import java.util.Date;

public class UserAdherenceParams {
	private Date lastUpdated;
	
	// This parameter can be freely changed
	private static final int N = 7;
	
	private static final double S = 2;
	private static final double C = 1.5;
	
	private double[] lastNDaysLocalScore;	
	protected UserAdherenceParams() {
		lastUpdated = new Date(System.currentTimeMillis());
		lastNDaysLocalScore = new double[N];
	}
	
	private double globalScore;
	
	/*
	 * Returns the number of days between d1 and d2. If dateDiff >= NUM_PREV_DAYS, outputs -1
	 * This is a O(1) method
	 */
	private int absoluteDateDiff(Date d1, Date d2) {
		if (d1.compareTo(d2) > 0) {
			Date temp = d1;
			d1 = d2;
			d2 = temp;
		}
		
		// Ensures the inputs aren't corrupted by this function
		d1 = (Date) d1.clone();
		d2 = (Date) d2.clone();
		
		for (int i = 0; i < N; i++) {
			if (d1.getYear() == d2.getYear() && d1.getMonth() == d2.getMonth() && d1.getDate() == d2.getDate()) {
				return i;
			}
			d1.setDate(d1.getDate()+1);
		}
		
		
		return -1;
	}
	
	// Return true if and only if the date changes
	private boolean updateDate() {
		Date today = new Date(System.currentTimeMillis());
		int dateDiff = absoluteDateDiff(lastUpdated, today);
		if (dateDiff == 0) {
			// no need to update global score
			return false;
		} else if (dateDiff == -1) {
			lastNDaysLocalScore = new double[N];
		} else {
			for (int i = 0; i < N; i++) {
				if (i + dateDiff < N) {
					lastNDaysLocalScore[i] = lastNDaysLocalScore[i+dateDiff];
				} else {
					lastNDaysLocalScore[i] = 0;
				}
			}
		}
		lastUpdated = today;
		return true;
	}
	
	protected double getScore() {
		updateGlobalScore();
		return globalScore;
	}
	
	// Note: do not include today in global score yet. Wait for all results
	private void updateGlobalScore() {
		if (!updateDate()) {
			return;
		}

		// pastData.length == N-1. Used pastData.length so that no one accidentally uses "N" instead of "N-1"
		
		double[] pastData = Arrays.copyOf(lastNDaysLocalScore, lastNDaysLocalScore.length-1);

		double avg = 0.0;
		double expScoreSq = 0.0;
		for (int i = 0; i < pastData.length; i++) {
			avg += lastNDaysLocalScore[i];
			expScoreSq += lastNDaysLocalScore[i] * lastNDaysLocalScore[i];
		}
		avg /= pastData.length;
		expScoreSq /= pastData.length;
		
		double stdDev = Math.sqrt(expScoreSq - avg*avg);
		
		globalScore = 0;
		for (int i = 0; i < pastData.length; i++) {
			globalScore += (avg - pastData[i] > S * stdDev ? C : 1) * pastData[i];
		}
	}
	
	// Only change score by making a call to this method
	protected void changeScore(double delta) {
		updateDate();
		lastNDaysLocalScore[N-1] += delta;
	}
	
	public static void main(String[] args) {
		// for testing
		UserAdherenceParams params = new UserAdherenceParams();
		Date yesterday = new Date(System.currentTimeMillis());
		yesterday.setDate(yesterday.getDate()-1);
		params.lastUpdated = yesterday;
		for (int i = 0; i < N; i++) {
			params.lastNDaysLocalScore[i] = 12;
		}
		params.lastNDaysLocalScore[1] = 3;
		
		System.out.println(params.getScore() == 12*(N-2) + C*3);
	}
}
