package test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import fitbit.FitBitAccount;
import fitbit.FitBitActivity;
import fitbit.FitBitIntraDayActivity;
import fitbit.FitBitIntraDayTimeDetail;
import fitbit.FitBitPeriod;
import fitbit.FitBitTools;

public class TestingDuringDev {
	
	private static void writeLine(BufferedWriter bw, String text) throws IOException {
		bw.write(text);
		bw.newLine();
	}
	
	private static String prettyPrintJSON(String jsonString) throws JSONException {
		JSONObject jsonObj = new JSONObject(jsonString);
		return jsonObj.toString(4);
	}
	
	private static void testFitBitTools() {
		try {
			FitBitAccount fitBitAccount = new FitBitAccount("userAuthentication.txt");
			Date today = new Date();
			
			BufferedWriter bw = new BufferedWriter(new FileWriter("output"));
			
			System.out.println("Writing to 'output' file...");
			
			writeLine(bw, "---getProfile---");
			writeLine(bw, prettyPrintJSON(FitBitTools.getProfile(fitBitAccount)));
			bw.newLine();
			
			writeLine(bw, "---getSleepForDate (today)---");
			writeLine(bw, prettyPrintJSON(FitBitTools.getSleepForDate(fitBitAccount, today)));
			bw.newLine();
			
			for (FitBitActivity act : FitBitActivity.values()) {
				writeLine(bw, String.format("---getActivityForPeriod (%s; today; month)---", act));
				writeLine(bw, prettyPrintJSON(FitBitTools.getActivityForPeriod(fitBitAccount, act,
						today, FitBitPeriod.MONTH)));
				bw.newLine();
				
				writeLine(bw, String.format("---getActivityForDateRange (%s; today; today)---", act));
				writeLine(bw, prettyPrintJSON(FitBitTools.getActivityForDateRange(fitBitAccount, act,
						today, today)));
				bw.newLine();
			}
			
			for (FitBitIntraDayActivity idact : FitBitIntraDayActivity.values()) {
				writeLine(bw, String.format("---getActivityForIntraDay (%s; today; 1min)---", idact));
				writeLine(bw, prettyPrintJSON(FitBitTools.getActivityForIntraDay(fitBitAccount, idact,
						today, FitBitIntraDayTimeDetail.MINUTES)));
				bw.newLine();
				
				writeLine(bw, String.format("---getActivityForIntraDayRange (%s; today; today; 1min)---", idact));
				writeLine(bw, prettyPrintJSON(FitBitTools.getActivityForIntraDayRange(fitBitAccount, idact,
						today, today, FitBitIntraDayTimeDetail.MINUTES)));
				bw.newLine();
				
				writeLine(bw, String.format("---getActivityForIntraDayTimeRange (%s; today; today; 1min)---", idact));
				writeLine(bw, prettyPrintJSON(FitBitTools.getActivityForIntraDayTimeRange(fitBitAccount, idact,
						today, today, FitBitIntraDayTimeDetail.MINUTES)));
				bw.newLine();
			}
			
			bw.close();
			
			System.out.println("Writing complete.");
		} catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
        	e.printStackTrace();
        }
	}
	
	public static void main(String[] args) {
		testFitBitTools();
	}
}
