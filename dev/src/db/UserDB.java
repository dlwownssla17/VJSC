package db;

import java.util.Date;

import org.json.JSONObject;

import util.DateFormat;

public class UserDB {
	private static String MemberSinceDateTimeFormat = "yyyy-MM-dd HH:mm";
	
	private String username;
	private String password;
	
	private String memberSince;
	
	// TODO a whole lot of other stuff goes in here
	
	// TODO this is temporary
	private String fitBitDisplayName;
	
	public UserDB(String username, String password, Date memberSince) {
		this.username = username;
		this.password = password;
		
		this.memberSince = DateFormat.getFormattedString(memberSince, MemberSinceDateTimeFormat);
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
	
	// TODO this is temporary
	public String getFitBitDisplayName() {
		return this.fitBitDisplayName == null ? "{NULL}" : this.fitBitDisplayName;
	}
	
	// TODO this is temporary
	public String setFitBitDisplayName(String fitBitDisplayName) {
		this.fitBitDisplayName = fitBitDisplayName;
		return getFitBitDisplayName();
	}
	
	// TODO this is temporary (fitBitDisplayName)
	public String toJSONString() {
		JSONObject userJSON = new JSONObject();
		userJSON.put("username", this.username);
		userJSON.put("password",  this.password);
		userJSON.put("member_since", this.memberSince);
		userJSON.put("fitbit_display_name", this.fitBitDisplayName);
		return userJSON.toString(4);
	}
	
	public static void main(String[] args) {
		long epoch = Long.parseLong("1081157732");
		UserDB userDB = new UserDB("spiro", "metaxas95", new Date(epoch * 1000));
		userDB.setFitBitDisplayName("SpiroTheMantaxas");
		System.out.println(userDB.toJSONString());
	}
}
