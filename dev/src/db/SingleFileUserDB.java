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
	
	private String fitBitAccessToken;
	private String fitBitRefreshToken;
	private String fitBitUserId;
	private String fitBitScope;
	private String fitBitTokenType;
	private String fitBitExpiresIn;
	
	// TODO this is temporary (remove this later)
	private String fitBitDisplayName;
	
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
	
	public String getFitBitAccessToken() {
		return this.fitBitAccessToken == null ? "{NULL}" : this.fitBitAccessToken;
	}
	
	public String setFitBitAccessToken(String fitBitAccessToken) {
		this.fitBitAccessToken = fitBitAccessToken;
		return this.fitBitAccessToken;
	}
	
	public String getFitBitRefreshToken() {
		return this.fitBitRefreshToken == null ? "{NULL}" : this.fitBitRefreshToken;
	}
	
	public String setFitBitRefreshToken(String fitBitRefreshToken) {
		this.fitBitRefreshToken = fitBitRefreshToken;
		return this.fitBitRefreshToken;
	}
	
	public String getFitBitUserId() {
		return this.fitBitUserId == null ? "{NULL}" : this.fitBitUserId;
	}
	
	public String setFitBitUserId(String fitBitUserId) {
		this.fitBitUserId = fitBitUserId;
		return this.fitBitUserId;
	}
	
	public String getFitBitScope() {
		return this.fitBitScope == null ? "{NULL}" : this.fitBitScope;
	}
	
	public String setFitBitScope(String fitBitScope) {
		this.fitBitScope = fitBitScope;
		return this.fitBitScope;
	}
	
	public String getFitBitTokenType() {
		return this.fitBitTokenType == null ? "{NULL}" : this.fitBitTokenType;
	}
	
	public String setFitBitTokenType(String fitBitTokenType) {
		this.fitBitTokenType = fitBitTokenType;
		return this.fitBitTokenType;
	}
	
	public String getFitBitExpiresIn() {
		return this.fitBitExpiresIn == null ? "{NULL}" : this.fitBitExpiresIn;
	}
	
	public String setFitBitExpiresIn(String fitBitExpiresIn) {
		this.fitBitExpiresIn = fitBitExpiresIn;
		return this.fitBitExpiresIn;
	}
	
	// TODO this is temporary (remove this later)
	public String getFitBitDisplayName() {
		return this.fitBitDisplayName == null ? "{NULL}" : this.fitBitDisplayName;
	}
	
	// TODO this is temporary (remove this later)
	public String setFitBitDisplayName(String fitBitDisplayName) {
		this.fitBitDisplayName = fitBitDisplayName;
		return this.fitBitDisplayName;
	}
	
	public static void main(String[] args) {
		long epoch = Long.parseLong("1081157732");
		SingleFileUserDB userDB = new SingleFileUserDB("spiro", "metaxas95", new Date(epoch * 1000), ModelTools.DEFAULT_CAPACITY);
		userDB.setFitBitDisplayName("SpiroTheMantaxas");
		System.out.println(SingleFileDBTools.toJSON(userDB).toString(4));
	}
}
