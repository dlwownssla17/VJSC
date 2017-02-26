package model;

import java.util.Date;

import fitbit.FitBitAccount;

public class User {
	private String username;
	private String password;
	
	private Date memberSince;
	
	private UserInfo info;
	private UserSettings settings;
	private UserDiabetesParams diabetesParams;
	private UserAdherenceParams adherenceParams;
	private UserCommunityParams communityParams;
	private UserSchedule schedule;
	// private BehaviorTPM tpm;
	
	private FitBitAccount fitBitAccount;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		// TODO username and password validation
		
		this.memberSince = new Date();
		
		// TODO decide on initialization of all the other fields
		
		this.schedule = new UserSchedule(365);
	}
	
	// TODO decide on how to initialize from initial survey upon registration
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String setPassword(String password) {
		this.password = password;
		// TODO same or different password validation
		return this.password;
	}
	
	public Date getMemberSince() {
		return this.memberSince;
	}
	
	public Date setMemberSince(Date memberSince) {
		this.memberSince = memberSince;
		return this.memberSince;
	}
	
	public UserSchedule getSchedule() {
		return this.schedule;
	}
	
	public boolean hasFitBitAccount() {
		return this.fitBitAccount != null;
	}
	
	public FitBitAccount getFitBitAccount() {
		return this.fitBitAccount;
	}
	
	public boolean linkFitBitAccount(FitBitAccount fitBitAccount) {
		this.fitBitAccount = fitBitAccount;
		return true;
	}
	
	public static void main(String[] args) {
		
	}
}
