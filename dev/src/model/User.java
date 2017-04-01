package model;

import java.util.Calendar;
import java.util.Date;

import fitbit.FitBitAccount;
import util.DateAndCalendar;

public class User {
	private String username;
	private String password;
	
	private Date memberSince;
	
//	private UserInfo info;
//	private UserSettings settings;
//	private UserDiabetesParams diabetesParams;
//	private UserAdherenceParams adherenceParams;
//	private UserCommunityParams communityParams;
	private Team team;
	private UserSchedule schedule;
	
	private FitBitAccount fitBitAccount;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		
		this.memberSince = DateAndCalendar.newDateGMT();
		
		// TODO decide on initialization of all the other fields
		
		this.schedule = new UserSchedule(ModelTools.DEFAULT_CAPACITY, this.memberSince, this.username);
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
	
//	public UserInfo getInfo() {
//		return this.info;
//	}
//	
//	public UserInfo setInfo(UserInfo info) {
//		this.info = info;
//		return this.info;
//	}
	
//	public UserSettings getSettings() {
//		return this.settings;
//	}
//	
//	public UserSettings setSettings(UserSettings settings) {
//		this.settings = settings;
//		return this.settings;
//	}
	
//	public UserDiabetesParams getDiabetesParams() {
//		return this.diabetesParams;
//	}
//	
//	public UserDiabetesParams setDiabetesParams(UserDiabetesParams diabetesParams) {
//		this.diabetesParams = diabetesParams;
//		return this.diabetesParams;
//	}
	
//	public UserAdherenceParams getAdherenceParams() {
//		return this.adherenceParams;
//	}
//	
//	public UserAdherenceParams setAdherenceParams(UserAdherenceParams adherenceParams) {
//		this.adherenceParams = adherenceParams;
//		return this.adherenceParams;
//	}
	
	public Team getTeam() {
		return this.team;
	}
	
	public boolean inTeam() {
		return this.team != null;
	}
	
	public Team joinTeam(Team team) {
		if (this.inTeam()) throw new IllegalStateException();
		
		this.team = team;
		return this.team;
	}
	
	public Team joinTeam(Team team, boolean asLeader) {
		if (asLeader) team.setLeader(this);
		
		return this.joinTeam(team);
	}
	
	public boolean isLeader() {
		return this.inTeam() && this.username.equals(this.team.getLeader().getUsername());
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
	
	public int getTotalRunningScore() {
		Date today = this.getSchedule().getLastDayChecked();
		Date yesterday = DateAndCalendar.addDate(today, -1);
		return this.getSchedule().getRunningScoreForDate(yesterday);
	}
}
