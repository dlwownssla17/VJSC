package model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserAdherencesParamsTest {
	UserAdherenceParams uap;
	Date twoDaysAgo;
	Date yesterday;

	@Before
	public void setUp() throws Exception {
		twoDaysAgo = UserAdherenceParams.getTodaysDateAtTwelveAM();
		twoDaysAgo.setDate(twoDaysAgo.getDate()-2);
		yesterday = UserAdherenceParams.getTodaysDateAtTwelveAM();
		yesterday.setDate(yesterday.getDate()-1);		
	
		// changes System's current time
		Map<Date, Double> dateToScore = new HashMap<>();	
		dateToScore.put(twoDaysAgo, 100.);
		dateToScore.put(yesterday, 150.);
		
		Map<Integer, DebraStatistic> categoryToStats = new HashMap<>();
		categoryToStats.put(1, new DebraStatistic(.6, .56, 10));
		
		uap = new UserAdherenceParams(dateToScore, categoryToStats);
	}	

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetTodaysDateAtTwelveAM() {
		Date twelveAmDate = UserAdherenceParams.getTodaysDateAtTwelveAM();
		Date today = new Date(System.currentTimeMillis());
		assertEquals(twelveAmDate.getYear(), today.getYear());
		assertEquals(twelveAmDate.getMonth(), today.getMonth());
		assertEquals(twelveAmDate.getDay(), today.getDay());
		assertTrue(twelveAmDate.getHours() + twelveAmDate.getMinutes() + twelveAmDate.getSeconds() == 0);
	}
	
	@Test
	public void testModifiedWeightedAveragingEmptyList() {
		double avg = UserAdherenceParams.modifiedWeightedAveraging(new ArrayList<>());
		assertTrue(avg == 0.0);
	}
	
	@Test
	public void testModifiedWeightedAveragingNonEmptyList() {
		List<Double> arg = new ArrayList<>();
		arg.add(100.);
		arg.add(200.);
		arg.add(300.);
		double avg = UserAdherenceParams.modifiedWeightedAveraging(arg);
		double expectedAvg = 163. + 63./99.;
		assertTrue(Math.abs(avg-expectedAvg) <= 0.00000001);
	}
	
	@Test
	public void testGlobalScore() {
		double expectedScore = (100./2 + 150.)/1.5;
		assertTrue(Math.abs(uap.getScore() - expectedScore) <= .0000001);		
	}
	
	@Test
	public void testChangeScore() {
		Date tomorrow = UserAdherenceParams.getTodaysDateAtTwelveAM();
		tomorrow.setDate(tomorrow.getDate()+1);
		Date twoDaysFromNow = UserAdherenceParams.getTodaysDateAtTwelveAM();
		twoDaysFromNow.setDate(tomorrow.getDate()+2);

		boolean[] daysOfWeekActive = {true, true, true, true, true, true, true};
		uap.changeScore(new ScheduleItem(0 /* filler ID */, "filler title", "filler desc", new User(null, null), tomorrow, twoDaysFromNow, daysOfWeekActive, 1 /* category */, new DebraStatistic(.6, .43, 10), null, null), .6);
		double expectedScore = UserAdherenceParams.CATEGORY_TO_SCORE.get(1) * .6;
		assertTrue(Math.abs(uap.getTodaysLocalScore() - expectedScore) <= .0000001);		
	}

}
