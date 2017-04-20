package model;

import java.util.ArrayList;
import java.util.Date;

import fitbit.FitbitAccount;
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
	
	private long teamId;
	private ArrayList<TeamInvitation> teamInvitations;
	
	private UserSchedule schedule;
	
	private FitbitAccount fitbitAccount;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		
		this.memberSince = DateAndCalendar.newDateGMT();
		
		this.teamId = -1;
		this.teamInvitations = new ArrayList<>();
		
		this.schedule = new UserSchedule(ModelTools.DEFAULT_CAPACITY, this.memberSince, this.username);
	}
	
	@Override
	public boolean equals(Object otherUser) {
		return otherUser instanceof User && this.username.equals(((User) otherUser).getUsername());
	}
	
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
	
	public long getTeamId() {
		return this.teamId;
	}
	
	public boolean inTeam() {
		return this.teamId >= 0;
	}
	
	public long setTeamId(long teamId) {
		this.teamId = teamId;
		return this.teamId;
	}
	
	public ArrayList<TeamInvitation> getTeamInvitations() {
		return this.teamInvitations;
	}
	
	public ArrayList<TeamInvitation> setTeamInvitations(ArrayList<TeamInvitation> teamInvitations) {
		this.teamInvitations = teamInvitations;
		return this.teamInvitations;
	}
	
	public boolean addTeamInvitation(TeamInvitation teamInvitation) {
		return this.teamInvitations.add(teamInvitation);
	}
	
	public boolean removeTeamInvitation(long teamId) {
		TeamInvitation teamInvitationToRemove = null;
		for (TeamInvitation teamInvitation : this.teamInvitations) {
			if (teamInvitation.getTeamId() == teamId) {
				teamInvitationToRemove = teamInvitation;
				break;
			}
		}
		return teamInvitationToRemove != null ? this.teamInvitations.remove(teamInvitationToRemove) : false;
	}
	
	public void clearTeamInvitations() {
		 this.teamInvitations.clear();
	}
	
	public boolean isLeader(Team team) {
		return this.teamId == team.getTeamId() && this.username.equals(team.getLeaderUsername());
	}
	
	public UserSchedule setSchedule(UserSchedule schedule) {
		this.schedule = schedule;
		return this.schedule;
	}
	
	public UserSchedule getSchedule() {
		return this.schedule;
	}
	
	public boolean hasFitbitAccount() {
		return this.fitbitAccount != null;
	}
	
	public FitbitAccount getFitbitAccount() {
		return this.fitbitAccount;
	}
	
	public FitbitAccount setFitbitAccount(FitbitAccount fitbitAccount) {
		this.fitbitAccount = fitbitAccount;
		return this.fitbitAccount;
	}
	
	public int getTotalRunningScore() {
		Date today = this.getSchedule().getLastDayChecked();
		Date yesterday = DateAndCalendar.addDate(today, -1);
		return this.getSchedule().getRunningScoreForDate(yesterday);
	}
	
	public int getTodayScoreSoFar() {
		Date today = this.getSchedule().getLastDayChecked();
		return this.getSchedule().computeDailyScore(today);
	}
}
