package com.chronicweirdo.ur.clock.components;

import static org.lwjgl.opengl.GL11.*;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;

import com.chronicweirdo.ur.clock.Hour;

public class Ball extends DynamicBody implements Destroyable {

	protected CircleShape shape;
	protected Fixture fixture;
	protected Hour hour;

	protected Ball(Hour hour, float x, float y, float radius) {
		super(x, y);

		this.hour = hour;
		this.shape = new CircleShape();
		this.shape.m_radius = radius;
	}

	@Override
	protected void init() {
		this.body = globals.world().createBody(this.definition);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 0.1f;
		fixtureDef.restitution = .95f;
		fixtureDef.shape = this.shape;
		fixture = this.body.createFixture(fixtureDef);
		fixture.setUserData(hour);
	}

	@Override
	public void render() {
		if (globals != null) {
			glPushMatrix();

			Vec2 bodyPosition = this.body.getPosition().mul(globals.d());
			glTranslatef(bodyPosition.x, bodyPosition.y, 0);
			glRotated(Math.toDegrees(this.body.getAngle()), 0, 0, 1);

			glColor3f(0, 0, 1);
			glLineWidth(1);

			glBegin(GL_POLYGON);
			for (int i = 0; i <= globals.circleSegments(); i++) {
				double degInRad = Math.toRadians(i);
				double angle = i * 2 * Math.PI / globals.circleSegments();
				double x = (Math.cos(angle) * shape.m_radius) * globals.d();
				double y = (Math.sin(angle) * shape.m_radius) * globals.d();
				glVertex2f((float) x, (float) y);
			}
			glEnd();
			glPopMatrix();
		}
	}

}
