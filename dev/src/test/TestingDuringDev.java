package test;

//import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import db.SingleFileDBTools;
import db.SingleFileTeamDB;
import db.SingleFileUserDB;
import fitbit.FitbitAccount;
import fitbit.FitbitActivity;
import fitbit.FitbitIntraDayActivity;
import fitbit.FitbitIntraDayTimeDetail;
import fitbit.FitbitPeriod;
import fitbit.FitbitTools;
import model.Team;
import model.User;
import util.DateAndCalendar;
import util.IO;

public class TestingDuringDev {
	
//	private static void writeLine(BufferedWriter bw, String text) throws IOException {
//		bw.write(text);
//		bw.newLine();
//	}
//	
//	private static String prettyPrintJSON(String jsonString) throws JSONException {
//		JSONObject jsonObj = new JSONObject(jsonString);
//		return jsonObj.toString(4);
//	}
	
//	private static void testFitbitTools() {
//		try {
//			FitbitAccount fitbitAccount = new FitbitAccount("userAuthentication.txt");
//			Date today = DateAndCalendar.newDateGMT();
//			
//			BufferedWriter bw = new BufferedWriter(new FileWriter("output"));
//			
//			System.out.println("Writing to 'output' file...");
//			
//			writeLine(bw, "---getProfile---");
//			writeLine(bw, prettyPrintJSON(FitbitTools.getProfile(fitbitAccount)));
//			bw.newLine();
//			
//			writeLine(bw, "---getSleepForDate (today)---");
//			writeLine(bw, prettyPrintJSON(FitbitTools.getSleepForDate(fitbitAccount, today)));
//			bw.newLine();
//			
//			for (FitbitActivity act : FitbitActivity.values()) {
//				writeLine(bw, String.format("---getActivityForPeriod (%s; today; month)---", act));
//				writeLine(bw, prettyPrintJSON(FitbitTools.getActivityForPeriod(fitbitAccount, act,
//						today, FitbitPeriod.MONTH)));
//				bw.newLine();
//				
//				writeLine(bw, String.format("---getActivityForDateRange (%s; today; today)---", act));
//				writeLine(bw, prettyPrintJSON(FitbitTools.getActivityForDateRange(fitbitAccount, act,
//						today, today)));
//				bw.newLine();
//			}
//			
//			for (FitbitIntraDayActivity idact : FitbitIntraDayActivity.values()) {
//				writeLine(bw, String.format("---getActivityForIntraDay (%s; today; 1min)---", idact));
//				writeLine(bw, prettyPrintJSON(FitbitTools.getActivityForIntraDay(fitbitAccount, idact,
//						today, FitbitIntraDayTimeDetail.MINUTES)));
//				bw.newLine();
//				
//				writeLine(bw, String.format("---getActivityForIntraDayRange (%s; today; today; 1min)---", idact));
//				writeLine(bw, prettyPrintJSON(FitbitTools.getActivityForIntraDayRange(fitbitAccount, idact,
//						today, today, FitbitIntraDayTimeDetail.MINUTES)));
//				bw.newLine();
//				
//				writeLine(bw, String.format("---getActivityForIntraDayTimeRange (%s; today; today; 1min)---", idact));
//				writeLine(bw, prettyPrintJSON(FitbitTools.getActivityForIntraDayTimeRange(fitbitAccount, idact,
//						today, today, FitbitIntraDayTimeDetail.MINUTES)));
//				bw.newLine();
//			}
//			
//			bw.close();
//			
//			System.out.println("Writing complete.");
//		} catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//        	e.printStackTrace();
//        }
//	}
	
//	private static void testDBTools() {
//		try {
//			User vivek = new User("vivekmaster", "vivek_password");
//			User jj = new User("jjmaster", "jj_password");
//			User spiro = new User("spiromaster", "spiro_password");
//			User chad = new User("chadmaster", "chad_password");
//			
//			FitbitAccount spiroFitbitAccount = new FitbitAccount("userAuthentication.txt");
//			
//			spiro.setFitbitAccount(spiroFitbitAccount);
//			
////			Team teamVJ = new Team("The Voice");
////			teamVJ.addUser(vivek);
////			teamVJ.addUser(jj);
////			Team teamSC = new Team("The Kardashians");
////			teamSC.addUser(spiro);
////			teamSC.addUser(chad);
//			
//			SingleFileUserDB vivekDB = SingleFileDBTools.toUserDB(vivek);
//			SingleFileUserDB jjDB = SingleFileDBTools.toUserDB(jj);
//			SingleFileUserDB spiroDB = SingleFileDBTools.toUserDB(spiro);
//			SingleFileUserDB chadDB = SingleFileDBTools.toUserDB(chad);
//			
////			SingleFileTeamDB teamVJDB = SingleFileDBTools.toTeamDB(teamVJ);
////			SingleFileTeamDB teamSCDB = SingleFileDBTools.toTeamDB(teamSC);
//			
//			SingleFileDBTools.setUserDB(vivekDB);
//			SingleFileDBTools.setUserDB(jjDB);
//			SingleFileDBTools.setUserDB(spiroDB);
////			SingleFileDBTools.setTeamDB(teamVJDB);
//			SingleFileDBTools.setUserDB(chadDB);
////			SingleFileDBTools.setTeamDB(teamSCDB);
//			
//			System.out.println("---Everyone joined!---");
//			System.out.println(IO.readFile("db"));
//			System.out.println();
//			
//			spiroDB = SingleFileDBTools.getUserDB("spiromaster");
//			System.out.println("---");
//			System.out.println(String.format("Spiro's Fitbit display name is = %s", spiroDB.getFitbitDisplayName()));
//			System.out.println();
//			
//			jj.setPassword("jj_with_his_bridgewater_transparency17");
//			jjDB = SingleFileDBTools.toUserDB(jj);
//			SingleFileDBTools.setUserDB(jjDB);
//			
////			SingleFileDBTools.removeTeamDB(teamSCDB);
//			SingleFileDBTools.removeUserDB(chadDB);
//			
//			System.out.println("---Now what?!---");
//			System.out.println(IO.readFile("db"));
//			System.out.println();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	public static void main(String[] args) {
		// testFitbitTools();
		
		// testDBTools();
		
		System.out.println("What's up!");
	}
}
