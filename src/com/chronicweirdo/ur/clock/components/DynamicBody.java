package com.chronicweirdo.ur.clock.components;

import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

public abstract class DynamicBody extends GameBody {

	protected static BodyDef definition(float x, float y) {
		BodyDef definition = new BodyDef();
		definition.position.set(x, y);
		definition.type = BodyType.DYNAMIC;
		return definition;
	}
	
	protected DynamicBody(float x, float y) {
		super(definition(x, y));
	}
}
