package com.chronicweirdo.ur.clock.components;

import static org.lwjgl.opengl.GL11.*;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.springframework.beans.factory.annotation.Autowired;

import com.chronicweirdo.ur.clock.DestroyComponentMessage;
import com.chronicweirdo.ur.clock.Main;

public class Bomb extends DynamicBody implements Destroyable {

	@Autowired
	private ComponentFactory factory;
	
	@Autowired
	private Main main;
	
	protected CircleShape shape;
	protected Fixture fixture;
	protected float blastRadius;
	protected long blastTimeout;
	protected long createdOn;
	protected long blastStart;
	protected long blastDuration;

	protected Bomb(float x, float y, float radius, float blastRadius,
			long blastTimeout) {
		super(x, y);

		this.shape = new CircleShape();
		this.shape.m_radius = radius;
		this.blastRadius = blastRadius;
		this.blastTimeout = blastTimeout;
		this.createdOn = System.currentTimeMillis();
	}

	@Override
	protected void init() {
		this.body = globals.world().createBody(this.definition);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 0.1f;
		fixtureDef.restitution = .2f;
		fixtureDef.shape = this.shape;
		fixture = this.body.createFixture(fixtureDef);
	}
	
	private float getRedColor() {
		float r = ((float) (System.currentTimeMillis() - createdOn) / blastTimeout) * .8f + .2f;
		return r;
	}

	@Override
	public void render() {
		glPushMatrix();

		Vec2 bodyPosition = this.body.getPosition().mul(globals.d());
		glTranslatef(bodyPosition.x, bodyPosition.y, 0);
		glRotated(Math.toDegrees(this.body.getAngle()), 0, 0, 1);

		float nowRadius = shape.m_radius;
		if (System.currentTimeMillis() - createdOn > blastTimeout) {
			nowRadius = blastRadius;
		}
		glColor3f(getRedColor(), 0, 0);
		glLineWidth(1);

		glBegin(GL_POLYGON);
		for (int i = 0; i <= globals.circleSegments(); i++) {
			double degInRad = Math.toRadians(i);
			double angle = i * 2 * Math.PI / globals.circleSegments();
			double x = (Math.cos(angle) * nowRadius) * globals.d();
			double y = (Math.sin(angle) * nowRadius) * globals.d();
			glVertex2f((float) x, (float) y);
		}
		glEnd();
		glPopMatrix();
		
		Vec2 bp = this.body.getPosition();
		if (System.currentTimeMillis() - createdOn > blastTimeout) {
			for (GameComponent c: factory.components()) {
				if (c instanceof Destroyable) {
					Body body = c.getBody();
					Vec2 p = body.getPosition();
					double d = Math.sqrt(Math.pow(Math.abs(p.x-bp.x), 2)
							+ Math.pow(Math.abs(p.y-bp.y),2));
					if (d <= blastRadius) {
						main.addMessage(new DestroyComponentMessage(c));
					}
				}
			}
		}
	}

}
