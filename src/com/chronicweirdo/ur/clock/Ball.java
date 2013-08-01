package com.chronicweirdo.ur.clock;

import static org.lwjgl.opengl.GL11.*;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public class Ball extends DynamicBody {
	
	
	protected CircleShape shape;
	protected FixtureDef fixture;

	protected Ball(World world, float x, float y, float radius) {
		super(x, y);

		this.shape = new CircleShape();
		this.shape.m_radius = radius;

		this.body = world.createBody(this.definition);

		this.fixture = new FixtureDef();
		this.fixture.density = 0.1f;
		this.fixture.restitution = .95f;
		this.fixture.shape = this.shape;
		this.body.createFixture(this.fixture);
		
		this.body.applyForce(new Vec2(1000, 1000), this.body.getWorldCenter());
	}

	@Override
	public void render() {
		glPushMatrix();

		Vec2 bodyPosition = this.body.getPosition().mul(Globals.D);
		glTranslatef(bodyPosition.x, bodyPosition.y, 0);
		glRotated(Math.toDegrees(this.body.getAngle()), 0, 0, 1);
		
		glColor3f(0, 1, 0);
		glLineWidth(1);
		
		glBegin(GL_LINE_LOOP);
		for (int i = 0; i <= Globals.CIRCLE_SEGMENTS; i++) {
			double degInRad = Math.toRadians(i);
			double angle = i * 2 * Math.PI / Globals.CIRCLE_SEGMENTS;
			double x = (Math.cos(angle)
					* shape.m_radius) * Globals.D;
			double y = (Math.sin(angle)
							* shape.m_radius) * Globals.D;
			glVertex2f((float) x, (float) y);
		}
		glEnd();
		glPopMatrix();
	}

}
