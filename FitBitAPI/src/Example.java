import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.ByteArrayInputStream;


public class Example {
	
	public static void main(String[] args) {
        //User person = new User("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1NDVROU0iLCJhdWQiOiIyMjg3NVIiLCJpc3MiOiJGaXRiaXQiLCJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJzY29wZXMiOiJyc29jIHJhY3QgcnNldCBybG9jIHJ3ZWkgcmhyIHJwcm8gcm51dCByc2xlIiwiZXhwIjoxNDgzNTE0OTQzLCJpYXQiOjE0ODM0ODYxNDN9.4yrn6x4zZ-Fn8R-KokrqGBk7HTodf5c-qNptHRJoPvs", "f1ed5e4938a9be86c695bee63cf51c444d6f0460089154eb8ed22f99d4ec3222", "545Q9M");
        User person = new User("userAuthentication.txt");
        Date today = new Date();
        /*String profile = FitBitTools.getProfile(person);
        System.out.println(profile);
        
        String steps1 = FitBitTools.getActivityForPeriod(person, FitBitActivities.Steps, today, FitBitPeriods.month);
        System.out.println(steps1);
        
        String steps2 = FitBitTools.getActivityForDateRange(person, FitBitActivities.Steps, today, today);
        System.out.println(steps2);
        
        String distance1 = FitBitTools.getActivityForPeriod(person, FitBitActivities.Distance, today, FitBitPeriods.month);
        System.out.println(distance1);
        
        String distance2 = FitBitTools.getActivityForDateRange(person, FitBitActivities.Distance, today, today);
        System.out.println(distance2);
        
        String minutesSedentary1 = FitBitTools.getActivityForPeriod(person, FitBitActivities.MinutesSedentary, today, FitBitPeriods.month);
        System.out.println(minutesSedentary1);
        
        String minutesSedentary2 = FitBitTools.getActivityForDateRange(person, FitBitActivities.MinutesSedentary, today, today);
        System.out.println(minutesSedentary2);
        
        String minutesLightlyActive1 = FitBitTools.getActivityForPeriod(person, FitBitActivities.MinutesLightlyActive, today, FitBitPeriods.month);
        System.out.println(minutesLightlyActive1);
        
        String minutesLightlyActive2 = FitBitTools.getActivityForDateRange(person, FitBitActivities.MinutesLightlyActive, today, today);
        System.out.println(minutesLightlyActive2);
        
        String minutesFairlyActive1 = FitBitTools.getActivityForPeriod(person, FitBitActivities.MinutesFairlyActive, today, FitBitPeriods.month);
        System.out.println(minutesFairlyActive1);
        
        String minutesFairlyActive2 = FitBitTools.getActivityForDateRange(person, FitBitActivities.MinutesFairlyActive, today, today);
        System.out.println(minutesFairlyActive2);
        
        String minutesVeryActive1 = FitBitTools.getActivityForPeriod(person, FitBitActivities.MinutesVeryActive, today, FitBitPeriods.month);
        System.out.println(minutesVeryActive1);
        
        String minutesVeryActive2 = FitBitTools.getActivityForDateRange(person, FitBitActivities.MinutesVeryActive, today, today);
        System.out.println(minutesVeryActive2);
        
        String floors1 = FitBitTools.getActivityForPeriod(person, FitBitActivities.Floors, today, FitBitPeriods.month);
        System.out.println(floors1);
        
        String floors2 = FitBitTools.getActivityForDateRange(person, FitBitActivities.Floors, today, today);
        System.out.println(floors2);
        
        String elevation1 = FitBitTools.getActivityForPeriod(person, FitBitActivities.Elevation, today, FitBitPeriods.month);
        System.out.println(elevation1);
        
        String elevation2 = FitBitTools.getActivityForDateRange(person, FitBitActivities.Elevation, today, today);
        System.out.println(elevation2);
        
        String calories1 = FitBitTools.getActivityForPeriod(person, FitBitActivities.Calories, today, FitBitPeriods.month);
        System.out.println(calories1);
        
        String calories2 = FitBitTools.getActivityForDateRange(person, FitBitActivities.Calories, today, today);
        System.out.println(calories2);
        
        String activityCalories1 = FitBitTools.getActivityForPeriod(person, FitBitActivities.ActivityCalories, today, FitBitPeriods.month);
        System.out.println(activityCalories1);
        
        String activityCalories2 = FitBitTools.getActivityForDateRange(person, FitBitActivities.ActivityCalories, today, today);
        System.out.println(activityCalories2);
        
        String caloriesBMR1 = FitBitTools.getActivityForPeriod(person, FitBitActivities.CaloriesBMR, today, FitBitPeriods.month);
        System.out.println(caloriesBMR1);
        
        String caloriesBMR2 = FitBitTools.getActivityForDateRange(person, FitBitActivities.CaloriesBMR, today, today);
        System.out.println(caloriesBMR2);
        
        String hearyRate1 = FitBitTools.getActivityForPeriod(person, FitBitActivities.HeartRate, today, FitBitPeriods.month);
        System.out.println(hearyRate1);
        
        String heartRate2 = FitBitTools.getActivityForDateRange(person, FitBitActivities.HeartRate, today, today);
        System.out.println(heartRate2);*/
        
        String steps3 = FitBitTools.getActivityForIntraDayTimeRange(person, FitBitIntraDayActivities.Steps, today, today, FitBitTimeDetail.Minutes);
        System.out.println(steps3);
        
        String sleep1 = FitBitTools.getSleepForDate(person, today);
        System.out.println(sleep1);
	}
}
