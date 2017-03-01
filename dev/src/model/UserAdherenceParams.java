package model;

public class UserAdherenceParams {
	private int userScore;
	
	protected int getUserScore() {
		return this.userScore;
	}
	
	protected int setUserScore(int userScore) {
		this.userScore = userScore;
		return this.userScore;
	}
}
