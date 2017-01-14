import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FitBitTools {

    /*
     * Get the profile data for the user
     */
    public static String getProfile(User user) {
        if (!user.hasAccess("profile")) {
            System.out.println("Error: temp.User does not have access to FitBit content.");
            return null;
        }
        String targetURL = "https://api.fitbit.com/1/user/-/profile.json";

        HttpURLConnection connection = null;
        URL url;
        try {
            url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            String authorizationHeader = "Bearer " + user.accessToken;
            connection.setRequestProperty("Authorization", authorizationHeader);
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            int responseCode = connection.getResponseCode();
            StringBuffer response = new StringBuffer();
            if (responseCode == 200) {
                String inputLine;
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                while((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();
            } else if (responseCode == 401) {
                if (!user.refreshAccessToken()) {
                    System.out.println("Error: Could not refresh FitBit Access Token.");
                    return null;
                }
                url = new URL(targetURL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                authorizationHeader = "Bearer " + user.accessToken;
                connection.setRequestProperty("Authorization", authorizationHeader);
                connection.setUseCaches(false);
                connection.setDoInput(true);
                connection.setDoOutput(true);

                responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    String inputLine;
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    while((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    return response.toString();
                } else if (responseCode == 429) {
                    System.out.println("Error: Reached FitBit API Limit.");
                }
            } else if (responseCode == 429) {
                System.out.println("Error: Reached FitBit API Limit.");
            } else {
                System.out.println(responseCode);
            }
            return null;
        } catch (MalformedURLException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    /*
     * Send an HTTP request for the provided FitBit url.  Return the JSON response
     * for the request or null if an error occurred.  If the access token
     * expired, refresh it and update the user authentication file.
     */
    private static String sendHTTPRequest(User user, String targetURL) {
        HttpURLConnection connection = null;
        URL url;
        try {
            url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            String authorizationHeader = "Bearer " + user.accessToken;
            connection.setRequestProperty("Authorization", authorizationHeader);
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            int responseCode = connection.getResponseCode();
            StringBuffer response = new StringBuffer();
            if (responseCode == 200) {
                String inputLine;
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                while((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();
            } else if (responseCode == 401) {
                if (!user.refreshAccessToken()) {
                    System.out.println("Error: Could not refresh FitBit Access Token.");
                    return null;
                }
                url = new URL(targetURL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                authorizationHeader = "Bearer " + user.accessToken;
                connection.setRequestProperty("Authorization", authorizationHeader);
                connection.setUseCaches(false);
                connection.setDoInput(true);
                connection.setDoOutput(true);

                responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    String inputLine;
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    while((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    return response.toString();
                } else if (responseCode == 429) {
                    System.out.println("Error: Reached FitBit API Limit.");
                }
            } else if (responseCode == 429) {
                System.out.println("Error: Reached FitBit API Limit.");
            } else {
                System.out.println(responseCode);
            }
            return null;
        } catch (MalformedURLException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    /*
     * Get Day data over the span of an end date, and a period leading up to
     * the end date.  Activities can be picked from FitBitActivities
     */
    public static String getActivityForPeriod(User user, String activity, Date endDate, String period) {
        if (!user.hasAccess(activity)) {
            System.out.println("Error: temp.User does not have access to FitBit content.");
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedEndDate = formatter.format(endDate);
        String targetURL = "https://api.fitbit.com/1/user/-/" + activity + "/date/" + formattedEndDate + "/" + period + ".json";
        return sendHTTPRequest(user, targetURL);
    }

    /*
     * Get Day data over the span of a start date and an end date.  Activities can
     * be picked from FitBitActivities.
     */
    public static String getActivityForDateRange(User user, String activity, Date startDate, Date endDate) {
        if (!user.hasAccess(activity)) {
            System.out.println("Error: temp.User does not have access to FitBit content.");
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedStartDate = formatter.format(startDate);
        String formattedEndDate = formatter.format(endDate);
        String targetURL = "https://api.fitbit.com/1/user/-/" + activity + "/date/" + formattedStartDate + "/" + formattedEndDate + ".json";
        return sendHTTPRequest(user, targetURL);
    }

    /*
     * Get sleep data for a particular date.
     */
    public static String getSleepForDate(User user, Date date) {
        if (!user.hasAccess("sleep")) {
            System.out.println("Error: temp.User does not have access to FitBit content.");
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedEndDate = formatter.format(date);
        String targetURL = "https://api.fitbit.com/1/user/-/sleep/date/" + formattedEndDate + ".json";
        return sendHTTPRequest(user, targetURL);
    }

    /*
     * Get IntraDay data for a particular activity over the range of a start
     * date and end date for a particular time detail (minutes or fifteenminutes).
     * Heart data however uses minutes or seconds.
     * Activities are picked from FitBitIntraDayActivities.
     */
    public static String getActivityForIntraDayRange(User user, String activity, Date startDate, Date endDate, String timeDetail) {
        if (!user.hasAccess(activity)) {
            System.out.println("Error: temp.User does not have access to FitBit content.");
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedStartDate = formatter.format(startDate);
        String formattedEndDate = formatter.format(endDate);
        String targetURL = "https://api.fitbit.com/1/user/-/" + activity + "/date/" + formattedStartDate + "/" + formattedEndDate + "/" + timeDetail + ".json";
        return sendHTTPRequest(user, targetURL);
    }

    /*
     * Get IntraDay data for a specific date and a time detail picked from
     * FirBitTimeDetail (minutes or fifteenminutes).  Heart Data however
     * uses minutes of seconds.  Activities are picked
     * from FitBitIntraDayActivities.
     */
    public static String getActivityForIntraDay(User user, String activity, Date startDate, String timeDetail) {
        if (!user.hasAccess(activity)) {
            System.out.println("Error: temp.User does not have access to FitBit content.");
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedStartDate = formatter.format(startDate);
        String targetURL = "https://api.fitbit.com/1/user/-/" + activity + "/date/" + formattedStartDate + "/1d/" + timeDetail + ".json";
        return sendHTTPRequest(user, targetURL);
    }

    /*
     * Get IntraDay data for a start date to an end date that starts with
     * the time provided in startDate and ends at the time of endDate.  A time detail
     * is picked from FitBitTimeDetail (minutes or fifteenminutes).  Heart data
     * however uses minutes or seconds.
     * An Activity is picked from FitBitIntraDayActivities.
     */
    public static String getActivityForIntraDayTimeRange(User user, String activity, Date startDate, Date endDate, String timeDetail) {
        if (!user.hasAccess(activity)) {
            System.out.println("Error: temp.User does not have access to FitBit content.");
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm");
        String formattedStartDate = formatter.format(startDate);
        String formattedEndDate = formatter.format(endDate);
        String formattedStartTime = formatterTime.format(startDate);
        String formattedEndTime = formatterTime.format(endDate);
        String targetURL = "https://api.fitbit.com/1/user/-/" + activity + "/date/" + formattedStartDate + "/" + formattedEndDate + "/" + timeDetail + "/time/" + formattedStartTime + "/" + formattedEndTime + ".json";
        return sendHTTPRequest(user, targetURL);
    }

}
