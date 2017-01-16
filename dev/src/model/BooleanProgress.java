package model;

public class BooleanProgress implements Progress {
	
	private boolean completed;
	
	public BooleanProgress() {
		this.completed = false;
	}

	@Override
	public double getProgress() {
		return this.completed ? 1. : 0.;
	}

	@Override
	public double setProgress(double progress) {
		this.completed = progress == 1.;
		return getProgress();
	}

}
