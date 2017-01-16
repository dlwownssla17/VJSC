package model;

public class LevelsProgress implements Progress {
	
	private int currentLevel;
	private int levels;
	
	public LevelsProgress(int levels) throws IllegalArgumentException {
		if (levels < 2 || levels > 24)
			throw new IllegalArgumentException("The number of levels must be between 2 and 24");
		this.currentLevel = 0;
		this.levels = levels;
	}

	@Override
	public double getProgress() {
		return ((double) this.currentLevel) / this.levels;
	}

	@Override
	public double setProgress(double progress) {
		this.currentLevel = (int) progress * this.levels;
		return getProgress();
	}

}
