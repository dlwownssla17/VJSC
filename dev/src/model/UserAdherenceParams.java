package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import util.Util;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

/* 
 * changeScore and getScore should be the only non-private methods. Score should only be change-able
 * by making a call to changeScore.	
*/
public class UserAdherenceParams {
	/*
	 * To disincentivize lying and reward gradual progress, MULT_MUCH_IMPROVEMENT is not much less than
	 * MULT_LOTS_OF_IMPROVEMENT.
	 * 
	 * Also, to not discourage, we don't penalize poor performance by too much.
	 */
	public static final double MULTIPLIER_LOTS_OF_IMPROVEMENT = 2.2;
	public static final double MULTIPLIER_MUCH_IMPROVEMENT = 2.;
	public static final double MULTIPLIER_NORMAL = 1.;
	public static final double MULTIPLIER_MUCH_DECREASE = .8;
	public static final double MULTIPLIER_LOTS_OF_DECREASE = .75;
	
	public static final ImmutableMap<Integer, Integer> CATEGORY_TO_SCORE = getCategoryToScore();	

	/*
	 *  We do not care about the "time" corresponding to the date object, so each Date object
	 *  must be set to 12AM. The constructor will ensure that this invariant holds.
	 */
	private Map<Date, Double> dateToScore;
	private Map<Integer, DebraStatistic> categoryToStats;
	
	private double globalScore;
	
	/*
	 * IllegalArgumentException is thrown if dates in the map are not all set to 12AM.
	 */
	protected UserAdherenceParams(Map<Date, Double> dateToScore, Map<Integer, DebraStatistic> categoryToStats) {
		if (dateToScore == null || categoryToStats == null) {
			throw new IllegalArgumentException("UserAdherenceParams: null inputs");
		}
		
		// ensures encapsulation
		this.dateToScore = new HashMap<>(dateToScore);
		this.categoryToStats = new HashMap<>(categoryToStats);
		
		Iterator<Date> dates = dateToScore.keySet().iterator();
		while (dates.hasNext()) {
			Date nextDate = dates.next();
			if (nextDate.getHours() + nextDate.getMinutes() + nextDate.getSeconds() != 0) {
				throw new IllegalArgumentException("UserAdherenceParams: all Dates in the map need to be set to 12AM, to simplify equality checking.");
			}
		}
	}
	
	public static Date getTodaysDateAtTwelveAM() {
		Date today = new Date(System.currentTimeMillis());
		today.setHours(0);
		today.setMinutes(0);
		today.setSeconds(0);
		return today;
	}
	
	public static double modifiedWeightedAveraging(List<Double> values) {
		if (values == null) {
			throw new IllegalArgumentException("modifiedWeightedAveraging: null input");
		}
		
		double numerator = 0;
		double denominator = 0;
		for (int i = 0; i < values.size(); i++) {
			numerator += (values.get(i) / (i+1));
			denominator += (1.0 / (i+1));
		}
		
		if (numerator == 0 || denominator == 0) { return 0; }
		return numerator / denominator;
	}
	
	/*
	 * Score is changed by multiplying categoryScore and multiplicity factor.
	 * categoryScore -- e.g. all food-related tasks have categoryScore of 100.
	 * multiplicity factor -- dependent on how well (or poorly) item does in comparison to past behavior.
	 */
	protected void changeScore(ScheduleItem item, double completionStatus) {
		if (item == null) {
			throw new IllegalArgumentException("changeScore: null input");
		}
		
		// Don't reward cheaters!
		if (isLying(item, completionStatus)) {
			return;
		}
		
		double scoreChange = CATEGORY_TO_SCORE.get(item.getCategory());
		
		double multiplier = MULTIPLIER_NORMAL;
		
		// Rewarding progress made on category-level
		DebraStatistic categoryStat = categoryToStats.get(item.getCategory());
		multiplier *= scoreMultiplier(completionStatus, categoryStat);
		
		// Rewarding progress made on a schedule-item-ID level
		DebraStatistic itemStat = item.getCompletionStats();
		multiplier *= scoreMultiplier(completionStatus, itemStat);
		
		/* Note: Rewarding progress made on a day-by-day basis: accounted for in global score */
		
		// Modify score in map
		Date today = getTodaysDateAtTwelveAM();
		double score = 0.;
		if (dateToScore.containsKey(today)) {
			score = dateToScore.get(today);
		}
		score += completionStatus * scoreChange * multiplier;
		dateToScore.put(today, score);
	}
	
	private double scoreMultiplier(double value, DebraStatistic stat) {
		if (stat == null) {
			throw new IllegalArgumentException("scoreMultiplier: null input");		
		}
		
		if (stat.isEmpty()) { return 1.; }
		
		double avg = stat.getAvg();
		double stdDev = stat.getStdDev();
		
		double multiplier = 1.0;
		
		if (value > avg + 2*stdDev) {
			multiplier = MULTIPLIER_LOTS_OF_IMPROVEMENT;
		} else if (value > avg + stdDev) {
			multiplier = MULTIPLIER_MUCH_IMPROVEMENT;
		} else if (value < avg - stdDev) {
			multiplier = MULTIPLIER_MUCH_DECREASE;
		} else if (value < avg - 2*stdDev) {
			multiplier = MULTIPLIER_LOTS_OF_DECREASE;
		} 
		stat.addEntry(value);
		
		return multiplier;
	}
	
	/*
	 * Return true if suspected lying.
	 */
	private boolean isLying(ScheduleItem item, double completionStatus) {
		if (item == null) {
			throw new IllegalArgumentException("isLying: null input");		
		}		
		// vivekaraj will incorporate this in a separate branch. This will not be needed for user test.
		
		return false;
	}
	
	/*
	 * Returns global score
	 */
	protected double getScore() {
		Date today = getTodaysDateAtTwelveAM();		
		
		// Global score only needs to be calculated at most once per day
		if (!dateToScore.containsKey(today)) {		
			List<Date> dates = new ArrayList<>(dateToScore.keySet());
			Collections.sort(dates);
			List<Double> previousScores = new ArrayList<>();
			for (Date date : dates) {
				previousScores.add(dateToScore.get(date));
			}
			previousScores = Lists.reverse(previousScores);
			globalScore = modifiedWeightedAveraging(previousScores);
		}
		
		return globalScore;
	}		
	
	protected Double getTodaysLocalScore() {
		return dateToScore.get(getTodaysDateAtTwelveAM());
	}
	
	private static ImmutableMap<Integer, Integer> getCategoryToScore() {		
		try {
			File file = new File("categoryToScore.txt");
			if (!file.exists()) {
				System.err.println("categoryToScore file doesn't exist :O");	
				return ImmutableMap.<Integer,Integer>builder().build(); // empty map
			}
			
			Scanner sc = new Scanner(file);			
			sc.nextLine(); // remove header line
			
			ImmutableMap.Builder<Integer,Integer> builder = ImmutableMap.<Integer,Integer>builder();
			
			while (sc.hasNextLine()) {
				String line = sc.nextLine().trim();
				String[] arr = line.split("//");
				
				if (arr.length != 3) {
					System.err.println("categoryToScore -- each line must contain 3 entries: " + line);	
					continue; 
				}
				
				for (int i = 0; i < arr.length; i++) {
					arr[i] = arr[i].trim();
				}
				if (!Util.isNumeric(arr[1]) || !Util.isNumeric(arr[2])) {
					System.err.println("categoryToScore contains a non-numeric id or score at line: " + line);	
					continue; 
				}
				
				builder.put(Integer.parseInt(arr[1]), Integer.parseInt(arr[2]));				
			}
			
			sc.close();
			return builder.build();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error in UserAdherenceParams' map construction!");	
			return ImmutableMap.<Integer,Integer>builder().build(); // empty map
		}
	}
}
