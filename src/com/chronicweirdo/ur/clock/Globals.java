package com.chronicweirdo.ur.clock;

import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chronicweirdo.ur.clock.components.ComponentFactory;

@Component
public class Globals {

	private World world;
	private int D = 3;
	private float G = -9.8f;
	private int circleSegments = 100;
	
	private Globals() {
		world = new World(new Vec2(0, G), false);
	}
	
	public void setContactListener(ContactListener listener) {
		world.setContactListener(listener);
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
