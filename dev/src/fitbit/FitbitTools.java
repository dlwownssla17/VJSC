package fitbit;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.json.JSONException;

import util.Connection;
import util.DateFormat;

public class FitbitTools {
	protected static String OAuth2URL = "https://api.fitbit.com/oauth2/token";
	protected static String DebraAuthorizationHeader = "MjI4NzVSOjBmZDM0YTdjZTFiMGY5ZjMwOTc4OTA0Mzc1MGI0MmQ0";
	// TODO store DebraAuthorizationHeader somewhere else
	
	private static String FitbitAPIUserURL = "https://api.fitbit.com/1/user/-/";
	private static String JSONExtension = ".json";
	private static String DateTag = "date";
	private static String TimeTag = "time";
	private static String ActTag = "activities";
	private static String FitbitAPIDateFormat = "yyyy-MM-dd";
	private static String FitbitAPITimeFormat = "HH:mm";
	
	/*
     * Check user access
     */
	private static boolean checkAccess(FitbitAccount fitbitAccount, FitbitAccess access) {
		try {
			if (!fitbitAccount.hasAccess(access)) throw new FitbitException(
					String.format("checkAccess: Fitbit user does not have access = %s", access));
		} catch (FitbitException e) {
			e.printStackTrace();
        	System.out.println(e.getMessage());
		}
		return true;
	}
	
	/*
     * Send an HTTP request for the provided Fitbit url.  Return the JSON response
     * for the request or null if an error occurred. If the access token
     * expired, refresh it and update the user authentication file
     */
	private static String fitbitAPISendHTTPRequest(FitbitAccount fitbitAccount, String targetURL) {
		URL url;
		HttpURLConnection connection = null;
        try {
        	url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            
            String authorizationHeader = "Bearer " + fitbitAccount.getAccessToken();
            connection.setRequestProperty("Authorization", authorizationHeader);
            
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            int rc = connection.getResponseCode();
            if (rc == 401) fitbitAccount.refreshAccessToken();
            
            if (rc == 200) return Connection.getResponseString(connection);
            else if (rc == 429) throw new FitbitException(
            			String.format("fitbitAPISendHTTPRequest: Reached Fitbit API Limit. (response code = %d)", rc));
            else throw new FitbitException(
            			String.format("fitbitAPISendHTTPRequest: Unexpected connection response code = %d.", rc));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
        	e.printStackTrace();
        } catch (FitbitException e) {
        	e.printStackTrace();
        	System.out.println(e.getMessage());
        }
        return null;
	}
	
	/*
     * Check user access then get data for the user
     */
	private static String getData(FitbitAccount fitbitAccount, FitbitAccess access, String targetURL) {
		checkAccess(fitbitAccount, access);
		return fitbitAPISendHTTPRequest(fitbitAccount, targetURL);
	}
	
	/*
     * Get the profile data for the user
     */
	public static String getProfile(FitbitAccount fitbitAccount) {
		String targetURL = String.format("%s%s%s", FitbitAPIUserURL, FitbitAccess.PROFILE, JSONExtension);
		return getData(fitbitAccount, FitbitAccess.PROFILE, targetURL);
	}
	
	/*
     * Get sleep data for a particular date
     */
	public static String getSleepForDate(FitbitAccount fitbitAccount, Date date) {
        String formattedDate = DateFormat.getFormattedString(date, FitbitAPIDateFormat);
        String targetURL = String.format("%s%s/%s/%s%s", FitbitAPIUserURL, FitbitAccess.SLEEP,
        		DateTag, formattedDate, JSONExtension);
        return getData(fitbitAccount, FitbitAccess.SLEEP, targetURL);
	}
	
	/*
     * Get Day data over the span of an end date, and a period leading up to
     * the end date. Activities can be picked from FitbitActivities
     */
	public static String getActivityForPeriod(FitbitAccount fitbitAccount, FitbitActivity act,
			Date endDate, FitbitPeriod dt) {
		String formattedEndDate = DateFormat.getFormattedString(endDate, FitbitAPIDateFormat);
		String targetURL = String.format("%s%s/%s/%s/%s/%s%s", FitbitAPIUserURL, ActTag, act,
				DateTag, formattedEndDate, dt, JSONExtension);
		return getData(fitbitAccount, FitbitAccess.ACTIVITY, targetURL);
	}
	
	/*
     * Get Day data over the span of a start date and an end date. Activities can
     * be picked from FitbitActivities.
     */
	public static String getActivityForDateRange(FitbitAccount fitbitAccount, FitbitActivity act,
			Date startDate, Date endDate) {
		String formattedStartDate = DateFormat.getFormattedString(startDate, FitbitAPIDateFormat);
		String formattedEndDate = DateFormat.getFormattedString(endDate, FitbitAPIDateFormat);
		String targetURL = String.format("%s%s/%s/%s/%s/%s%s", FitbitAPIUserURL, ActTag, act,
				DateTag, formattedStartDate, formattedEndDate, JSONExtension);
		return getData(fitbitAccount, FitbitAccess.ACTIVITY, targetURL);
	}
	
	/*
     * Get IntraDay data for a specific date and a time detail picked from
     * FirBitTimeDetail (minutes or fifteenminutes). Heart Data however
     * uses minutes of seconds. Activities are picked
     * from FitbitIntraDayActivities.
     */
	public static String getActivityForIntraDay(FitbitAccount fitbitAccount, FitbitIntraDayActivity idact,
			Date startDate, FitbitIntraDayTimeDetail timeDetail) {
		String formattedStartDate = DateFormat.getFormattedString(startDate, FitbitAPIDateFormat);
		String targetURL = String.format("%s%s/%s/%s/%s/%s/%s%s", FitbitAPIUserURL, ActTag, idact,
				DateTag, formattedStartDate, FitbitPeriod.DAY, timeDetail, JSONExtension);
		return getData(fitbitAccount, FitbitAccess.ACTIVITY, targetURL);
	}
	
	/*
     * Get IntraDay data for a particular activity over the range of a start
     * date and end date for a particular time detail (minutes or fifteen minutes).
     * Heart data however uses minutes or seconds.
     * Activities are picked from FitbitIntraDayActivities.
     */
	public static String getActivityForIntraDayRange(FitbitAccount fitbitAccount, FitbitIntraDayActivity idact,
			Date startDate, Date endDate, FitbitIntraDayTimeDetail timeDetail) {
		String formattedStartDate = DateFormat.getFormattedString(startDate, FitbitAPIDateFormat);
		String formattedEndDate = DateFormat.getFormattedString(endDate, FitbitAPIDateFormat);
		String targetURL = String.format("%s%s/%s/%s/%s/%s/%s%s", FitbitAPIUserURL, ActTag, idact,
				DateTag, formattedStartDate, formattedEndDate, timeDetail, JSONExtension);
		return getData(fitbitAccount, FitbitAccess.ACTIVITY, targetURL);
	}
	
	/*
     * Get IntraDay data for a start date to an end date that starts with
     * the time provided in startDate and ends at the time of endDate. A time detail
     * is picked from FitbitTimeDetail (minutes or fifteen minutes). Heart data
     * however uses minutes or seconds.
     * An Activity is picked from FitbitIntraDayActivities.
     */
	public static String getActivityForIntraDayTimeRange(FitbitAccount fitbitAccount, FitbitIntraDayActivity idact,
			Date startDate, Date endDate, FitbitIntraDayTimeDetail timeDetail) {
		String formattedStartDate = DateFormat.getFormattedString(startDate, FitbitAPIDateFormat);
		String formattedEndDate = DateFormat.getFormattedString(endDate, FitbitAPIDateFormat);
		String formattedStartTime = DateFormat.getFormattedString(startDate, FitbitAPITimeFormat);
		String formattedEndTime = DateFormat.getFormattedString(endDate, FitbitAPITimeFormat);
		String targetURL = String.format("%s%s/%s/%s/%s/%s/%s/%s/%s/%s%s", FitbitAPIUserURL, ActTag, idact,
				DateTag, formattedStartDate, formattedEndDate, timeDetail,
				TimeTag, formattedStartTime, formattedEndTime, JSONExtension);
		return getData(fitbitAccount, FitbitAccess.ACTIVITY, targetURL);
	}
}
