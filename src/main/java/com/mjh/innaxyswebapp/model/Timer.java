package com.mjh.innaxyswebapp.model;

// TODO Add JavaDoc comments.
public class Timer {
	/*---- Field ----*/
	private long startTime;
	private long stopTime;
	
	/*---- Constructors ----*/
	public Timer() {
		this.startTime = 0;
		this.stopTime = 0;
	}
	
	/*---- Methods ----*/
	public void addStartTime(long startTime) {
		this.startTime += startTime;
	}
	
	public void addStopTime(long stopTime) {
		this.stopTime += stopTime;	
	}
	
	public double getTotalTime() {
		return (this.stopTime - this.startTime) / 1000000.0;
	}
	
	/*---- Getters and Setters ----*/
	public long getStartTime() {
		return this.startTime;
	}
	
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	
	public long getStopTime() {
		return this.stopTime;
	}
	
	public void setStopTime(long stopTime) {
		this.stopTime = stopTime;
	}
}
