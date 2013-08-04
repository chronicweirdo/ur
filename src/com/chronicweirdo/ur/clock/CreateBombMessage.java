package com.chronicweirdo.ur.clock;

public class CreateBombMessage implements GameMessage {

	public float x, y, r;

	public CreateBombMessage(float x, float y, float r) {
		super();
		this.x = x;
		this.y = y;
		this.r = r;
	}
	
	
}
