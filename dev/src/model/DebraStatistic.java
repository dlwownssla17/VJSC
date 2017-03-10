package model;

public class DebraStatistic {
	private double avg;
	private double avgSq;
	private int numSamples;
	
	public DebraStatistic(double avg, double avgSq, int numSamples) {
		this.avg = avg;
		this.avgSq = avgSq;
		this.numSamples = numSamples;
	}
	
	public DebraStatistic() {
		avg = 0.;
		avgSq = 0.;
		numSamples = 0;
	}
	
	public double getAvg() {
		return avg;
	}
	
	public double getStdDev() {		
		return Math.sqrt(avgSq - avg*avg);
	}
	
	public boolean isEmpty() {
		return numSamples == 0;
	}
	
	public void addEntry(double value) {
		avg = ((avg * numSamples) + value) / (numSamples+1);
		avgSq = ((avgSq * numSamples) + value*value) / (numSamples+1);
		numSamples++;
	}
}
