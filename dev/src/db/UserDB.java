package db;

import java.util.Date;

import org.json.JSONObject;

import util.DateFormat;

public class UserDB {
	private String username;
	private String password;
	
	private String memberSince;
	
	// TODO a whole lot of other stuff goes in here
	
	private String fitBitAccessToken;
	private String fitBitRefreshToken;
	private String fitBitUserId;
	private String fitBitScope;
	private String fitBitTokenType;
	private String fitBitExpiresIn;
	
	// TODO this is temporary
	private String fitBitDisplayName;
	
	public UserDB(String username, String password, String memberSince) {
		this.username = username;
		this.password = password;
		
		this.memberSince = memberSince;
	}
	
	public UserDB(String username, String password, Date memberSince) {
		this(username, password, DateFormat.getFormattedString(memberSince, DBTools.MemberSinceDateTimeFormat));
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String setPassword(String password) {
		this.password = password;
		return getPassword();
	}
	
	public String getMemberSince() {
		return this.memberSince;
	}
	
	public String getFitBitAccessToken() {
		return this.fitBitAccessToken == null ? "{NULL}" : this.fitBitAccessToken;
	}
	
	public String setFitBitAccessToken(String fitBitAccessToken) {
		this.fitBitAccessToken = fitBitAccessToken;
		return getFitBitAccessToken();
	}
	
	public String getFitBitRefreshToken() {
		return this.fitBitRefreshToken == null ? "{NULL}" : this.fitBitRefreshToken;
	}
	
	public String setFitBitRefreshToken(String fitBitRefreshToken) {
		this.fitBitRefreshToken = fitBitRefreshToken;
		return getFitBitRefreshToken();
	}
	
	public String getFitBitUserId() {
		return this.fitBitUserId == null ? "{NULL}" : this.fitBitUserId;
	}
	
	public String setFitBitUserId(String fitBitUserId) {
		this.fitBitUserId = fitBitUserId;
		return getFitBitUserId();
	}
	
	public String getFitBitScope() {
		return this.fitBitScope == null ? "{NULL}" : this.fitBitScope;
	}
	
	public String setFitBitScope(String fitBitScope) {
		this.fitBitScope = fitBitScope;
		return getFitBitScope();
	}
	
	public String getFitBitTokenType() {
		return this.fitBitTokenType == null ? "{NULL}" : this.fitBitTokenType;
	}
	
	public String setFitBitTokenType(String fitBitTokenType) {
		this.fitBitTokenType = fitBitTokenType;
		return getFitBitTokenType();
	}
	
	public String getFitBitExpiresIn() {
		return this.fitBitExpiresIn == null ? "{NULL}" : this.fitBitExpiresIn;
	}
	
	public String setFitBitExpiresIn(String fitBitExpiresIn) {
		this.fitBitExpiresIn = fitBitExpiresIn;
		return getFitBitExpiresIn();
	}
	
	// TODO this is temporary
	public String getFitBitDisplayName() {
		return this.fitBitDisplayName == null ? "{NULL}" : this.fitBitDisplayName;
	}
	
	// TODO this is temporary
	public String setFitBitDisplayName(String fitBitDisplayName) {
		this.fitBitDisplayName = fitBitDisplayName;
		return getFitBitDisplayName();
	}
	
	public static void main(String[] args) {
		long epoch = Long.parseLong("1081157732");
		UserDB userDB = new UserDB("spiro", "metaxas95", new Date(epoch * 1000));
		userDB.setFitBitDisplayName("SpiroTheMantaxas");
		System.out.println(DBTools.toJSON(userDB).toString(4));
	}
}
