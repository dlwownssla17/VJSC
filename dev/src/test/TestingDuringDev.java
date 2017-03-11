package test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import db.SingleFileDBTools;
import db.SingleFileTeamDB;
import db.SingleFileUserDB;
import fitbit.FitBitAccount;
import fitbit.FitBitActivity;
import fitbit.FitBitIntraDayActivity;
import fitbit.FitBitIntraDayTimeDetail;
import fitbit.FitBitPeriod;
import fitbit.FitBitTools;
import model.Team;
import model.User;
import util.IO;

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
	
	private static void testDBTools() {
		try {
			User vivek = new User("vivekmaster", "vivek_password");
			User jj = new User("jjmaster", "jj_password");
			User spiro = new User("spiromaster", "spiro_password");
			User chad = new User("chadmaster", "chad_password");
			
			FitBitAccount spiroFitBitAccount = new FitBitAccount("userAuthentication.txt");
			
			spiro.linkFitBitAccount(spiroFitBitAccount);
			
			Team teamVJ = new Team("The Voice");
			teamVJ.addUser(vivek);
			teamVJ.addUser(jj);
			Team teamSC = new Team("The Kardashians");
			teamSC.addUser(spiro);
			teamSC.addUser(chad);
			
			SingleFileUserDB vivekDB = SingleFileDBTools.toUserDB(vivek);
			SingleFileUserDB jjDB = SingleFileDBTools.toUserDB(jj);
			SingleFileUserDB spiroDB = SingleFileDBTools.toUserDB(spiro);
			SingleFileUserDB chadDB = SingleFileDBTools.toUserDB(chad);
			
			SingleFileTeamDB teamVJDB = SingleFileDBTools.toTeamDB(teamVJ);
			SingleFileTeamDB teamSCDB = SingleFileDBTools.toTeamDB(teamSC);
			
			SingleFileDBTools.setUserDB(vivekDB);
			SingleFileDBTools.setUserDB(jjDB);
			SingleFileDBTools.setUserDB(spiroDB);
			SingleFileDBTools.setTeamDB(teamVJDB);
			SingleFileDBTools.setUserDB(chadDB);
			SingleFileDBTools.setTeamDB(teamSCDB);
			
			System.out.println("---Everyone joined!---");
			System.out.println(IO.readFile("db"));
			System.out.println();
			
			spiroDB = SingleFileDBTools.getUserDB("spiromaster");
			System.out.println("---");
			System.out.println(String.format("Spiro's FitBit display name is = %s", spiroDB.getFitBitDisplayName()));
			System.out.println();
			
			jj.setPassword("jj_with_his_bridgewater_transparency17");
			jjDB = SingleFileDBTools.toUserDB(jj);
			SingleFileDBTools.setUserDB(jjDB);
			
			SingleFileDBTools.removeTeamDB(teamSCDB);
			SingleFileDBTools.removeUserDB(chadDB);
			
			System.out.println("---Now what?!---");
			System.out.println(IO.readFile("db"));
			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// testFitBitTools();
		
		// testDBTools();
		
		System.out.println("What's up!");
	}
}
