package com.chronicweirdo.ur.clock;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.springframework.stereotype.Component;

@Component
public class Globals {

	private World world;
	private int D = 10;
	private float G = -9.8f;
	private int circleSegments = 100;
	
	private Globals() {
		world = new World(new Vec2(0, G), false);
	}

	public World world() {
		return this.world;
	}
	
	public int d() {
		return this.D;
	}
	
	public float g() {
		return this.G;
	}
	
	public int circleSegments() {
		return this.circleSegments;
	}
}
