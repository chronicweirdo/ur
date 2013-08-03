package com.chronicweirdo.ur.clock;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class GameBody implements GameComponent {
	
	@Autowired
	protected Globals globals;
	protected BodyDef definition;
	protected World world;
	protected Body body;

	protected GameBody(BodyDef definition) {
		//this.world = globals.world();
		this.definition = definition;
		this.world = world;
	}
	
	public Globals getGlobals() {
		return this.globals;
	}

	public abstract void render();
}
