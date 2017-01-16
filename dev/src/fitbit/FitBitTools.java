package fitbit;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.json.JSONException;

import util.Connection;
import util.DateFormat;

public class FitBitTools {
	protected static String OAuth2URL = "https://api.fitbit.com/oauth2/token";
	protected static String DebraAuthorizationHeader = "MjI4NzVSOjBmZDM0YTdjZTFiMGY5ZjMwOTc4OTA0Mzc1MGI0MmQ0";
	// TODO store DebraAuthorizationHeader somewhere else
	
	private static String FitBitAPIUserURL = "https://api.fitbit.com/1/user/-/";
	private static String JSONExtension = ".json";
	private static String DateTag = "date";
	private static String TimeTag = "time";
	private static String ActTag = "activities";
	private static String FitBitAPIDateFormat = "yyyy-MM-dd";
	private static String FitBitAPITimeFormat = "HH:mm";
	
	/*
     * Check user access
     */
	private static boolean checkAccess(FitBitAccount fitBitAccount, FitBitAccess access) {
		try {
			if (!fitBitAccount.hasAccess(access)) throw new FitBitException(
					String.format("checkAccess: FitBit user does not have access = %s", access));
		} catch (FitBitException e) {
			e.printStackTrace();
        	System.out.println(e.getMessage());
		}
		return true;
	}
	
	/*
     * Send an HTTP request for the provided FitBit url.  Return the JSON response
     * for the request or null if an error occurred. If the access token
     * expired, refresh it and update the user authentication file
     */
	private static String fitBitAPISendHTTPRequest(FitBitAccount fitBitAccount, String targetURL) {
		URL url;
		HttpURLConnection connection = null;
        try {
        	url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            
            String authorizationHeader = "Bearer " + fitBitAccount.getAccessToken();
            connection.setRequestProperty("Authorization", authorizationHeader);
            
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            int rc = connection.getResponseCode();
            if (rc == 401) fitBitAccount.refreshAccessToken();
            
            if (rc == 200) return Connection.getResponseString(connection);
            else if (rc == 429) throw new FitBitException(
            			String.format("fitBitAPISendHTTPRequest: Reached FitBit API Limit. (response code = %d)", rc));
            else throw new FitBitException(
            			String.format("fitBitAPISendHTTPRequest: Unexpected connection response code = %d.", rc));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
        	e.printStackTrace();
        } catch (FitBitException e) {
        	e.printStackTrace();
        	System.out.println(e.getMessage());
        }
        return null;
	}
	
	/*
     * Check user access then get data for the user
     */
	private static String getData(FitBitAccount fitBitAccount, FitBitAccess access, String targetURL) {
		checkAccess(fitBitAccount, access);
		return fitBitAPISendHTTPRequest(fitBitAccount, targetURL);
	}
	
	/*
     * Get the profile data for the user
     */
	public static String getProfile(FitBitAccount fitBitAccount) {
		String targetURL = String.format("%s%s%s", FitBitAPIUserURL, FitBitAccess.PROFILE, JSONExtension);
		return getData(fitBitAccount, FitBitAccess.PROFILE, targetURL);
	}
	
	/*
     * Get sleep data for a particular date
     */
	public static String getSleepForDate(FitBitAccount fitBitAccount, Date date) {
        String formattedDate = DateFormat.getFormattedString(date, FitBitAPIDateFormat);
        String targetURL = String.format("%s%s/%s/%s%s", FitBitAPIUserURL, FitBitAccess.SLEEP,
        		DateTag, formattedDate, JSONExtension);
        return getData(fitBitAccount, FitBitAccess.SLEEP, targetURL);
	}
	
	/*
     * Get Day data over the span of an end date, and a period leading up to
     * the end date. Activities can be picked from FitBitActivities
     */
	public static String getActivityForPeriod(FitBitAccount fitBitAccount, FitBitActivity act,
			Date endDate, FitBitPeriod dt) {
		String formattedEndDate = DateFormat.getFormattedString(endDate, FitBitAPIDateFormat);
		String targetURL = String.format("%s%s/%s/%s/%s/%s%s", FitBitAPIUserURL, ActTag, act,
				DateTag, formattedEndDate, dt, JSONExtension);
		return getData(fitBitAccount, FitBitAccess.ACTIVITY, targetURL);
	}
	
	/*
     * Get Day data over the span of a start date and an end date. Activities can
     * be picked from FitBitActivities.
     */
	public static String getActivityForDateRange(FitBitAccount fitBitAccount, FitBitActivity act,
			Date startDate, Date endDate) {
		String formattedStartDate = DateFormat.getFormattedString(startDate, FitBitAPIDateFormat);
		String formattedEndDate = DateFormat.getFormattedString(endDate, FitBitAPIDateFormat);
		String targetURL = String.format("%s%s/%s/%s/%s/%s%s", FitBitAPIUserURL, ActTag, act,
				DateTag, formattedStartDate, formattedEndDate, JSONExtension);
		return getData(fitBitAccount, FitBitAccess.ACTIVITY, targetURL);
	}
	
	/*
     * Get IntraDay data for a specific date and a time detail picked from
     * FirBitTimeDetail (minutes or fifteenminutes). Heart Data however
     * uses minutes of seconds. Activities are picked
     * from FitBitIntraDayActivities.
     */
	public static String getActivityForIntraDay(FitBitAccount fitBitAccount, FitBitIntraDayActivity idact,
			Date startDate, FitBitIntraDayTimeDetail timeDetail) {
		String formattedStartDate = DateFormat.getFormattedString(startDate, FitBitAPIDateFormat);
		String targetURL = String.format("%s%s/%s/%s/%s/%s/%s%s", FitBitAPIUserURL, ActTag, idact,
				DateTag, formattedStartDate, FitBitPeriod.DAY, timeDetail, JSONExtension);
		return getData(fitBitAccount, FitBitAccess.ACTIVITY, targetURL);
	}
	
	/*
     * Get IntraDay data for a particular activity over the range of a start
     * date and end date for a particular time detail (minutes or fifteen minutes).
     * Heart data however uses minutes or seconds.
     * Activities are picked from FitBitIntraDayActivities.
     */
	public static String getActivityForIntraDayRange(FitBitAccount fitBitAccount, FitBitIntraDayActivity idact,
			Date startDate, Date endDate, FitBitIntraDayTimeDetail timeDetail) {
		String formattedStartDate = DateFormat.getFormattedString(startDate, FitBitAPIDateFormat);
		String formattedEndDate = DateFormat.getFormattedString(endDate, FitBitAPIDateFormat);
		String targetURL = String.format("%s%s/%s/%s/%s/%s/%s%s", FitBitAPIUserURL, ActTag, idact,
				DateTag, formattedStartDate, formattedEndDate, timeDetail, JSONExtension);
		return getData(fitBitAccount, FitBitAccess.ACTIVITY, targetURL);
	}
	
	/*
     * Get IntraDay data for a start date to an end date that starts with
     * the time provided in startDate and ends at the time of endDate. A time detail
     * is picked from FitBitTimeDetail (minutes or fifteen minutes). Heart data
     * however uses minutes or seconds.
     * An Activity is picked from FitBitIntraDayActivities.
     */
	public static String getActivityForIntraDayTimeRange(FitBitAccount fitBitAccount, FitBitIntraDayActivity idact,
			Date startDate, Date endDate, FitBitIntraDayTimeDetail timeDetail) {
		String formattedStartDate = DateFormat.getFormattedString(startDate, FitBitAPIDateFormat);
		String formattedEndDate = DateFormat.getFormattedString(endDate, FitBitAPIDateFormat);
		String formattedStartTime = DateFormat.getFormattedString(startDate, FitBitAPITimeFormat);
		String formattedEndTime = DateFormat.getFormattedString(endDate, FitBitAPITimeFormat);
		String targetURL = String.format("%s%s/%s/%s/%s/%s/%s/%s/%s/%s%s", FitBitAPIUserURL, ActTag, idact,
				DateTag, formattedStartDate, formattedEndDate, timeDetail,
				TimeTag, formattedStartTime, formattedEndTime, JSONExtension);
		return getData(fitBitAccount, FitBitAccess.ACTIVITY, targetURL);
	}
}
