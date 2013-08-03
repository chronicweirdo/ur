package com.chronicweirdo.ur.clock.components;

import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

public abstract class RigidBody extends GameBody {

	protected static BodyDef definition(float x, float y) {
		BodyDef definition = new BodyDef();
		definition.position.set(x, y);
		definition.type = BodyType.STATIC;
		return definition;
	}
	
	protected RigidBody(float x, float y) {
		super(definition(x, y));
	}

}
