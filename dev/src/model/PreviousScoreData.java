package model;

public class PreviousScoreData {
	int category;
	
	int numSamples;
	
	double avgValue;
	double avgValueSq;
	
	public PreviousScoreData(int category, int numSamples, double avgValue, double avgValueSq) {
		this.category = category;
		this.numSamples = numSamples;
		this.avgValue = avgValue;
		this.avgValueSq = avgValueSq;
	}
	
	public void addEntry(double value) {
		avgValue = (avgValue * numSamples + value) / (numSamples+1);
		avgValueSq = (avgValue * numSamples + (value*value)) / (numSamples+1);
		numSamples++;
	}
	
	public double getAvg() { return avgValue; }
	public double getStdDev() { return avgValueSq - avgValue*avgValue; }
}
