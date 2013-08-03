package com.chronicweirdo.ur.clock.components;

import static org.lwjgl.opengl.GL11.*;

import javax.annotation.PostConstruct;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.FixtureDef;

public class Box extends DynamicBody {

	protected PolygonShape shape;
	protected FixtureDef fixture;

	public Box(float x, float y, float width, float height) {
		super(x, y);

		this.shape = new PolygonShape();
		this.shape.setAsBox(width, height);
	}
	
	@Override
	protected void init() {
		this.body = globals.world().createBody(this.definition);

		this.fixture = new FixtureDef();
		this.fixture.density = 0.1f;
		
		this.fixture.shape = this.shape;
		this.body.createFixture(this.fixture);
	}

	@Override
	public void render() {
		glPushMatrix();

		Vec2 bodyPosition = this.body.getPosition().mul(globals.d());
		glTranslatef(bodyPosition.x, bodyPosition.y, 0);
		glRotated(Math.toDegrees(this.body.getAngle()), 0, 0, 1);
		
		Vec2[] vertices = this.shape.getVertices();
		glColor3f(1, 1, 1);
		glBegin(GL_QUADS);
		for (Vec2 v : vertices) {
			glVertex2f(v.x * globals.d(), v.y * globals.d());
		}
		glEnd();
		glPopMatrix();
	}

}
