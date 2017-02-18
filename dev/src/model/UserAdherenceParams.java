package model;

public class UserAdherenceParams {
	private User user;
	private int score;
	
	public static final int COMPLETED_TASK_SCORE = 100;
	public static final int INCOMPLETE_TASK_SCORE = -100;
	public static final int DELETED_TASK_SCORE = -1000;

	
	public UserAdherenceParams(User user) {
		this.user = user;
		this.score = 0;
	}
	
	public User getUser() { return this.user; }
	
	protected int getScore() {
		return this.score;
	}
	
	protected void setScore(int score) {
		this.score = score;
	}
	
	public void changeScore(int delta) {
		score += delta;
	}
	
	public void completedTask() {
		changeScore(COMPLETED_TASK_SCORE);
	}
	
	public void deletedTask() {
		changeScore(DELETED_TASK_SCORE);
	}
	
	public void didntCompleteTask() {
		changeScore(INCOMPLETE_TASK_SCORE);
	}
}
