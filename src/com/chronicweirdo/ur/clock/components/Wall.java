package com.chronicweirdo.ur.clock.components;

import javax.annotation.PostConstruct;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.FixtureDef;

public class Wall extends RigidBody {

	private PolygonShape shape;
	private FixtureDef fixture;
	
	public Wall(float x, float y, float width, float height) {
		super(x, y);
		
		this.shape = new PolygonShape();
		this.shape.setAsBox(width, height);
	}

	@Override
	protected void init() {
		this.body = globals.world().createBody(definition);

		this.fixture = new FixtureDef();
		fixture.density = 1;
		fixture.restitution = 0.3f;
		fixture.shape = shape;

		this.body.createFixture(fixture);
	}

	@Override
	public void render() {
		// walls are not drawn
	}

	
}
