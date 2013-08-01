package com.chronicweirdo.ur.clock;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

public abstract class GameBody implements GameComponent {
	
	protected BodyDef definition;
	protected World world;
	protected Body body;

	protected GameBody(BodyDef definition) {
		this.world = Globals.WORLD;
		this.definition = definition;
		this.world = world;
	}

	public abstract void render();
}
