package com.chronicweirdo.ur.clock;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

public class GameBody extends Body {

	private GameBody(BodyDef bd, World world) {
		super(bd, world);
	}

	public void render() {
		// draw the body
	}
}
