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
		
		this.schedule = new UserSchedule(ModelTools.DEFAULT_CAPACITY);
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
	
	public UserInfo getInfo() {
		return this.info;
	}
	
	public UserInfo setInfo(UserInfo info) {
		this.info = info;
		return this.info;
	}
	
	public UserSettings getSettings() {
		return this.settings;
	}
	
	public UserSettings setSettings(UserSettings settings) {
		this.settings = settings;
		return this.settings;
	}
	
	public UserDiabetesParams getDiabetesParams() {
		return this.diabetesParams;
	}
	
	public UserDiabetesParams setDiabetesParams(UserDiabetesParams diabetesParams) {
		this.diabetesParams = diabetesParams;
		return this.diabetesParams;
	}
	
	public UserAdherenceParams getAdherenceParams() {
		return this.adherenceParams;
	}
	
	public UserAdherenceParams setAdherenceParams(UserAdherenceParams adherenceParams) {
		this.adherenceParams = adherenceParams;
		return this.adherenceParams;
	}
	
	public UserCommunityParams getCommunityParams() {
		return this.communityParams;
	}
	
	public UserCommunityParams setCommunityParams(UserCommunityParams communityParams) {
		this.communityParams = communityParams;
		return this.communityParams;
	}
	
	public UserSchedule setSchedule(UserSchedule schedule) {
		this.schedule = schedule;
		return this.schedule;
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
	
	public FitBitAccount setFitBitAccount(FitBitAccount fitBitAccount) {
		this.fitBitAccount = fitBitAccount;
		return this.fitBitAccount;
	}
}
