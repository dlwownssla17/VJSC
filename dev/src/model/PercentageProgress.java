package model;

public class PercentageProgress implements Progress {
	
	private double progress;
	
	public PercentageProgress() {
		this.progress = 0.;
	}

	@Override
	public double getProgress() {
		return this.progress;
	}

	@Override
	public double setProgress(double progress) {
		this.progress = progress;
		return getProgress();
	}

}
