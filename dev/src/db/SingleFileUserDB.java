package db;

import java.util.Date;

import org.json.JSONObject;

import model.ModelTools;
import model.User;
import util.DateFormat;

public class SingleFileUserDB {
	private String username;
	private String password;
	
	private String memberSince;
	
	// TODO a whole lot of other stuff goes in here
	
	private int userScheduleCapacity;
	
	private String fitbitAccessToken;
	private String fitbitRefreshToken;
	private String fitbitScope;
	
	public SingleFileUserDB(String username, String password, String memberSince, int userScheduleCapacity) {
		this.username = username;
		this.password = password;
		
		this.memberSince = memberSince;
		
		this.userScheduleCapacity = userScheduleCapacity;
	}
	
	public SingleFileUserDB(String username, String password, Date memberSince, int userScheduleCapacity) {
		this(username, password, DateFormat.getFormattedString(memberSince, SingleFileDBTools.MemberSinceDateTimeFormat),
				userScheduleCapacity);
	}
	
	public SingleFileUserDB(User user) {
		this(user.getUsername(), user.getPassword(),
				DateFormat.getFormattedString(user.getMemberSince(), SingleFileDBTools.MemberSinceDateTimeFormat),
				user.getSchedule().getCapacity());
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String setPassword(String password) {
		this.password = password;
		return this.password;
	}
	
	public String getMemberSince() {
		return this.memberSince;
	}
	
	public String getFitbitAccessToken() {
		return this.fitbitAccessToken == null ? "{NULL}" : this.fitbitAccessToken;
	}
	
	public String setFitbitAccessToken(String fitbitAccessToken) {
		this.fitbitAccessToken = fitbitAccessToken;
		return this.fitbitAccessToken;
	}
	
	public String getFitbitRefreshToken() {
		return this.fitbitRefreshToken == null ? "{NULL}" : this.fitbitRefreshToken;
	}
	
	public String setFitbitRefreshToken(String fitbitRefreshToken) {
		this.fitbitRefreshToken = fitbitRefreshToken;
		return this.fitbitRefreshToken;
	}
	
	public String getFitbitScope() {
		return this.fitbitScope == null ? "{NULL}" : this.fitbitScope;
	}
	
	public String setFitbitScope(String fitbitScope) {
		this.fitbitScope = fitbitScope;
		return this.fitbitScope;
	}
	
	public static void main(String[] args) {
		long epoch = Long.parseLong("1081157732");
		SingleFileUserDB userDB = new SingleFileUserDB("spiro", "metaxas95", new Date(epoch * 1000), ModelTools.DEFAULT_CAPACITY);
		System.out.println(SingleFileDBTools.toJSON(userDB).toString(4));
	}
}
