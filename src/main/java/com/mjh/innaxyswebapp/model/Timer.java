package com.mjh.innaxyswebapp.model;

import java.util.Objects;

/**
 * Class which allows for timing. This is primarily used for timing the time taken
 * to find the shortest path in a graph, but can be used elsewhere. Various getters 
 * and setters are available so as to provide specific information pertaining to the edge.
 * 
 * @author	MaxMJH - MaxHarrisMJH@gmail.com
 * @version 1.0
 * @since 	02-10-2023
 */
public class Timer {
	/*---- Fields ----*/
	/**
	 * Variable to store the start time.
	 */
	private long startTime;
	
	/**
	 * Variable to store the stop time.
	 */
	private long stopTime;
	
	/*---- Constructor ----*/
	/**
	 * Constructor which initialises the start and stop times to 0.
	 */
	public Timer() {
		this.startTime = 0;
		this.stopTime = 0;
	}
	
	/*---- Methods ----*/
	/**
	 * This method allows for adding start time to the timer. Use this
	 * method at the start of a method, and ensure that it is ended via
	 * adding stop time. This class assumes that inputs are that of nano time.
	 * 
	 * @param startTime The start time to add to the timer - these can be stacked.
	 */
	public void addStartTime(long startTime) {
		this.startTime += startTime;
	}
	
	/**
	 * This method allows for adding stop time to the timer. Use this
	 * method at the end of a method, and ensure that it is started via
	 * adding a start time. This class assumes that inputs are that of nano time.
	 * 
	 * @param stopTime The stop time to add to the timer - these can be stacked.
	 */
	public void addStopTime(long stopTime) {
		this.stopTime += stopTime;	
	}
	
	/**
	 * This method calculates the total time elapsed between the start and stop time.
	 * Once this is calculated, the time is converted to milliseconds.
	 * 
	 * @return The time elapsed between the two start and stop times in milliseconds.
	 */
	public double getTotalTime() {
		return (this.stopTime - this.startTime) / 1000000.0;
	}
	
	/*---- Getters and Setters ----*/
	/**
	 * This method gets the start time.
	 * 
	 * @return The start time.
	 */
	public long getStartTime() {
		return this.startTime;
	}
	
	/**
	 * This method sets the start time.
	 * 
	 * @param startTime A long which signifies the start time.
	 */
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	
	/**
	 * This method gets the stop time.
	 * 
	 * @return The stop time.
	 */
	public long getStopTime() {
		return this.stopTime;
	}
	
	/**
	 * This method sets the stop time.
	 * 
	 * @param stopTime A long which signifies the stop time.
	 */
	public void setStopTime(long stopTime) {
		this.stopTime = stopTime;
	}
	
	/*---- Overridden Methods ----*/
	/**
	 * Returns the hash code of the start and stop time.
	 * 
	 * @return The hash code of the instance.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.startTime, this.stopTime);
	}

	/**
	 * Method used to determine whether or not another instance of Timer is 
	 * equal to another. This is primarily tested via the shortest path.
	 * 
	 * @param  obj The instance which to compare.
	 * @return true if the two instances are the same, false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (!(obj instanceof Timer)) {
			return false;
		}
		
		Timer other = (Timer) obj;
		
		return Objects.equals(this.startTime, other.startTime) && Objects.equals(this.stopTime, other.stopTime);
	}
	
	/**
	 * Returns the string representation of the Timer class, containing the start
	 * and stop time.
	 * 
	 * @return String representation of the Timer class.
	 */
	@Override 
	public String toString() {
		return String.format("{start time: %d, stop time: %d}", this.startTime, this.stopTime);
	}
}
