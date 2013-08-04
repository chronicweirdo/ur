package com.chronicweirdo.ur.clock;

public class Hour {

	public static enum HourSet {
		ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, ELEVEN, 
		TWELVE
	}
	
	private HourSet hour;
	private boolean onPin = false;
	
	public Hour(HourSet hour, boolean onPin) {
		super();
		this.hour = hour;
		this.onPin = onPin;
	}

	public HourSet getHour() {
		return hour;
	}

	public boolean isOnPin() {
		return onPin;
	}


	
	
}
