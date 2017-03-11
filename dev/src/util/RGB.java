package util;

import model.ProgressColor;

public class RGB {
	// TODO: account for white and grey colors
	public static int[] progressColorToRGB(ProgressColor c) {
		switch (c) {
			case RED:
				return new int[]{ 255, 0, 0 };
			case YELLOW:
				return new int[]{ 255, 255, 0 };
			case GREEN:
				return new int[]{ 0, 255, 0 };
			case WHITE:
				return null;
			case GREY:
				return null;
			default:
				throw new IllegalArgumentException("invalid color.");
		}
	}
}
