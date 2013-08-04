package com.chronicweirdo.ur.clock.components;

import static org.lwjgl.opengl.GL11.*;

import javax.annotation.PostConstruct;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.springframework.beans.factory.annotation.Autowired;

import com.chronicweirdo.ur.clock.Hour;
import com.chronicweirdo.ur.clock.Main;

public class HourPin extends RigidBody {

	@Autowired
	protected Main main;
	
	protected CircleShape shape;
	protected Fixture fixture;
	protected Hour hour;

	protected HourPin(Hour hour, float x, float y) {
		super(x, y);
		this.hour = hour;
	}

	@Override
	@PostConstruct
	protected void init() {
		this.shape = new CircleShape();
		this.shape.m_radius = .5f;
		
		this.body = globals.world().createBody(this.definition);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 1f;
		fixtureDef.restitution = .3f;
		fixtureDef.shape = this.shape;
		Fixture sensor = this.body.createFixture(fixtureDef);
		sensor.setUserData(hour);
	}

	@Override
	public void render() {
		if (globals != null) {
			glPushMatrix();

			Vec2 bodyPosition = this.body.getPosition().mul(globals.d());
			glTranslatef(bodyPosition.x, bodyPosition.y, 0);
			glRotated(Math.toDegrees(this.body.getAngle()), 0, 0, 1);

			if (main.getCurrentHour().equals(hour.getHour())) {
				glColor3f(0, 1, 0);
			} else {
				glColor3f(1, 1, 0);
			}
			glLineWidth(2);

			glBegin(GL_LINE_LOOP);
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
