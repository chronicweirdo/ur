package com.chronicweirdo.ur.clock;

public class CreateBoxMessage implements GameMessage {

	public float x, y, w, h;

	public CreateBoxMessage(float x, float y, float w, float h) {
		super();
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	
}
