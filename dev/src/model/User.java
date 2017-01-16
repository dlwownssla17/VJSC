package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import fitbit.FitBitAccount;

public class User {
	private String username;
	private String password;
	private UserInfo info;
	private UserSettings settings;
	private UserDiabetesParams diabetesParams;
	private UserAdherenceParams adherenceParams;
	private UserCommunityParams communityParams;
	private HashMap<Date, ArrayList<ScheduleItem>> schedule;
	private BehaviorTPM tpm;
	
	private FitBitAccount fitBitAccount;
	
	public static void main(String[] args) {
		
	}
}
