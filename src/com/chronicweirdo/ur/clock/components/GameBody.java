package com.chronicweirdo.ur.clock.components;

import javax.annotation.PostConstruct;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.springframework.beans.factory.annotation.Autowired;

import com.chronicweirdo.ur.clock.Globals;

public abstract class GameBody implements Component {
	
	@Autowired
	protected Globals globals;
	
	protected BodyDef definition;
	protected Body body;

	protected GameBody(BodyDef definition) {
		this.definition = definition;
	}
	
	@PostConstruct
	protected abstract void init();
	
	public abstract void render();
	
	public Body getBody() {
		return this.body;
	}
}
